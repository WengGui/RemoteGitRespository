package No2.Current;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable{
	
    private BufferedReader console;	//控制台的输入流
    private DataOutputStream dos; 	//管道的数据输出流  
    private boolean isRunning = true;	 //控制线程标识
    
    //初始化
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
    
    //1、从控制台接收数据
    private String getMesFromConsole(){
    	try {
			return console.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return "";
    }
    
    /**
     * 1、从控制台接收数据
     * 2、发送数据
     */
    public void send(){
    	String msg = getMesFromConsole();
    	try {
    		if(null!=msg&& !msg.equals("")){
    			dos.writeUTF(msg);
                dos.flush();//强制刷新
    		}
		} catch (Exception e) {
			isRunning = false;					//发送失败，提示关闭线程
			CloseUtil.closeAll(dos,console);	//如果不能发送成功，直接关闭流。
		}
    }
    
    
    @Override
	public void run() {
		while(isRunning){
			send();
		}
	} 
}
