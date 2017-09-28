package No2.Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 这个方仍然存在问题，
 * 服务器端只能够一个一个的接收，必须要等到上一条执行完，才能进入下一条，
 * 存在所谓的先后顺序，并不具备多线程的功能。
 * 因此我们也需要对服务器进行多线程。
 * @author weng
 *
 */
public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(9999);
		while (true) {
			Socket client = server.accept();
			//写数据
			//输入流
			DataInputStream dis = new DataInputStream(client.getInputStream());
			DataOutputStream dos = new DataOutputStream(client.getOutputStream());
			while(true){
				String meg = dis.readUTF();
				System.out.println("服务器收到的数据："+meg);
				String bf=new BufferedReader(new InputStreamReader(System.in)).readLine();
				System.out.println("请输入.....");
				dos.writeUTF("服务器返回的"+bf);
				dos.flush();
			}
			
		}
	}
}
