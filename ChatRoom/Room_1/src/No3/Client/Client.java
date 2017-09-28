package No3.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import No3.Util.Receive;
import No3.Util.Send;

/**
 * �����ͻ��ˣ���������+��������
 * д�����ݣ������
 * ��ȡ���ݣ�������
 * ���������������ͬһ���߳��� Ӧ�ö�������
 * ��������
 */
public class Client {
	public static void main(String[] args) throws IOException {
		System.out.println("�������û�����");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name = br.readLine();
        if(name.equals(""))
        {
            return;
        }
        Socket client = new Socket("localhost",9999);
        new Thread(new Send(client,name)).start();//һ��·��
        new Thread(new Receive(client)).start();//һ��·��
	}
}
