package No1.Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 1�������ͻ��� ����ָ��������+�˿�  ��ʱ��������
 * Socket(String host,int port)
 * 2����������+��������
 */
public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		//1�������ͻ��� ����ָ��������+�˿�  ��ʱ��������
		Socket client = new Socket("localhost",8888);
		//2����������
//		InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
//		BufferedReader br = new BufferedReader(inputStreamReader);
//		String echo = br.readLine();//����ʽ����
//		System.out.println(echo);
		while(true){
			DataInputStream dis = new DataInputStream(client.getInputStream());
			String echo = dis.readUTF();
			System.err.println(echo);
		}
	}
}
