package No3.Util;

import java.io.Closeable;

public class CloseUtil {
	public static void closeAll(Closeable ...io){
		for (Closeable closeable : io) {
			if(null == closeable){
				try {
					closeable.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
