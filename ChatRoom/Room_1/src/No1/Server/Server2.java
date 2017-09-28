package No1.Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import No1.Client.Client;

public class Server2 {
	public static final int PORT = 12345;//监听的端口号  
	
	public static void main(String[] args) {
		System.out.println("服务器启动...\n");
		Server2 server = new Server2();
		server.init();
	}
	
	public void init(){
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while(true){
				// 一旦有堵塞, 则表示服务器与客户端获得了连接    
				Socket client = serverSocket.accept();    
				try {
					// 读取客户端数据    
	                DataInputStream input = new DataInputStream(client.getInputStream());  
	                String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFExceptio
	                // 处理客户端数据    
	                System.out.println("客户端发过来的内容:" + clientInputStr);
	                
	                // 向客户端回复信息    
	                DataOutputStream out = new DataOutputStream(client.getOutputStream());    
	                System.out.print("请输入:\t");    
	                // 发送键盘输入的一行    
	                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();    
	                out.writeUTF(s);
	                
	                out.close();    
	                input.close(); 
				} catch (Exception e) {
					 System.out.println("服务器 run 异常: " + e.getMessage());  
				}finally {
					 if (client != null) {    
		                    try {    
		                    	client.close();    
		                    } catch (Exception e) {    
		                    	client = null;    
		                        System.out.println("服务端 finally 异常:" + e.getMessage());    
		                    }    
					 }
				}	
			}
//			new HandlerThread(client);
		} catch (Exception e) {
			System.out.println("服务器异常: " + e.getMessage());
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
//				// 读取客户端数据    
//                DataInputStream input = new DataInputStream(socket.getInputStream());  
//                String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFExceptio
//                // 处理客户端数据    
//                System.out.println("客户端发过来的内容:" + clientInputStr);
//                
//                // 向客户端回复信息    
//                DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
//                System.out.print("请输入:\t");    
//                // 发送键盘输入的一行    
//                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();    
//                out.writeUTF(s);
//                
//                out.close();    
//                input.close(); 
//			} catch (Exception e) {
//				 System.out.println("服务器 run 异常: " + e.getMessage());  
//			}finally {
//				 if (socket != null) {    
//	                    try {    
//	                        socket.close();    
//	                    } catch (Exception e) {    
//	                        socket = null;    
//	                        System.out.println("服务端 finally 异常:" + e.getMessage());    
//	                    }    
//				 }
//			}
//		}
//	}
}
