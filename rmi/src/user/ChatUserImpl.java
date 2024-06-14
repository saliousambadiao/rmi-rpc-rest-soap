package user;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author papihack
 * @since 21/05/20
 * @version 0.1.0
 *
 */
@SuppressWarnings("serial")
public class ChatUserImpl extends UnicastRemoteObject implements ChatUser
{
	
	private InterfaceGraphique gui;
	
	protected ChatUserImpl() throws RemoteException
	{
		super();
	}
	
	public void setIg(InterfaceGraphique gui)
	{
		this.gui = gui;
	}
	
	@Override
	public void displayMessage(String message) throws RemoteException
	{
		gui.display(message);
	}
	
}
