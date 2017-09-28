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
 * ����������������
 * 1������������ ָ���˿� ServerSocket(int port)
 * 2�����տͻ��˵�����  ����ʽ
 * 3����������+��������
 * 
 * ���ն���ͻ���
 * ���������������ͬһ���߳��ڣ��������Ӧ���� ��������������˴˶���
 */
@SuppressWarnings("all")
public class MultiServer {
	public static void main(String[] args) throws IOException {
		//1������������ ָ���˿�
		int port = 8888;
		ServerSocket server = new ServerSocket(port);
		System.out.println("�������Ѿ�����");
		while (true){	//��ѭ�� һ��accept һ���ͻ���
			//2�����տͻ��˵�����
			Socket client = server.accept();
			System.out.println("һ���ͻ��˽�������");
			
			//д����
			//������
			DataInputStream dis = new DataInputStream(client.getInputStream());
			String msg = dis.readUTF();
			System.out.println("�������յ�"+msg);
			
			
			//3����������
			String msg2 = "��ӭʹ��";
//			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
//			BufferedWriter bw = new BufferedWriter(out);
//			bw.write(msg);
//			bw.newLine();//һ��Ҫ���н���������Ȼ����������
//			bw.flush();
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			System.out.println("������");
			String bfr = new BufferedReader(new InputStreamReader(System.in)).readLine();
			dos.writeUTF(msg2);
			dos.flush();
		}
	}
}	
