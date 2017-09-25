package FotFTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class MyFTPClient {
	/**
	 * Description: 连接FTP
	 * @param url  FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param user FTP登录账号
	 * @param pass FTP登录密码
	 * @return
	 */
	public FTPClient conClient(String url,int port,String  user, String pass){
		FTPClient ftp = new FTPClient();
		try {
			//创建地址  
			//SocketAddress address = new InetSocketAddress(url, port);
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			//登陆  
			ftp.login(user, pass);
			int reply; 
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) { 
	            ftp.disconnect(); 
	            System.out.println("连接失败");
	            return null;
	        } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ftp;
	}
	/**
	 * Description: 向FTP服务器上传文件
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 */
	public boolean upload(FTPClient ftp,String path, String filename, InputStream input) {
		OutputStream os = null;  
        FileInputStream fis = null;
        try {
        	ftp.changeWorkingDirectory(path);
        	ftp.storeFile(filename,input);
        	
        	input.close(); 
            ftp.logout();
            return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
	}
	/**
	 * 
	 * @param ftp
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @return
	 */
	public boolean downLoad(FTPClient ftp,String remotePath,String fileName,String localPath){
		
		try {
			ftp.changeWorkingDirectory(remotePath);
			FTPFile[] fs = ftp.listFiles();
			//遍历FTP服务器文件名
			System.out.println("遍历FTP服务器文件名");
			for (FTPFile ftpFile : fs) {
				System.out.println(ftpFile.getName());
				if(ftpFile.getName().equals(fileName)){
					//设置下载路径
					File localFile = new File(localPath+"/"+ftpFile.getName());
					
					//通过输出流下载文件
					OutputStream os = new FileOutputStream(localFile);
					ftp.retrieveFile(ftpFile.getName(), os);
					
					os.close();
				}
			}
			ftp.logout(); 
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}//转移到FTP服务器目录
		return false;
	}
	public static void main(String[] args) {
		//连接FTP
		MyFTPClient ftpClient = new MyFTPClient();
		FTPClient client = ftpClient.conClient("192.168.100.17", 21, "weng", "123456");
		
		//测试上传
//		try {
//			//读取本地文件 塞到输入流中上传
//			FileInputStream fis = new FileInputStream("test.txt");
//			InputStream input = fis;
//			//InputStream input = new FileInputStream("test.txt");
//			boolean flag = ftpClient.upload(client, "F:/FTPDown", "test.txt", input);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//测试下载
		try {
			boolean flag = ftpClient.downLoad(client, "/hh/", "222.txt", "F:/");
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
