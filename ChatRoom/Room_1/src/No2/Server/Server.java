package No2.Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �������Ȼ�������⣬
 * ��������ֻ�ܹ�һ��һ���Ľ��գ�����Ҫ�ȵ���һ��ִ���꣬���ܽ�����һ����
 * ������ν���Ⱥ�˳�򣬲����߱����̵߳Ĺ��ܡ�
 * �������Ҳ��Ҫ�Է��������ж��̡߳�
 * @author weng
 *
 */
public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(9999);
		while (true) {
			Socket client = server.accept();
			//д����
			//������
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			while(true){
				String meg = dis.readUTF();
				System.out.println("�������յ������ݣ�"+meg);
				String bf=new BufferedReader(new InputStreamReader(System.in)).readLine();
				System.out.println("������.....");
				dos.writeUTF("���������ص�"+bf);
				dos.flush();
			}
			
		}
	}
}
