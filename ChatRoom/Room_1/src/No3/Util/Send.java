package No3.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable{
	//����̨������
    private BufferedReader console;
    //�ܵ������
    private DataOutputStream dos;
    //�����̱߳�ʶ
    private boolean isRunning = true;
    //����
    private String name;
    
    //��ʼ��
    public Send(){
    	console = new BufferedReader(new InputStreamReader(System.in)); 
    }
    public Send(Socket client,String name){
    	this();
    	try {
			dos = new DataOutputStream(client.getOutputStream());
			this.name = name;
			send(this.name);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    //1���ӿ���̨��������
    private String getMesFromConsole(){
    	try {
            return console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
   
    /**
     * 1���ӿ���̨��������
     * 2����������
     */
    private void  send(String msg){
    	try {
            if(null!=msg&& !msg.equals(""))
            {
                dos.writeUTF(msg);
                dos.flush();//ǿ��ˢ��
            }
        } catch (IOException e) {
            //e.printStackTrace();
             isRunning = false;
             CloseUtil.closeAll(dos,console);
        }
    }
	@Override
	public void run() {
		while(isRunning)
        {
            send( getMesFromConsole());
        }
	}
	
}
