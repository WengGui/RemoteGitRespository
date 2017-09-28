import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/*
	服务器主框架。
	包含ChatServer构造函数，主要功能为定义服务器端的界面，添加事件监听与事件处理。
	调用ServerListen类来实现服务端用户上下线的侦听，
	调用ServerReceive类来实现服务器端的消息收发。
*/
class ChatServer extends JFrame implements ActionListener 
{
	//设置端口
	private static int port = 30000 ;

	private final int SER_WIDHT = 400 ;
	private final int SER_HETGHT = 450 ; 
	JTextField showStatus ; 
	JComboBox userComboBox ; 

	JButton sendMessageButton ;
	JTextField editMessage ;
	JLabel messageLabel ; 
	JScrollPane messageScrollPane ;
	// 将上面两个组件组合再一起. 

	// 菜单
	JMenuBar Jmenubar = new JMenuBar() ;
	JMenu file = new JMenu("服务(V)") ;
	JMenu helpMenu = new JMenu("帮助(H)") ;
	JMenuItem helpItem = new JMenuItem("帮助(H)") ;
	JMenuItem startItem = new JMenuItem("启动服务器(S)") ;
	JMenuItem endItem = new JMenuItem("停止服务器(E)") ;
	JMenuItem exitItem = new JMenuItem("退出(X)") ;

	//聊天显示框 
	JTextArea showMessage ; 

	//框架的大小
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
		//设置大小。
		Dimension screenSize = 
			Toolkit.getDefaultToolkit().getScreenSize() ;
		//设置服务器出现的位置。
		this.setLocation((int)(screenSize.width - faceSize.getWidth())/2 ,
			 (int)(screenSize.height - faceSize.getHeight())/2) ;
		this.setResizable(false) ;
		//设置标题
		this.setTitle("聊天室服务器") ;  
		this.show(true) ;

		//设置快捷键
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

		//组合菜单
		file.add(startItem) ;
		file.add(endItem) ;
		file.addSeparator() ;
		file.add(exitItem) ;
		helpMenu.add(helpItem) ;
		Jmenubar.add(file) ;
		Jmenubar.add(helpMenu) ;
		//初始化，停止服务按钮不可用
		endItem.setEnabled(false) ;
		setJMenuBar (Jmenubar);

		//为菜单添加事件监听 
		startItem.addActionListener(this) ;
		endItem.addActionListener(this) ;
		exitItem.addActionListener(this) ;
		helpItem.addActionListener(this) ;

		//设置显示消息Area只读
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
		sendMessageButton = new JButton("发送") ;
		sendMessageButton.setEnabled(false) ;
		//添加消息事件
		editMessage.addActionListener(this) ;
		sendMessageButton.addActionListener(this) ;

		messageLabel = new JLabel("发送消息:") ;
		panel = new JPanel() ;
		gridBag = new GridBagLayout() ;
		panel.setLayout(gridBag) ;
		
		//布局的设置
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


	//事件处理
	public void actionPerformed(ActionEvent e)
	{
		//最初发生 Event 的对象。
		Object obj = e.getSource() ;
		if (obj == startItem)
		{
			startService() ;
		}
		else if (obj == endItem)
		{
			int flag = JOptionPane.showConfirmDialog(
				this,"确定停止服务器？" ,"停止服务器",
				JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE );
			if (flag == JOptionPane.YES_OPTION)
			{
				stopService() ;
			}
		}
		else if (obj == exitItem)
		{
			int flag = JOptionPane.showConfirmDialog(
				this,"是否要退出？" , "退出" , JOptionPane.YES_OPTION
				,JOptionPane.QUESTION_MESSAGE) ;
			if (flag == JOptionPane.YES_OPTION)
			{
				stopService() ;
				System.exit(0) ;
			}
		}
		else if (obj == helpItem)
		{
			//帮助。。。。。。
			new  ServerHelp(this).setVisible(true) ;  
		}
		else if (obj == editMessage || obj == sendMessageButton)
		{
			sendSystemMessage() ;
		}
	}
	//启动服务器
	public void startService()
	{
		try
		{
			serverSocket = new ServerSocket(port,100) ;
			showMessage.append("服务器已经启动！\n") ;
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
	//停止服务器
	public void stopService()
	{
		try
		{
			startItem.setEnabled(true) ;
			endItem.setEnabled(false) ;
			editMessage.setEnabled(false) ;
			sendMessageButton.setEnabled(false) ;
			//发送服务器关闭的提示语
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
			showMessage.append("服务器已经关闭\n") ;

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
				node.oos.writeObject("服务关闭") ;
				node.oos.flush() ;
			}
			catch (Exception e){}
		}
	}
	//发送消息给所有人
	public void sendMessageToAll(String message)
	{
		int count = userLinkList.getCount() ;
		for (int i = 0; i < count ; ++ i )
		{
				LNode node = userLinkList.findUser(i) ;
				if (node == null) continue ;
				try
				{
					node.oos.writeObject("系统消息") ;
					node.oos.flush() ;
					node.oos.writeObject(message) ; 
					node.oos.flush() ;
				}
				catch (Exception e){}
		}
		editMessage.setText("") ;
	}
	//向客户端发送消息
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
