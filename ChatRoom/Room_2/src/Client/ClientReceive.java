//package ChatClient ;

import javax.swing.* ;
import java.io.* ;
import java.net.* ;
/*
	ʵ������ͻ�����Ϣ�շ��ࡣ
	�����̲߳��ϵĽ��պͷ�����Ϣ����������ֱ���Ͽ����������������ֹͣ��
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
				if (type.equalsIgnoreCase("ϵͳ��Ϣ"))
				{
					String sysMessage = (String)input.readObject() ;
					textArea.append("ϵͳ��Ϣ:" + sysMessage) ;
				}
				else if (type.equalsIgnoreCase("����ر�"))
				{
					output.close() ;
					input.close() ;
					socket.close() ;
					textArea.append("�������ѹر�.\n")  ;
					break ;
				}
				else if (type.equalsIgnoreCase("������Ϣ"))
				{
					String message = (String)input.readObject() ;
					textArea.append(message) ;
				}
				else if (type.equalsIgnoreCase("�û��б�"))
				{
					String userList = (String)input.readObject() ;
					String userNames[] = userList.split("\n") ;
					userComboBox.removeAllItems() ;

					userComboBox.addItem("������") ;
					for ( int i = 0 ; i < userNames.length; ++ i )
					{
						userComboBox.addItem(userNames[i]) ;
					}
					userComboBox.setSelectedIndex(0) ;
					showStatus.setText("�����û�"+ userNames.length +"��") ;
				}
			}
			catch (Exception e) {}
		}
	}
}
