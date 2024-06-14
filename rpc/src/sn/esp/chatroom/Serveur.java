package sn.esp.chatroom;

import java.util.Vector;

import org.apache.xmlrpc.WebServer;

/**
 * 
 * @author papihack
 * @since 21/05/20
 * @version 0.1.0
 *
 */
public class Serveur
{
	
	private static Vector<String> user = new Vector<String>();
	String msg_courant = null;
	
	public static void main(String[] args)
	{
		try
		{
			System.out.println("[*] Tentative de démarrage du serveur XML-RPC...");
			WebServer server = new WebServer(8080);
			server.addHandler("sample", new Serveur());
			server.start();
			System.out.println("[*] Démarré avec succès.");
			System.out.println("[*] En attente des prochaines requêtes. (CTRL-C pour arrêter le programme.)");
		} catch (Exception exception)
		{
			System.err.println("[*] Serveur Java: " + exception);
		}
	}
	
	public boolean subscribe(String pseudo)
	{
		boolean ok = false;
		if (!user.contains(pseudo))
		{
			user.add(pseudo);
			ok = true;
		}
		postMessage(pseudo, " s'est connecté(e).");
		return ok;
	}
	
	public String postMessage(String pseudo, String message)
	{
		String messageEntier;
		if (message.endsWith("s'est connecté(e).") || message.endsWith("s'est déconnecté(e)."))
		{
			messageEntier = "[" + pseudo + "]" + message;
		}
		else
		{
			messageEntier = "[" + pseudo + "] >>> " + message;
		}
		// System.out.println(messageEntier);
		msg_courant = messageEntier;
		return messageEntier;
	}
	
	public String getMessage()
	{
		return msg_courant;
	}
	
	public boolean unsubscribe(String pseudo)
	{
		boolean ok = false;
		if (user.contains(pseudo))
		{
			user.remove(pseudo);
			ok = true;
			postMessage(pseudo, " s'est déconnecté(e).");
		}
		return ok;
	}
	
}
