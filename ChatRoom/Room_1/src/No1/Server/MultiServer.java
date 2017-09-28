package No1.Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 必须先启动再连接
 * 1、创建服务器 指定端口 ServerSocket(int port)
 * 2、接收客户端的连接  阻塞式
 * 3、发送数据+接收数据
 * 
 * 接收多个客户端
 * 输入流与输出流在同一个线程内，因此我们应该让 输入流与输出流彼此独立
 */
@SuppressWarnings("all")
public class MultiServer {
	public static void main(String[] args) throws IOException {
		//1、创建服务器 指定端口
		int port = 8888;
		ServerSocket server = new ServerSocket(port);
		System.out.println("服务器已经启动");
		while (true){	//死循环 一个accept 一个客户端
			//2、接收客户端的连接
			Socket client = server.accept();
			System.out.println("一个客户端建立连接");
			
			//写数据
			//输入流
			DataInputStream dis = new DataInputStream(client.getInputStream());
			String msg = dis.readUTF();
			System.out.println("服务器收到"+msg);
			
			
			//3、发送数据
			String msg2 = "欢迎使用";
//			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
//			BufferedWriter bw = new BufferedWriter(out);
//			bw.write(msg);
//			bw.newLine();//一定要加行结束符，不然读不到数据
//			bw.flush();
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			System.out.println("请输入");
			String bfr = new BufferedReader(new InputStreamReader(System.in)).readLine();
			dos.writeUTF(msg2);
			dos.flush();
		}
	}
}	
