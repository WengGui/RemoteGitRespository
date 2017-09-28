package No2.Current;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;

public class CloseUtil {
	public static void closeAll(Closeable ... io){
		for (Closeable temp : io) {
			if(null==temp){
				try {
                    temp.close();
                }catch (IOException e){
                	e.printStackTrace();
                }
			}
		}
	}
}
