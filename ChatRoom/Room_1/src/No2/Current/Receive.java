package No2.Current;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * �����̣߳����ڽ�������
 */
public class Receive implements Runnable{
	//�ܵ�������������
    private DataInputStream dis ;
    //�̱߳�ʶ
    private boolean isRunning = true;
	
    public Receive()    {   
    }   
    public Receive(Socket client) {
        try {
            dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dis);
        }
    }
    
    //�������ݵķ���
    public String receive(){
    	String msg = "";
    	try {
            msg = dis.readUTF();
        } catch (IOException e) {
            isRunning = false;
            CloseUtil.closeAll(dis);
        }
        return msg;
    }
    
	
	@Override
	public void run() {
		while(isRunning){
            System.out.println(receive());
        }   
	}
	
}
