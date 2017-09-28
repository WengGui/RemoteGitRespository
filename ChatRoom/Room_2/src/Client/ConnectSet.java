
import java.awt.*;
import java.awt.event.*; 
import java.io.*; 
import javax.swing.*;
import javax.swing.event.*; 
import java.util.*;
import java.net.*;

public class ConnectSet extends JDialog 
{
	
	private final int WIDTH = 300 ;
	private final int HEIGHT = 120 ;
	String ip ;
	JPanel panel = new JPanel() ;
	JPanel panelSave = new JPanel() ;

	JLabel message = new JLabel();
	JTextField setIp ;
	JButton save = new JButton("ȷ��");
	JButton cancel = new JButton("ȡ��") ;
	JLabel label = new JLabel("IP��ʽ��: 127.0.0.1 (��Χ��0.0.0.0 ~ 255.255.255.255) ");
	public ConnectSet(JFrame frame , String sysIp)
	{
		super(frame , true) ;
		this.ip  = sysIp ;
		try
		{
			IPInit( ) ;
		}
		catch (Exception e) {System.out.println("������") ;}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation((int )(screenSize.width - 400) / 2 + 15 ,
			(int)(screenSize.height - 600)/2 + 150) ;
		this.setResizable(false) ;
	}
	public void IPInit ( ) throws Exception 
	{	
		this.setSize(new Dimension(WIDTH , HEIGHT)) ;
		this.setTitle("IP��ַ����") ;
		message.setText("������IP��ַ:") ;
		setIp = new JTextField(10) ;
		setIp.setText(ip) ;
		
		panel.setLayout(new FlowLayout()) ;
		panel.add(message) ; 
		panel.add(setIp) ;

		panelSave.add(new Label("                ")) ;
		panelSave.add(save) ;
		panelSave.add(cancel) ;
		panelSave.add(new Label("                ")) ;

		Container contentPane = getContentPane() ;
		contentPane.setLayout(new BorderLayout() ) ;
		contentPane.add(panel , BorderLayout.NORTH) ;
		contentPane.add(label) ;
		contentPane.add(panelSave,BorderLayout.SOUTH) ;
		
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a)
			{ 
				ip = setIp.getText() ;
				dispose() ;
			}
		});

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{ 
				System.exit(0) ;
			}
		}) ;

		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				dispose() ;
			}
		}) ;
	}
}
