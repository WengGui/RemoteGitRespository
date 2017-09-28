package No2.Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import No2.Current.Receive;
import No2.Current.Send;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("localhost",9999);
		new Thread(new Send(client)).start();
		new Thread(new Receive(client)).start();
	}
}
