package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import user.ChatUser;

/**
 * 
 * @author papihack
 * @since 21/05/20
 * @version 0.1.0
 *
 */
public interface ChatRoom extends Remote
{
	public void subscribe(ChatUser user, String pseudo) throws RemoteException;
	
	public void unsubscribe(String pseudo) throws RemoteException;
	
	public void postMessage(String pseudo, String message) throws RemoteException;
}
