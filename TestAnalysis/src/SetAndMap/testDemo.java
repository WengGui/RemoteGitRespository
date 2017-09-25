package SetAndMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import java.util.Set;

public class testDemo {

	public static void main(String[] args) {
		
		Map<String,String> map = new HashMap<String,String>();
		
//		map.put("02", "lisi");
//		map.put("03", "wangwu");
//		map.put("04", "wangwu");
//		map.put("01", "234234");
//		map.put("01", "zhangsan");
//		System.out.println(map.get("01"));
	
//		System.out.println(map.toString());
//		System.out.println("************************");
//		for (Entry<String, String> entry : map.entrySet()) {
//			System.out.println(entry.getKey()+" >>> "+entry.getValue());
//		}
//		System.out.println("************************");
//		Collection<String> collection = map.values();//返回值是个值的Collection集合
//		System.out.println(collection);
//		System.out.println("************************");
//		Set<String> keySet = map.keySet();//先获取map集合的所有键的Set集合
//        Iterator<String> it = keySet.iterator();//有了Set集合，就可以获取其迭代器。
//        while(it.hasNext()){
//        	String key = it.next();
//        	System.out.println(key+" >>> "+ map.get(key));
//			
//		}
//        System.out.println("************************");
//        
//        Set <Entry<String,String>>conds=map.entrySet();
//        for (Entry<String, String> entry : conds) {
//			System.out.println(entry.getKey()+ " ...... "+ entry.getValue());
//		}
//		Map<String,String[]> map = new HashMap<String,String[]>();
//		map.put("type", new String[] { "4", "" });
//		Set <Entry<String,String[]>>conds=map.entrySet();
//		 for(Entry<String,String[]> e:conds){
//			 System.out.println(e.getKey()+" >>>>> "+e.getValue()[0]);
//		 }
		
		String string = "2012-1-23 15:34:25  %T_T%   g";
		string = string.replaceAll("T", " ");
		System.out.println(string);
	}

}
