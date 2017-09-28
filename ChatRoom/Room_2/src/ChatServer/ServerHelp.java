import javax.swing.* ; 
import javax.swing.event.* ;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.io.*;
public class ServerHelp  extends JDialog
{

	private final int HELP_WIDHT = 400 ;
	private final int HELP_HETGHT = 200 ;

	JPanel titlePanel = new JPanel() ;
	JPanel closePanel = new JPanel() ;
	JPanel helpPanel = new JPanel() ;

	JTextArea helpArea = new JTextArea() ;
	JLabel title = new JLabel ("服务器帮助") ;
	JButton closeButton = new JButton ("关闭") ;

	public ServerHelp(JFrame frame)
	{
		super(frame,true) ;
		try
		{
			jbInit() ;
		}
		catch (Exception e){}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation((int)(screenSize.width - 400 )/2,
			(int)(screenSize.height - 320)/2) ;
		this.setResizable(false) ;
	}
	public void jbInit () throws Exception
	{
		this.setSize(new Dimension(HELP_WIDHT,HELP_HETGHT)) ;
		this.setTitle("帮助") ;
		helpArea.setText("1、服务器分为菜单、消息显示、消息编辑；\n"
						+"2、通过"+"服务(V)"+" -> "+"启动服务器(S)"+" 来启动服务器。\n"
						+"3、通过"+"服务(V)" +"->"+"停止服务器(E)"+" 来关闭服务器.\n" 
						) ;
		helpArea.setEditable(false) ;

		titlePanel.add(new JLabel("      ")) ;
		titlePanel.add(title) ;
		titlePanel.add(new JLabel("      ")) ;

		helpPanel.add(helpArea) ;

		closePanel.add(new JLabel("      ")) ;
		closePanel.add(closeButton) ;
		closePanel.add(new JLabel("      ")) ;

		Container helpPane = getContentPane() ;
		helpPane.setLayout(new BorderLayout()) ;
		helpPane.add(titlePanel,BorderLayout.NORTH) ;
		helpPane.add(helpPanel) ;
		helpPane.add(closePanel,BorderLayout.SOUTH) ;

		this.addWindowListener(new WindowAdapter(){
			public void windowClosiong(WindowEvent e)
			{
				System.exit(0) ;
			}
		}) ;

		closeButton.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
		});
	} 
}
/* 
	
*/