//package ChatClient ;

import java.awt.*;
import java.awt.event.*; 
import java.io.*; 
import javax.swing.*;
import javax.swing.event.*; 
import java.util.*;
import java.net.*;

public class  UserSet extends JDialog
{
	/*
		�û�������Ϣ��
		�¼�����
	*/
	private final int WIDTH = 300 ;
	private final int HEIGHT = 120 ;
	JPanel panelUserSet = new JPanel () ;
	JButton save = new JButton("����") ;
	JButton cancel = new JButton("ȡ��") ;
	JPanel panelSave = new JPanel() ;
	JLabel message = new JLabel() ;
	String userInputName ;
	JLabel label = new JLabel (
			"                       ��д��ȷ���û����֡�") ;

	JTextField userName ;
	public UserSet (JFrame frame , String str)
	{
		super(frame , true) ;
		this.userInputName = str ;
		try
		{
			jbInit () ;
		}
		catch (Exception e) { } 
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize() ;
		this.setLocation((int )(screenSize.width - 400) / 2 + 15 ,
			(int)(screenSize.height - 600)/2 + 150) ;
		this.setResizable(false) ;
	}
	private void jbInit() throws Exception
	{
		this.setSize(new Dimension(WIDTH , HEIGHT)) ;
		this.setTitle("�û�����") ;
		message.setText("�������û���:") ;
		userName = new JTextField(10) ;
		userName.setText(userInputName) ;
		
		panelUserSet.setLayout(new FlowLayout()) ;
		panelUserSet.add(message) ; 
		panelUserSet.add(userName) ;

		panelSave.add(new Label("                ")) ;
		panelSave.add(save) ;
		panelSave.add(cancel) ;
		panelSave.add(new Label("                ")) ;

		Container contentPane = getContentPane() ;
		contentPane.setLayout(new BorderLayout() ) ;
		contentPane.add(panelUserSet , BorderLayout.NORTH) ;
		contentPane.add(label) ;
		contentPane.add(panelSave,BorderLayout.SOUTH) ;

		//�¼�����
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a)
			{
				if (userName.getText().equals(""))
				{
					/*
					int flag = JOptionPane.showConfirmDialog(a,
					"�û�������Ϊ�ա������������û�����","��ʾ" ,
					JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE) ; 
					*/
				}
				else if (userName.getText().length() > 15)
				{ 
					/*
					int flag = JOptionPane.showConfirmDialog(a,
					"�û������Ȳ��ܴ���15���ַ�!","��ʾ" ,
					JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE) ; 
					*/
				}
				userInputName = userName.getText() ;
				dispose() ;
			}
		});

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{ 
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
