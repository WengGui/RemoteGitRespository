package No1.Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 1、创建客户端 必须指定服务器+端口  此时就在连接
 * Socket(String host,int port)
 * 2、接收数据+发送数据
 */
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		//1、创建客户端 必须指定服务器+端口  此时就在连接
		Socket client = new Socket("localhost",8888);
		//2、接收数据
//		InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
//		BufferedReader br = new BufferedReader(inputStreamReader);
//		String echo = br.readLine();//阻塞式方法
//		System.out.println(echo);
		while(true){
			DataInputStream dis = new DataInputStream(client.getInputStream());
			String echo = dis.readUTF();
			System.err.println(echo);
		}
	}
}
