package ForSerializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** 
 *Title:应用学生类 
 *Description:实现学生类实例的序列化与反序列化 
 *Copyright: copyright(c) 2012 
 *Filename: UseStudent.java 
 *@author Wang Luqing 
 *@version 1.0 
 */  
public class SerializableDemo {

	public void toSerializable() throws ClassNotFoundException{
		Student st = new Student("翁",'M',20,3.6);
		File file = new File("student.txt");
		try {
			file.createNewFile();
			//Student对象序列化过程
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(st);
			oos.flush();
			oos.close();
			fos.close();
		} catch (IOException  e) {
			e.printStackTrace();
		}   
	}
	
	public void deSerializable()throws ClassNotFoundException{
		//Student对象反序列化过程
		 
		File file = new File("student.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Student st = (Student) ois.readObject();
			System.out.println(st.toString());
			ois.close();  
			fis.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
