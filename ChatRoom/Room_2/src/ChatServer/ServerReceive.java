//接受聊天室的消息
import java.awt.* ; 
import java.awt.event.* ; 
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;

public class ServerReceive extends Thread  
{
	
	JTextArea textArea ;
	JTextField textField ;
	UserLinkList userLinkList ; 
	LNode client ;

	public boolean isStop ;

	public ServerReceive(JTextArea textArea , JTextField textField ,
		LNode client ,UserLinkList userLinkList)
	{ 
		this.textArea = textArea ; 
		this.textField = textField ;
		this.client = client ;
		this.userLinkList = userLinkList ;

		isStop = false ;
	}
	public void run()
	{
		//向所有人发送用户列表。
		sendUserList(); 

		while(!isStop && !client.socket.isClosed())
		{
			try
			{
				String type = (String)client.ois.readObject() ;
				if (type.equalsIgnoreCase("聊天信息"))
				{
					String toSomebody = (String)client.ois.readObject() ;
					String status = (String)client.ois.readObject() ;
					String action = (String)client.ois.readObject() ;
					String message = (String)client.ois.readObject() ;

					String msg = "-"
						+ client.username 
						+ " "  
						+ action  
						+ " 对 " 
						+ toSomebody 
						+ " 说 "
						+ "\n"
						+ message
						+ "\n" ; 
					textArea.append(msg) ;
					if (toSomebody.equalsIgnoreCase("所有人"))
					{
						sendToAll(msg) ;
					}
					else 
					{
						try
						{
							client.oos.writeObject("聊天信息") ;
							client.oos.flush() ;
							client.oos.writeObject(msg) ;
							client.oos.flush() ;
						} catch (Exception e) {}

						LNode node = userLinkList.findUser(toSomebody) ;

						if (node != null)
						{
							node.oos.writeObject("聊天信息") ;
							node.oos.flush() ;
							node.oos.writeObject(msg) ;
							node.oos.flush() ;
						}
					}
				}
				else if (type.equalsIgnoreCase("用户下线"))
				{   
					System.out.println("用户下线成功。") ;
					LNode node = userLinkList.findUser(client.username) ;
					userLinkList.delUser(node) ;
					

					String msg = "用户 " + client.username + " 下线\n" ;

					textArea.append(msg) ;
					textField.setText("在线用户:" + userLinkList.getCount()+"人\n") ;
					sendToAll(msg) ; 
					sendUserList() ; 

					break ;
				}
			}
			catch (Exception e){}
		}
	}
	//把消息发给所有人
	public void sendToAll(String message)
	{
		int count = userLinkList.getCount() ;

		for (int i = 0 ; i < count ; ++ i )
		{
				LNode node = userLinkList.findUser(i) ;
				if (node == null || node.username == client.username ) continue ;
				try
				{
					node.oos.writeObject("聊天信息") ;
					node.oos.flush() ;
					node.oos.writeObject(message) ;
					node.oos.flush() ;
				}
				catch (Exception e){} 
		}
	}
	public void sendUserList()
	{
		String userList = "" ;
		int count = userLinkList.getCount();
		for (int i = 0 ; i < count ; ++ i )
		{
				LNode node = userLinkList.findUser(i) ;
				if (node == null) continue ;
				userList += node.username ;
				userList += '\n' ;
		}
		for (int i = 0 ; i < count ; ++ i )
		{
				LNode node = userLinkList.findUser(i) ;
				if (node == null ) continue ;
				try
				{
					node.oos.writeObject("用户列表") ;
					node.oos.flush() ;
					node.oos.writeObject(userList) ;
					node.oos.flush() ;
				}
				catch (Exception e){}
		}
	}
}
