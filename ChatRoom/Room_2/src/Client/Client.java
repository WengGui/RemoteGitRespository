
//import ChatClient.* ;
import java.awt.*;
import java.awt.event.*; 
import java.io.*; 
import javax.swing.*;
import javax.swing.event.*; 
import java.util.*;
import java.net.*;

/*
	客户端主框架.
*/

public class Client extends JFrame implements ActionListener 
{
	private final int CLIENT_WIDHT = 400 ;
	private final int CLIENT_HETGHT = 450 ; 

	String ip = "218.75.210.25" ;
	int port = 30000 ; //端口
	String userName = ""  ;
	int login = 0 ;  // 0为断开，1为连接。

	JComboBox userComboBox , actionList;

	JTextArea messageShow ;  
	JScrollPane messageScrollPane ;
	JTextField editMessage  ;
	JButton sendMessageButton ; 

	JLabel messageLabel , express , sendToLabel; 
	
	JTextField showStatus ;

	//菜单组件
	JMenuBar menuBar = new JMenuBar();
	JMenu operateMenu = new JMenu("操作(O)") ; 
	JMenuItem startItem = new JMenuItem("用户登录(Q)") ;
	JMenuItem stopItem = new JMenuItem("用户注销(C)") ; 
	JMenuItem exitItem = new JMenuItem("退出(X)") ;
	
	JMenu helpMenu = new JMenu("帮助(H)") ;
	JMenuItem helpItem = new JMenuItem("帮助(H)") ;

	JMenu conMenu = new JMenu("设置(O)") ;
	JMenuItem userItem = new JMenuItem("用户设置(I)") ;
	JMenuItem setIpItem = new JMenuItem("地址设置(L)") ;
	//建立工具栏
	JToolBar toolBar = new JToolBar() ;
	JButton loginButton = new JButton("用户登录") ;
	JButton logoffButton = new JButton("注销") ;
	JButton userSetButton = new JButton("用户设置") ;
	JButton exitButton = new JButton ("退出") ;

	Dimension ClientFaceSize = new Dimension(CLIENT_WIDHT , CLIENT_HETGHT) ;

	JPanel downPanel ;
	GridBagLayout gridBag ;
	GridBagConstraints gridBagCon ; 

	Socket socket ;
	ObjectOutputStream output ; 
	ObjectInputStream input ;

	ClientReceive receiveThread ;

	public Client()
	{
		init() ; 

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		this.pack() ;
		this.setSize(ClientFaceSize) ;
		//设置运行位置 ;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width - ClientFaceSize.getWidth()) / 2 ,
			(int)(screenSize.height - ClientFaceSize.getHeight())/2) ;
		this.setResizable(false) ;
		this.setTitle("聊天客户端") ;
		show() ;

		//设置快捷键
		operateMenu.setMnemonic('O') ;

		startItem.setMnemonic('I') ; 
		startItem.setAccelerator(KeyStroke.getKeyStroke
			(KeyEvent.VK_I , InputEvent.CTRL_MASK)) ;

	}
	/*
	protected void makeButton(Component c , GridBagConstraints gridBagCon )
	{
		girdBag.setConstraints(c , gridBagCon) ;
		gridBag.add(jc) ;
	}
	*/
	//初始化
	public void init()
	{
		/*
			组合所有组件。
		*/
		Container contentPane = getContentPane() ;
		contentPane.setLayout(new BorderLayout()) ;

		//添加菜单栏
		operateMenu.add(startItem) ;
		operateMenu.add(stopItem) ;
		operateMenu.addSeparator() ;
		operateMenu.add(exitItem) ;
		menuBar.add(operateMenu) ;
		conMenu.add(userItem) ;
		conMenu.add(setIpItem) ;
		menuBar.add(conMenu) ;
		helpMenu.add(helpItem) ;
		menuBar.add(helpMenu) ;
		setJMenuBar(menuBar) ;

		//添加工具栏
		toolBar.add(userSetButton) ;
		toolBar.addSeparator() ;
		toolBar.add(loginButton) ;
		toolBar.add(logoffButton) ;
		toolBar.addSeparator() ;
		toolBar.add(exitButton) ;
		contentPane.add(toolBar,BorderLayout.NORTH) ;

		//添加监听事件
		setIpItem.addActionListener(this) ;
		startItem.addActionListener(this) ;
		stopItem.addActionListener(this);
		exitItem.addActionListener(this);
		userItem.addActionListener(this);
		helpItem.addActionListener(this) ;

		loginButton.addActionListener(this);
		logoffButton.addActionListener(this);
		userSetButton.addActionListener(this);
		exitButton.addActionListener(this);

		//添加表情
		actionList = new JComboBox() ;
		actionList.addItem("微笑地") ;
		actionList.addItem("高兴地") ;
		actionList.addItem("郁闷地") ;
		actionList.addItem("邪恶地") ;
		actionList.addItem("生气地") ; 

		userComboBox = new JComboBox() ;
		userComboBox.insertItemAt("所有人",0) ;
		userComboBox.setSelectedIndex(0) ;

		loginButton.setEnabled(true) ;
		logoffButton.setEnabled(false) ;
		startItem.setEnabled(true) ;
		stopItem.setEnabled(false) ;


		messageShow = new JTextArea() ;
		messageShow.setEditable(false) ;
		messageScrollPane = new JScrollPane(messageShow ,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED) ;
		messageScrollPane.setPreferredSize(new Dimension(400,400)) ;
		messageScrollPane.revalidate();

		editMessage = new JTextField(23) ;
		editMessage.setEnabled(false) ;
		sendMessageButton = new JButton() ;
		sendMessageButton.setText("发送") ;
		sendMessageButton.setEnabled(false) ;
		//添加监听事件
		editMessage.addActionListener(this) ;
		sendMessageButton.addActionListener(this) ;

		sendToLabel = new JLabel("发送至:") ;
		express = new JLabel("    表情:  ") ;
		messageLabel = new JLabel("发送消息:") ;
		downPanel = new JPanel() ;
		gridBag = new GridBagLayout() ;
		downPanel.setLayout(gridBag) ;
		
		//布局管理
		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 0 ; 
		gridBagCon.gridy = 0 ;
		gridBagCon.gridwidth = 5 ;
		gridBagCon.gridheight = 2 ; 
		gridBagCon.ipadx = 5;
		gridBagCon.ipady = 5 ;
		JLabel none = new JLabel("   ") ;
		gridBag.setConstraints(none,gridBagCon) ;
		downPanel.add(none) ;

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 0 ; 
		gridBagCon.gridy = 2 ;
		gridBagCon.insets = new Insets(1,0,0,0) ;
		gridBag.setConstraints(sendToLabel,gridBagCon) ;
		downPanel.add(sendToLabel) ;

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 1 ;
		gridBagCon.gridy = 2 ;
		gridBagCon.anchor = GridBagConstraints.LINE_START ;
		gridBag.setConstraints(userComboBox,gridBagCon) ;
		downPanel.add(userComboBox);
		
		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 2 ;
		gridBagCon.gridy = 2 ;
		gridBagCon.anchor = GridBagConstraints.LINE_START ;
		gridBag.setConstraints(express,gridBagCon) ;
		downPanel.add(express);
		
		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 3 ;
		gridBagCon.gridy = 2;
		gridBagCon.anchor = GridBagConstraints.LINE_START ;
		gridBag.setConstraints(actionList,gridBagCon) ;
		downPanel.add(actionList) ;

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 0 ;
		gridBagCon.gridy = 3 ;
		gridBag.setConstraints(messageLabel,gridBagCon) ;
		downPanel.add(messageLabel) ;

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 1 ;
		gridBagCon.gridy = 3 ;
		gridBagCon.gridwidth = 3 ;
		gridBagCon.gridheight = 1 ;
		gridBag.setConstraints(editMessage,gridBagCon) ;
		downPanel.add(editMessage) ;

		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 4 ;
		gridBagCon.gridy = 3 ;
		gridBag.setConstraints(sendMessageButton,gridBagCon) ;
		downPanel.add(sendMessageButton) ;

		showStatus = new JTextField(35) ;
		showStatus.setEditable(false) ;
		gridBagCon = new GridBagConstraints() ;
		gridBagCon.gridx = 0 ;
		gridBagCon.gridy = 5 ;
		gridBagCon.gridwidth = 5 ; 
		gridBag.setConstraints(showStatus,gridBagCon) ;
		downPanel.add(showStatus) ;

		contentPane.add(messageScrollPane) ; 
		contentPane.add(downPanel,BorderLayout.SOUTH) ;

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				if(login == 1)
				{
					//断开连接
				}
				System.exit(0) ;
			}
		}) ;
	}
	/*
		事件处理
	*/
	public void actionPerformed(ActionEvent e)
	{
		Object obj = e.getSource() ;//最初发生 Event 的对象

		if (obj == userItem || obj == userSetButton)
		{
			//调出用户信息设置对话框
				UserSet userSet = new UserSet(this,userName) ;
				userSet.show() ;
				userName = userSet.userInputName ;
		}
		else if (obj == startItem || obj == loginButton)
		{ 
			Connect() ;
		}
		else if (obj == stopItem || obj == logoffButton)
		{ 
			DisConnect() ;
			showStatus.setText("") ;
		}
		else if (obj == editMessage || obj == sendMessageButton)
		{
			//发送消息
			sendMessage() ; 
			editMessage.setText("") ;
		}
		else if (obj == exitButton || obj == exitItem)
		{
			int flag = JOptionPane.showConfirmDialog(this,
				"是否确定退出？" , "退出" ,
				JOptionPane.YES_OPTION , JOptionPane.QUESTION_MESSAGE) ;
			if (flag == JOptionPane.YES_OPTION)
			{
				if (login == 1 )
				{
					//断开连接
					DisConnect() ;
				}
				System.exit(0) ;
			}
		}
		else if (obj == setIpItem)
		{
			ConnectSet setIp = new ConnectSet(this,ip) ; 
			setIp.show() ;
			ip = setIp.ip ;
		}
		else if (obj == helpItem)
		{
			//帮助.
		}
	
	}
	//连接服务器
	public void Connect()
	{
		/*
			1、连接服务器。
			2、建立输出输出流，启动线程。
			3、判断用户名是否为空。
			4、转变按键的属性。

		*/
		try
		{
			socket = new Socket(ip , port) ;
		}
		catch (Exception e)
		{
			JOptionPane.showConfirmDialog(this,
				"不能连接到指定的服务器.\n 请确认连接设置是否正确.","提示",
				JOptionPane.DEFAULT_OPTION , JOptionPane.WARNING_MESSAGE) ;
			return ;
		}
		try
		{
			if (userName == "")
			{
				int flag = JOptionPane.showConfirmDialog(this,
					"用户名为空。请重新输入用户名。","提示" ,
					JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE) ;
				if (flag == JOptionPane.YES_OPTION)
				{
					//用户设置
					UserSet userSet = new UserSet(this,userName) ;
					userSet.show() ;
					userName = userSet.userInputName ;
				}
				else return ;
			}
			output= new ObjectOutputStream(socket.getOutputStream()) ;
			output.flush() ;

			input = new ObjectInputStream(socket.getInputStream()) ;

			output.writeObject(userName) ;
			output.flush() ;

			receiveThread = new ClientReceive(socket , output ,
				input , userComboBox , messageShow , showStatus) ;
			receiveThread.start() ;

			startItem.setEnabled(false) ;
			stopItem.setEnabled(true) ;
			loginButton.setEnabled(false) ;
			logoffButton.setEnabled(true) ;
			userItem.setEnabled(false) ;
			userSetButton.setEnabled(false) ;
			editMessage.setEnabled(true) ;
			sendMessageButton.setEnabled(true) ;

			messageShow.append("连接服务器"+ ip + ":" + port + "成功!\n") ;
			login = 1 ;
		}
		catch (Exception e){return;}
	}
	//断开连接
	public void DisConnect()
	{
		/*
			1、初始化按键。
			2、提示用户下线.
			3、关闭输入、输出流。
		*/

			startItem.setEnabled(true) ;
			stopItem.setEnabled(false) ;
			loginButton.setEnabled(true) ;
			logoffButton.setEnabled(false) ;
			userItem.setEnabled(true) ;
			userSetButton.setEnabled(true) ;
			editMessage.setEnabled(false) ;
			sendMessageButton.setEnabled(false) ;

			if (socket.isClosed())
			{
				return ;
			}
			try
			{
					output.writeObject("用户下线");
					output.flush();
			
					input.close();
					output.close();
					socket.close();
					messageShow.append("已经与服务器断开连接...\n");
					login = 0;//标志位设为未连接
			}
			catch (Exception e){}
	}
	public void sendMessage()
	{
		/*
			1、发给谁。
			2、表情。
			3、发送消息。
		*/
		String toSomebody = userComboBox.getSelectedItem().toString() ;
		String status = "" ;
		String action = actionList.getSelectedItem().toString() ;
		String message = editMessage.getText() ;
		messageShow.append("[我] 对 "+toSomebody+" 说 ：" + message + "\n") ;
		if (socket.isClosed())
		{
			return ;
		}
		try
		{
			output.writeObject("聊天信息") ;
			output.flush() ;
			output.writeObject(toSomebody) ;
			output.flush() ;
			output.writeObject(status) ;
			output.flush() ;
			output.writeObject(action) ;
			output.flush() ;
			output.writeObject(message) ;
			output.flush() ;
		}
		catch (Exception e){}
	}
	public static void main(String[] args) 
	{
		new Client() ;
	}
}
