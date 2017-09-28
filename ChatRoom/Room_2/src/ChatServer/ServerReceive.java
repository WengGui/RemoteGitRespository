//���������ҵ���Ϣ
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
		//�������˷����û��б�
		sendUserList(); 

		while(!isStop && !client.socket.isClosed())
		{
			try
			{
				String type = (String)client.ois.readObject() ;
				if (type.equalsIgnoreCase("������Ϣ"))
				{
					String toSomebody = (String)client.ois.readObject() ;
					String status = (String)client.ois.readObject() ;
					String action = (String)client.ois.readObject() ;
					String message = (String)client.ois.readObject() ;

					String msg = "-"
						+ client.username 
						+ " "  
						+ action  
						+ " �� " 
						+ toSomebody 
						+ " ˵ "
						+ "\n"
						+ message
						+ "\n" ; 
					textArea.append(msg) ;
					if (toSomebody.equalsIgnoreCase("������"))
					{
						sendToAll(msg) ;
					}
					else 
					{
						try
						{
							client.oos.writeObject("������Ϣ") ;
							client.oos.flush() ;
							client.oos.writeObject(msg) ;
							client.oos.flush() ;
						} catch (Exception e) {}

						LNode node = userLinkList.findUser(toSomebody) ;

						if (node != null)
						{
							node.oos.writeObject("������Ϣ") ;
							node.oos.flush() ;
							node.oos.writeObject(msg) ;
							node.oos.flush() ;
						}
					}
				}
				else if (type.equalsIgnoreCase("�û�����"))
				{   
					System.out.println("�û����߳ɹ���") ;
					LNode node = userLinkList.findUser(client.username) ;
					userLinkList.delUser(node) ;
					

					String msg = "�û� " + client.username + " ����\n" ;

					textArea.append(msg) ;
					textField.setText("�����û�:" + userLinkList.getCount()+"��\n") ;
					sendToAll(msg) ; 
					sendUserList() ; 

					break ;
				}
			}
			catch (Exception e){}
		}
	}
	//����Ϣ����������
	public void sendToAll(String message)
	{
		int count = userLinkList.getCount() ;

		for (int i = 0 ; i < count ; ++ i )
		{
				LNode node = userLinkList.findUser(i) ;
				if (node == null || node.username == client.username ) continue ;
				try
				{
					node.oos.writeObject("������Ϣ") ;
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
					node.oos.writeObject("�û��б�") ;
					node.oos.flush() ;
					node.oos.writeObject(userList) ;
					node.oos.flush() ;
				}
				catch (Exception e){}
		}
	}
}
