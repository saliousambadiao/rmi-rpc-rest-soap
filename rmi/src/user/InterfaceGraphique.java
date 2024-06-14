package user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.ChatRoom;

/**
 * 
 * @author papihack
 * @since 21/05/20
 * @version 0.1.0
 *
 */
@SuppressWarnings("serial")
public class InterfaceGraphique implements Serializable
{
	private String title = "RMI ChatRoom - Salon de discussion";
	private String pseudo = null;
	private ChatRoom room = null;
	
	private JFrame window = new JFrame(this.title);
	private JTextArea txtOutput = new JTextArea();
	private JTextField txtMessage = new JTextField();
	private JButton btnSend = new JButton("Envoyer");
	ChatUserImpl chatUserImpl;
	
	public InterfaceGraphique() throws RemoteException
	{
		this.createIHM();
		try
		{
			chatUserImpl = new ChatUserImpl();
			chatUserImpl.setIg(this);
			
			// obtention d'une référence sur l'objet distant à partir de son nom
			// Changer le paramètre de la fonction lookup en fonction de votre utilisation
			// (@IP du serveur)
			Remote r = Naming.lookup("rmi://localhost:1099/CHAT");
			this.room = (ChatRoom) r;
			this.requestPseudo();
		} catch (MalformedURLException e)
		{
			System.out.println("Impossible de joindre le salon de discussion...");
			System.exit(0);
			e.printStackTrace();
		} catch (RemoteException e)
		{
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void createIHM()
	{
		JPanel panel = (JPanel) this.window.getContentPane();
		JScrollPane sclPane = new JScrollPane(txtOutput);
		panel.add(sclPane, BorderLayout.CENTER);
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(this.txtMessage, BorderLayout.CENTER);
		southPanel.add(this.btnSend, BorderLayout.EAST);
		panel.add(southPanel, BorderLayout.SOUTH);
		
		window.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				try
				{
					window_windowClosing(e);
				} catch (RemoteException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		btnSend.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnSend_actionPerformed(e);
			}
		});
		txtMessage.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent event)
			{
				if (event.getKeyChar() == '\n')
					btnSend_actionPerformed(null);
			}
		});
		
		this.txtOutput.setBackground(new Color(220, 220, 220));
		this.txtOutput.setEditable(false);
		this.window.setSize(500, 400);
		this.window.setVisible(true);
		this.txtMessage.requestFocus();
	}
	
	public void requestPseudo() throws RemoteException
	{
		this.pseudo = JOptionPane.showInputDialog(this.window, "Veuillez saisir votre pseudo svp : ", this.title,
				JOptionPane.OK_OPTION);
		if (this.pseudo == null)
			System.exit(0);
		this.room.subscribe(chatUserImpl, this.pseudo);
		
	}
	
	public void window_windowClosing(WindowEvent e) throws RemoteException
	{
		this.room.unsubscribe(this.pseudo);
		System.exit(-1);
	}
	
	public void btnSend_actionPerformed(ActionEvent e)
	{
		try
		{
			this.room.postMessage(this.pseudo, this.txtMessage.getText());
		} catch (RemoteException e1)
		{
			e1.printStackTrace();
			System.out.println("Impossible d'envoyer le message...");
		}
		this.txtMessage.setText("");
		this.txtMessage.requestFocus();
	}
	
	public static void main(String[] args) throws RemoteException
	{
		new InterfaceGraphique();
	}
	
	public void display(String message) throws RemoteException
	{
		this.txtOutput.append(message + " \n");
		this.txtOutput.moveCaretPosition(this.txtOutput.getText().length());
	}
}
