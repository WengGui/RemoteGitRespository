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
	JLabel title = new JLabel ("����������") ;
	JButton closeButton = new JButton ("�ر�") ;

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
		this.setTitle("����") ;
		helpArea.setText("1����������Ϊ�˵�����Ϣ��ʾ����Ϣ�༭��\n"
						+"2��ͨ��"+"����(V)"+" -> "+"����������(S)"+" ��������������\n"
						+"3��ͨ��"+"����(V)" +"->"+"ֹͣ������(E)"+" ���رշ�����.\n" 
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