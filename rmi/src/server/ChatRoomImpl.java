package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;

import user.ChatUser;

/**
 * 
 * @author papihack
 * @since 21/05/20
 * @version 0.1.0
 *
 */
@SuppressWarnings("serial")
public class ChatRoomImpl extends UnicastRemoteObject implements ChatRoom
{
	
	Hashtable<String, ChatUser> utilisateurs;
	
	protected ChatRoomImpl() throws RemoteException
	{
		super();
		utilisateurs = new Hashtable<String, ChatUser>();
	}
	
	@Override
	public void subscribe(ChatUser user, String pseudo) throws RemoteException
	{
		if (!utilisateurs.containsKey(pseudo))
		{
			utilisateurs.put(pseudo, user);
			this.postMessage(pseudo, " s'est connecté(e).");
		}
	}
	
	@Override
	public void unsubscribe(String pseudo) throws RemoteException
	{
		if (utilisateurs.containsKey(pseudo))
		{
			utilisateurs.remove(pseudo);
			this.postMessage(pseudo, " s'est déconnecté(e).");
		}
	}
	
	@Override
	public void postMessage(String pseudo, String message) throws RemoteException
	{
		String messageEntier;
		if (message.endsWith("s'est connecté(e).") || message.endsWith("s'est déconnecté(e)."))
		{
			messageEntier = "[" + pseudo + "]" + message;
		}
		else
		{
			messageEntier = pseudo + " >>> " + message;
		}
		Enumeration<ChatUser> e = utilisateurs.elements();
		while (e.hasMoreElements())
		{
			ChatUser user = (ChatUser) e.nextElement();
			user.displayMessage(messageEntier);
		}
	}
	
}
