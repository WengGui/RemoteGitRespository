/**
 * ”√ªß¡¥±Ì
 */
public class UserLinkList {
	LNode root;
	LNode pointer;
	int count;
	 
	public UserLinkList(){
		root = new LNode();
		root.next = null;
		pointer = null;
		count = 0;
	}
	 
	public void addUser(LNode n){
		pointer = root;
		
		while(pointer.next != null){
			pointer = pointer.next;
		}
		
		pointer.next = n;
		n.next = null;
		count++;
		
	} 
	public void delUser(LNode n){
		pointer = root;
		
		while(pointer.next != null){
			if(pointer.next == n){
				pointer.next = n.next;
				count --;
				
				break;
			}
			
			pointer = pointer.next;
		}
	}
 
	public int getCount(){
		return count;
	}
 
	public LNode findUser(String username){
		if(count == 0) return null;
		
		pointer = root;
		
		while(pointer.next != null){
			pointer = pointer.next;
			
			if(pointer.username.equalsIgnoreCase(username)){
				return pointer;
			}
		}
		
		return null;
	}
 
	public LNode findUser(int index){
		if(count == 0) {
			return null;
		}
		
		if(index <  0) {
			return null;
		}
		
		pointer = root;
		
		int i = 0;
		while(i < index + 1){
			if(pointer.next != null){
				pointer = pointer.next;
			}
			else{
				return null;
			}
			
			i++;
		}
		
		return pointer;
	}
}
