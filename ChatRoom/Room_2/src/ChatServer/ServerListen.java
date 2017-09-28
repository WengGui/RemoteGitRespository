//服务端的监听类
import java.awt.* ; 
import java.awt.event.* ; 
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;

public class ServerListen extends Thread
{
	ServerSocket server ;  
	 
	JTextArea textArea ;
	JTextField textField ;
	UserLinkList userLinkList ; 

	LNode client ;
	ServerReceive receiveThread ;

	public boolean isStop ;

	public ServerListen (ServerSocket server , JTextArea textArea , 
		JTextField textField,UserLinkList userLinkList)
	{
		this.server = server ;
		this.textArea = textArea ; 
		this.textField = textField ;
		this.userLinkList = userLinkList ;
		isStop = false ;
	}
	public void run()
	{
		while (!isStop && !server.isClosed())
		{
			try
			{
				client = new LNode() ;
				client.socket = server.accept() ;
				client.oos = new ObjectOutputStream(client.socket.getOutputStream()) ;
				client.oos.flush() ;
				client.ois = new ObjectInputStream(client.socket.getInputStream()) ;
				client.username = (String)client.ois.readObject() ; 

				userLinkList.addUser(client) ;
				textArea.append("用户 " + client.username + " 上线.\n") ;
				textField.setText("在线用户:"+userLinkList.getCount()+"人\n");

				receiveThread = new ServerReceive (textArea , textField , client , userLinkList ) ;
				/*
				JTextArea textArea , JTextField textField ,
															LNode client ,UserLinkList userLinkList
				*/
				receiveThread.start() ;
			}
			catch (Exception e) { }
		}
	}
}
