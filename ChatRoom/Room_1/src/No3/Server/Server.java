package No3.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import No3.Util.CloseUtil;

/**
 * ���̷߳�����
 * @author weng
 *
 */
/**
 * ����������
 */
public class Server {
	private List<MyChannel> all = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		new Server().start();
	}

	public void start() throws IOException {
		ServerSocket server = new ServerSocket(9999);
		while (true) {
			Socket client = server.accept();
			MyChannel channel = new MyChannel(client);
			all.add(channel);// ͳһ����
			new Thread(channel).start();// һ����·
		}
	}

	/**
	 * �ڲ��� һ���ͻ��� һ����· 1�������� 2������� 3���������� 4����������
	 * 
	 * @author Administrator
	 *
	 */
	class MyChannel implements Runnable {
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning = true;
		private String name;

		public MyChannel() {
		}

		// ��ʼ��
		public MyChannel(Socket client) {
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());

				this.name = dis.readUTF();
				// System.out.println(this.name);
				this.send("��ӭ����������");

				sendOthers(this.name + " ������������", true);
			} catch (IOException e) {
				CloseUtil.closeAll(dos, dis);
				isRunning = false;
			}
		}

		// ��������
		private String receive() {
			String msg = "";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				CloseUtil.closeAll(dis);
				isRunning = false;
				all.remove(this);// �Ƴ�����
			}
			return msg;

		}

		// ��������
		private void send(String msg) {
			if (null == msg || msg.equals("")) {
				return;
			}
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				CloseUtil.closeAll(dos);
				isRunning = false;
				all.remove(this);// �Ƴ�����
			}
		}

		// ���͸������ͻ���
		private void sendOthers(String msg, boolean sys) {
			// �Ƿ�Ϊ˽�� Լ��
			if (msg.startsWith("@") && msg.indexOf(":") > -1) {
				// ��ȡname
				String name = msg.substring(1, msg.indexOf(":"));
				String contant = msg.substring(msg.indexOf(":") + 1);
				for (MyChannel other : all) {
					if (other.name.equals(name)) {
						other.send(this.name + "�������ĵ�˵��" + contant);
					}
				}
			} else {
				for (MyChannel other : all) {
					if (other == this) {
						continue;
					}

					if (sys == true){// ϵͳ��Ϣ
						other.send("ϵͳ��Ϣ��" + msg);
					} else {
						// ���������ͻ���
						other.send(this.name + " ��������˵:" + msg);
					}
				}
			}
		}

		@Override
		public void run() {
			while (isRunning) {
				sendOthers(receive(), false);
			}
		}
	}
}