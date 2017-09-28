package No2.Current;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable{
	
    private BufferedReader console;	//����̨��������
    private DataOutputStream dos; 	//�ܵ������������  
    private boolean isRunning = true;	 //�����̱߳�ʶ
    
    //��ʼ��
    public Send() {
        console = new BufferedReader(new InputStreamReader(System.in)); 
    }
    public Send(Socket client){
    	this();
    	try {
			dos = new DataOutputStream(client.getOutputStream());
		} catch (Exception e) {
		}
    }
    
    //1���ӿ���̨��������
    private String getMesFromConsole(){
    	try {
			return console.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return "";
    }
    
    /**
     * 1���ӿ���̨��������
     * 2����������
     */
    public void send(){
    	String msg = getMesFromConsole();
    	try {
    		if(null!=msg&& !msg.equals("")){
    			dos.writeUTF(msg);
                dos.flush();//ǿ��ˢ��
    		}
		} catch (Exception e) {
			isRunning = false;					//����ʧ�ܣ���ʾ�ر��߳�
			CloseUtil.closeAll(dos,console);	//������ܷ��ͳɹ���ֱ�ӹر�����
		}
    }
    
    
    @Override
	public void run() {
		while(isRunning){
			send();
		}
	} 
}
