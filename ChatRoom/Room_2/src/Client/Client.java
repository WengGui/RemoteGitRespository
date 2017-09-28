
//import ChatClient.* ;
import java.awt.*;
import java.awt.event.*; 
import java.io.*; 
import javax.swing.*;
import javax.swing.event.*; 
import java.util.*;
import java.net.*;

/*
	�ͻ��������.
*/

public class Client extends JFrame implements ActionListener 
{
	private final int CLIENT_WIDHT = 400 ;
	private final int CLIENT_HETGHT = 450 ; 

	String ip = "218.75.210.25" ;
	int port = 30000 ; //�˿�
	String userName = ""  ;
	int login = 0 ;  // 0Ϊ�Ͽ���1Ϊ���ӡ�

	JComboBox userComboBox , actionList;

	JTextArea messageShow ;  
	JScrollPane messageScrollPane ;
	JTextField editMessage  ;
	JButton sendMessageButton ; 

	JLabel messageLabel , express , sendToLabel; 
	
	JTextField showStatus ;

	//�˵����
	JMenuBar menuBar = new JMenuBar();
	JMenu operateMenu = new JMenu("����(O)") ; 
	JMenuItem startItem = new JMenuItem("�û���¼(Q)") ;
	JMenuItem stopItem = new JMenuItem("�û�ע��(C)") ; 
	JMenuItem exitItem = new JMenuItem("�˳�(X)") ;
	
	JMenu helpMenu = new JMenu("����(H)") ;
	JMenuItem helpItem = new JMenuItem("����(H)") ;

	JMenu conMenu = new JMenu("����(O)") ;
	JMenuItem userItem = new JMenuItem("�û�����(I)") ;
	JMenuItem setIpItem = new JMenuItem("��ַ����(L)") ;
	//����������
	JToolBar toolBar = new JToolBar() ;
	JButton loginButton = new JButton("�û���¼") ;
	JButton logoffButton = new JButton("ע��") ;
	JButton userSetButton = new JButton("�û�����") ;
	JButton exitButton = new JButton ("�˳�") ;

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
		//��������λ�� ;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(screenSize.width - ClientFaceSize.getWidth()) / 2 ,
			(int)(screenSize.height - ClientFaceSize.getHeight())/2) ;
		this.setResizable(false) ;
		this.setTitle("����ͻ���") ;
		show() ;

		//���ÿ�ݼ�
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
	//��ʼ��
	public void init()
	{
		/*
			������������
		*/
		Container contentPane = getContentPane() ;
		contentPane.setLayout(new BorderLayout()) ;

		//��Ӳ˵���
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

		//��ӹ�����
		toolBar.add(userSetButton) ;
		toolBar.addSeparator() ;
		toolBar.add(loginButton) ;
		toolBar.add(logoffButton) ;
		toolBar.addSeparator() ;
		toolBar.add(exitButton) ;
		contentPane.add(toolBar,BorderLayout.NORTH) ;

		//��Ӽ����¼�
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

		//��ӱ���
		actionList = new JComboBox() ;
		actionList.addItem("΢Ц��") ;
		actionList.addItem("���˵�") ;
		actionList.addItem("���Ƶ�") ;
		actionList.addItem("а���") ;
		actionList.addItem("������") ; 

		userComboBox = new JComboBox() ;
		userComboBox.insertItemAt("������",0) ;
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
		sendMessageButton.setText("����") ;
		sendMessageButton.setEnabled(false) ;
		//��Ӽ����¼�
		editMessage.addActionListener(this) ;
		sendMessageButton.addActionListener(this) ;

		sendToLabel = new JLabel("������:") ;
		express = new JLabel("    ����:  ") ;
		messageLabel = new JLabel("������Ϣ:") ;
		downPanel = new JPanel() ;
		gridBag = new GridBagLayout() ;
		downPanel.setLayout(gridBag) ;
		
		//���ֹ���
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
					//�Ͽ�����
				}
				System.exit(0) ;
			}
		}) ;
	}
	/*
		�¼�����
	*/
	public void actionPerformed(ActionEvent e)
	{
		Object obj = e.getSource() ;//������� Event �Ķ���

		if (obj == userItem || obj == userSetButton)
		{
			//�����û���Ϣ���öԻ���
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
			//������Ϣ
			sendMessage() ; 
			editMessage.setText("") ;
		}
		else if (obj == exitButton || obj == exitItem)
		{
			int flag = JOptionPane.showConfirmDialog(this,
				"�Ƿ�ȷ���˳���" , "�˳�" ,
				JOptionPane.YES_OPTION , JOptionPane.QUESTION_MESSAGE) ;
			if (flag == JOptionPane.YES_OPTION)
			{
				if (login == 1 )
				{
					//�Ͽ�����
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
			//����.
		}
	
	}
	//���ӷ�����
	public void Connect()
	{
		/*
			1�����ӷ�������
			2���������������������̡߳�
			3���ж��û����Ƿ�Ϊ�ա�
			4��ת�䰴�������ԡ�

		*/
		try
		{
			socket = new Socket(ip , port) ;
		}
		catch (Exception e)
		{
			JOptionPane.showConfirmDialog(this,
				"�������ӵ�ָ���ķ�����.\n ��ȷ�����������Ƿ���ȷ.","��ʾ",
				JOptionPane.DEFAULT_OPTION , JOptionPane.WARNING_MESSAGE) ;
			return ;
		}
		try
		{
			if (userName == "")
			{
				int flag = JOptionPane.showConfirmDialog(this,
					"�û���Ϊ�ա������������û�����","��ʾ" ,
					JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE) ;
				if (flag == JOptionPane.YES_OPTION)
				{
					//�û�����
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

			messageShow.append("���ӷ�����"+ ip + ":" + port + "�ɹ�!\n") ;
			login = 1 ;
		}
		catch (Exception e){return;}
	}
	//�Ͽ�����
	public void DisConnect()
	{
		/*
			1����ʼ��������
			2����ʾ�û�����.
			3���ر����롢�������
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
					output.writeObject("�û�����");
					output.flush();
			
					input.close();
					output.close();
					socket.close();
					messageShow.append("�Ѿ���������Ͽ�����...\n");
					login = 0;//��־λ��Ϊδ����
			}
			catch (Exception e){}
	}
	public void sendMessage()
	{
		/*
			1������˭��
			2�����顣
			3��������Ϣ��
		*/
		String toSomebody = userComboBox.getSelectedItem().toString() ;
		String status = "" ;
		String action = actionList.getSelectedItem().toString() ;
		String message = editMessage.getText() ;
		messageShow.append("[��] �� "+toSomebody+" ˵ ��" + message + "\n") ;
		if (socket.isClosed())
		{
			return ;
		}
		try
		{
			output.writeObject("������Ϣ") ;
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
