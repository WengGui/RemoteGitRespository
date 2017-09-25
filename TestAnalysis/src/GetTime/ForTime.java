package GetTime;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ForTime {
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";  
	
	public static String getLocalTime(String format){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String time = dateFormat.format(calendar.getTime());	
		return time;
	}
	
	public static String getNetWorkTime(String format,String url){
		try {
			URL url2=new URL(url);	// 获取url对象  
			URLConnection connection = url2.openConnection();// 获取生成连接对象 
			connection.connect();// 发出连接请求  
			long dataL = connection.getDate();// 读取网站日期时间  
			Date date = new Date(dataL);// 转化为时间对象  
			SimpleDateFormat time = new SimpleDateFormat( format != null ? format : DEFAULT_FORMAT, Locale.CHINA);// 输出北京时间
			return time.format(date);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		String format="YYYY-MM-dd HH:mm:ss";
		String url = "http://www.baidu.com";// bjTime北京时间
		String time;
//		time = getLocalTime(format);
		time = getNetWorkTime(format, url);
		
		System.out.println(time);
	}
}
