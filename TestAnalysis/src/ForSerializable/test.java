package ForSerializable;

public class test {
	public static void main(String[] args) throws ClassNotFoundException {
		SerializableDemo demo = new SerializableDemo();
//		demo.toSerializable();
//		System.out.println("ok");
		
		demo.deSerializable();
		System.out.println("ok");
	}
}
