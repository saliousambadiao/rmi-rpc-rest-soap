package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 
 * @author papihack
 * @since 21/05/20
 * @version 0.1.0
 *
 */
public class ChatServeur
{
	
	public static void main(String[] args)
	{
		try
		{
			LocateRegistry.createRegistry(1099);
			ChatRoomImpl chatRoomImpl = new ChatRoomImpl();
			
			Naming.rebind("rmi://localhost/CHAT", chatRoomImpl);
			
			System.out.println("Serveur du ChatRoom démarré...");
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}
	
}
