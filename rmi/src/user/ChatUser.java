package user;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author papihack
 * @since 21/05/20
 * @version 0.1.0
 *
 */
public interface ChatUser extends Remote
{
	public void displayMessage(String message) throws RemoteException;
}
