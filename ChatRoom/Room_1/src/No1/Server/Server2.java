package No1.Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import No1.Client.Client;

public class Server2 {
	public static final int PORT = 12345;//�����Ķ˿ں�  
	
	public static void main(String[] args) {
		System.out.println("����������...\n");
		Server2 server = new Server2();
		server.init();
	}
	
	public void init(){
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while(true){
				// һ���ж���, ���ʾ��������ͻ��˻��������    
				Socket client = serverSocket.accept();    
				try {
					// ��ȡ�ͻ�������    
	                DataInputStream input = new DataInputStream(client.getInputStream());  
	                String clientInputStr = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFExceptio
	                // ����ͻ�������    
	                System.out.println("�ͻ��˷�����������:" + clientInputStr);
	                
	                // ��ͻ��˻ظ���Ϣ    
	                DataOutputStream out = new DataOutputStream(client.getOutputStream());    
	                System.out.print("������:\t");    
	                // ���ͼ��������һ��    
	                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();    
	                out.writeUTF(s);
	                
	                out.close();    
	                input.close(); 
				} catch (Exception e) {
					 System.out.println("������ run �쳣: " + e.getMessage());  
				}finally {
					 if (client != null) {    
		                    try {    
		                    	client.close();    
		                    } catch (Exception e) {    
		                    	client = null;    
		                        System.out.println("����� finally �쳣:" + e.getMessage());    
		                    }    
					 }
				}	
			}
//			new HandlerThread(client);
		} catch (Exception e) {
			System.out.println("�������쳣: " + e.getMessage());
		}
	}
	
//	private class HandlerThread implements Runnable {
//		private Socket socket;
//		public HandlerThread(Socket client){
//			socket = client;
//			new Thread(this).start();
//		}
//		@Override
//		public void run() {
//			try {
//				// ��ȡ�ͻ�������    
//                DataInputStream input = new DataInputStream(socket.getInputStream());  
//                String clientInputStr = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFExceptio
//                // ����ͻ�������    
//                System.out.println("�ͻ��˷�����������:" + clientInputStr);
//                
//                // ��ͻ��˻ظ���Ϣ    
//                DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
//                System.out.print("������:\t");    
//                // ���ͼ��������һ��    
//                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();    
//                out.writeUTF(s);
//                
//                out.close();    
//                input.close(); 
//			} catch (Exception e) {
//				 System.out.println("������ run �쳣: " + e.getMessage());  
//			}finally {
//				 if (socket != null) {    
//	                    try {    
//	                        socket.close();    
//	                    } catch (Exception e) {    
//	                        socket = null;    
//	                        System.out.println("����� finally �쳣:" + e.getMessage());    
//	                    }    
//				 }
//			}
//		}
//	}
}
