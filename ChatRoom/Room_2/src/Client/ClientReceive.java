//package ChatClient ;

import javax.swing.* ;
import java.io.* ;
import java.net.* ;
/*
	实现聊天客户端消息收发类。
	启动线程不断的接收和发送消息给服务器。直到断开服务器，或服务器停止。
*/

public class ClientReceive extends Thread
{
	JComboBox userComboBox ;
	JTextArea textArea ; 

	Socket socket ;
	ObjectOutputStream output ;
	ObjectInputStream input ;
	JTextField showStatus ; 

	public ClientReceive(Socket socket , ObjectOutputStream output ,
		ObjectInputStream input , JComboBox userComboBox , JTextArea textArea ,
		JTextField showStatus) 
	{
		this.socket = socket ;
		this.output = output ;
		this.input = input ;
		this.userComboBox = userComboBox ;
		this.textArea = textArea ;
		this.showStatus = showStatus ;
	}
	public void run ()
	{
		while (! socket.isClosed())
		{
			try
			{
				String type = (String)input.readObject() ;
				if (type.equalsIgnoreCase("系统消息"))
				{
					String sysMessage = (String)input.readObject() ;
					textArea.append("系统消息:" + sysMessage) ;
				}
				else if (type.equalsIgnoreCase("服务关闭"))
				{
					output.close() ;
					input.close() ;
					socket.close() ;
					textArea.append("服务器已关闭.\n")  ;
					break ;
				}
				else if (type.equalsIgnoreCase("聊天信息"))
				{
					String message = (String)input.readObject() ;
					textArea.append(message) ;
				}
				else if (type.equalsIgnoreCase("用户列表"))
				{
					String userList = (String)input.readObject() ;
					String userNames[] = userList.split("\n") ;
					userComboBox.removeAllItems() ;

					userComboBox.addItem("所有人") ;
					for ( int i = 0 ; i < userNames.length; ++ i )
					{
						userComboBox.addItem(userNames[i]) ;
					}
					userComboBox.setSelectedIndex(0) ;
					showStatus.setText("在线用户"+ userNames.length +"人") ;
				}
			}
			catch (Exception e) {}
		}
	}
}
