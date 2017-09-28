import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/*
	����������ܡ�
	����ChatServer���캯������Ҫ����Ϊ����������˵Ľ��棬����¼��������¼�����
	����ServerListen����ʵ�ַ�����û������ߵ�������
	����ServerReceive����ʵ�ַ������˵���Ϣ�շ���
*/
class ChatServer extends JFrame implements ActionListener 
{
	//���ö˿�
	private static int port = 30000 ;

	private final int SER_WIDHT = 400 ;
	private final int SER_HETGHT = 450 ; 
	JTextField showStatus ; 
	JComboBox userComboBox ; 

	JButton sendMessageButton ;
	JTextField editMessage ;
	JLabel messageLabel ; 
	JScrollPane messageScrollPane ;
	// ������������������һ��. 

	// �˵�
	JMenuBar Jmenubar = new JMenuBar() ;
	JMenu file = new JMenu("����(V)") ;
	JMenu helpMenu = new JMenu("����(H)") ;
	JMenuItem helpItem = new JMenuItem("����(H)") ;
	JMenuItem startItem = new JMenuItem("����������(S)") ;
	JMenuItem endItem = new JMenuItem("ֹͣ������(E)") ;
	JMenuItem exitItem = new JMenuItem("�˳�(X)") ;

	//������ʾ�� 
	JTextArea showMessage ; 

	//��ܵĴ�С
	Dimension faceSize = new Dimension(SER_WIDHT , SER_HETGHT) ; 

	JPanel panel = new JPanel() ;
	GridBagLayout gridBag ;
	GridBagConstraints gridBagCon ;

	ServerSocket serverSocket ; 
	UserLinkList userLinkList ;
	ServerListen listenThread ;

	public ChatServer ()
	{
		init() ;

		this.pack() ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		this.setSize(faceSize) ;
		//���ô�С��
		Dimension screenSize = 
			Toolkit.getDefaultToolkit().getScreenSize() ;
		//���÷��������ֵ�λ�á�
		this.setLocation((int)(screenSize.width - faceSize.getWidth())/2 ,
			 (int)(screenSize.height - faceSize.getHeight())/2) ;
		this.setResizable(false) ;
		//���ñ���
		this.setTitle("�����ҷ�����") ;  
		this.show(true) ;

		//���ÿ�ݼ�
		file.setMnemonic('V') ;
		startItem.setMnemonic('S') ;
		startItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_S,InputEvent.CTRL_MASK)) ;
		endItem.setMnemonic('E') ; 
		endItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_E,InputEvent.CTRL_MASK)) ;
		exitItem.setMnemonic('X') ;
		exitItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_X,InputEvent.CTRL_MASK)) ;
		helpMenu.setMnemonic('H') ;
		helpItem.setMnemonic('H');
		helpItem.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_S,InputEvent.CTRL_MASK)) ;

	}

	public void init()
	{
		Container contentPane = getContentPane() ;
		contentPane.setLayout(new BorderLayout()) ;

		//��ϲ˵�
		file.add(startItem) ;
		file.add(endItem) ;
		file.addSeparator() ;
		file.add(exitItem) ;
		helpMenu.add(helpItem) ;
		Jmenubar.add(file) ;
		Jmenubar.add(helpMenu) ;
		//��ʼ����ֹͣ����ť������
		endItem.setEnabled(false) ;
		setJMenuBar (Jmenubar);

		//Ϊ�˵�����¼����� 
		startItem.addActionListener(this) ;
		endItem.addActionListener(this) ;
		exitItem.addActionListener(this) ;
		helpItem.addActionListener(this) ;

		//������ʾ��ϢAreaֻ��
		showMessage = new JTextArea() ; 
		showMessage.setEditable(false) ;
		messageScrollPane = new JScrollPane(showMessage , 
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) ; 
		messageScrollPane.setPreferredSize(new Dimension(400,400));
		messageScrollPane.revalidate() ;

		showStatus = new JTextField(35) ;
		showStatus.setEditable(false) ;
		editMessage = new JTextField(24) ;
		editMessage.setEnabled(false) ;
		sendMessageButton = new JButton("����") ;
		sendMessageButton.setEnabled(false) ;
		//�����Ϣ�¼�
		editMessage.addActionListener(this) ;
		sendMessageButton.addActionListener(this) ;

		messageLabel = new JLabel("������Ϣ:") ;
		panel = new JPanel() ;
		gridBag = new GridBagLayout() ;
		panel.setLayout(gridBag) ;
		
		//���ֵ�����
		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 0 ;
		gridBagCon.gridy = 0 ;
		gridBagCon.gridwidth = 3 ;
		gridBagCon.gridheight = 2 ;
		gridBagCon.ipadx = 5 ;
		gridBagCon.ipady = 5 ;
		JLabel none = new JLabel("   ") ;
		gridBag.setConstraints(none , gridBagCon) ;
		panel.add(none) ;

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 0 ;
		gridBagCon.gridy = 3 ;
		gridBag.setConstraints(messageLabel,gridBagCon) ;
		panel.add(messageLabel) ; 

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 1 ;
		gridBagCon.gridy = 3 ;
		gridBag.setConstraints(editMessage,gridBagCon) ;
		panel.add(editMessage) ;

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 2 ;
		gridBagCon.gridy = 3 ;
		gridBag.setConstraints(sendMessageButton,gridBagCon) ;
		panel.add(sendMessageButton) ;

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 0 ;
		gridBagCon.gridy = 4 ;
		gridBagCon.gridwidth = 3 ;
		gridBag.setConstraints(showStatus,gridBagCon) ;
		panel.add(showStatus) ;

		contentPane.add(messageScrollPane,BorderLayout.CENTER) ;
		contentPane.add(panel,BorderLayout.SOUTH) ;

		this.addWindowListener(new WindowAdapter(){
			public void windowClosiong(WindowEvent e)
			{
				stopService() ;
				System.exit(0) ;
			}
		}) ;
	}


	//�¼�����
	public void actionPerformed(ActionEvent e)
	{
		//������� Event �Ķ���
		Object obj = e.getSource() ;
		if (obj == startItem)
		{
			startService() ;
		}
		else if (obj == endItem)
		{
			int flag = JOptionPane.showConfirmDialog(
				this,"ȷ��ֹͣ��������" ,"ֹͣ������",
				JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE );
			if (flag == JOptionPane.YES_OPTION)
			{
				stopService() ;
			}
		}
		else if (obj == exitItem)
		{
			int flag = JOptionPane.showConfirmDialog(
				this,"�Ƿ�Ҫ�˳���" , "�˳�" , JOptionPane.YES_OPTION
				,JOptionPane.QUESTION_MESSAGE) ;
			if (flag == JOptionPane.YES_OPTION)
			{
				stopService() ;
				System.exit(0) ;
			}
		}
		else if (obj == helpItem)
		{
			//����������������
			new  ServerHelp(this).setVisible(true) ;  
		}
		else if (obj == editMessage || obj == sendMessageButton)
		{
			sendSystemMessage() ;
		}
	}
	//����������
	public void startService()
	{
		try
		{
			serverSocket = new ServerSocket(port,100) ;
			showMessage.append("�������Ѿ�������\n") ;
			startItem.setEnabled(false) ;
			endItem.setEnabled(true) ;
			editMessage.setEnabled(true) ;
			sendMessageButton.setEnabled(true) ;
		}
		catch (Exception e){}
		userLinkList = new UserLinkList() ;
		listenThread = new ServerListen(serverSocket ,showMessage 
			, showStatus ,userLinkList ) ;
		listenThread.start() ;
	}
	//ֹͣ������
	public void stopService()
	{
		try
		{
			startItem.setEnabled(true) ;
			endItem.setEnabled(false) ;
			editMessage.setEnabled(false) ;
			sendMessageButton.setEnabled(false) ;
			//���ͷ������رյ���ʾ��
			sendStopToAll() ;
			listenThread.isStop = true ;
			serverSocket.close() ;
			
			int count = userLinkList.getCount() ;
			
			for (int i = 0 ; i < count ; ++ i)
			{
				LNode node = userLinkList.findUser(i) ;
				node.ois.close() ;
				node.oos.close() ;
				node.socket.close() ;
			} 
			showMessage.append("�������Ѿ��ر�\n") ;

		}
		catch (Exception e){}
	}
	public void sendStopToAll()
	{
		int count = userLinkList.getCount() ;
		for (int i = 0 ;i < count; ++ i)
		{
			LNode node = userLinkList.findUser(i) ;
			if (node == null) continue ;
			try
			{
				node.oos.writeObject("����ر�") ;
				node.oos.flush() ;
			}
			catch (Exception e){}
		}
	}
	//������Ϣ��������
	public void sendMessageToAll(String message)
	{
		int count = userLinkList.getCount() ;
		for (int i = 0; i < count ; ++ i )
		{
				LNode node = userLinkList.findUser(i) ;
				if (node == null) continue ;
				try
				{
					node.oos.writeObject("ϵͳ��Ϣ") ;
					node.oos.flush() ;
					node.oos.writeObject(message) ; 
					node.oos.flush() ;
				}
				catch (Exception e){}
		}
		editMessage.setText("") ;
	}
	//��ͻ��˷�����Ϣ
	public void sendSystemMessage()
	{
		String message = editMessage.getText()+"\n" ;
		showMessage.append(message) ;
		sendMessageToAll(message) ;
		editMessage.setText("") ;
	}
	public static void main(String[] args) 
	{
		new ChatServer() ;
	}
}
