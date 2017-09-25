package Analysis.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Ll_UserAction {
	private String product = "产品号码";
	private String user_name = "客户名称";
	private Integer inputrate =453;
	private String mem = "men数据";
	
	
	
	public String queryLl_Users() {
		try {
			StringBuffer condition=new StringBuffer();			
			if (this.product != null && this.product.trim().length() > 0) {
				//conditions.put("name", new String[] { "0", this.getName().trim() });
				condition.append(" and product like '% 【action名】 %' ");
			}
			if (this.user_name != null && this.user_name.trim().length() > 0) {
				//conditions.put("name", new String[] { "0", this.getName().trim() });
				condition.append(" and user_name like '%"+ this.user_name.trim()+"%' ");
			}
			if(this.inputrate!=null && inputrate>0){
				condition.append(" and inputrate="+ this.inputrate+" ");
			}
			if (this.mem != null && this.mem.trim().length() > 0) {
				condition.append(" and mem like '%"+ this.getMem().trim()+"%' ");
			}
			System.out.println(condition.toString());
//			if (this.ips != null && this.ips.trim().length() > 0) {
//				Integer [] ip_node=new Integer[4];
//				ip_node[0]=0;
//				ip_node[1]=0;
//				ip_node[2]=0;
//				ip_node[3]=0;
//				Integer [] ip_node2=new Integer[4];
//				ip_node2[0]=255;
//				ip_node2[1]=255;
//				ip_node2[2]=255;
//				ip_node2[3]=255;
//				String ip_nodes[]=ips.split("\\.");
//				for(int i=0;i<ip_nodes.length;i++){
//					if(ip_nodes[i].trim().length()>0){
//						ip_node[i]=Integer.parseInt(ip_nodes[i]);
//						ip_node2[i]=Integer.parseInt(ip_nodes[i]);
//					}
//				}
//				//condition.append(" and id in(select ll_user_id from ll_user_to_ip where startIp<="+getIp10(StringUtils.join(ip_node, "."))+" and endIp>="+getIp10(StringUtils.join(ip_node2, "."))+")");
//				condition.append(" and id in(select ll_user_id from ll_user_to_ip where startIp<="+getIp10(StringUtils.join(ip_node, "."))+" and endIp>="+getIp10(StringUtils.join(ip_node2, "."))+" or startIp>="+getIp10(StringUtils.join(ip_node, "."))+" and startIp<="+getIp10(StringUtils.join(ip_node2, "."))+" or  startIp<="+getIp10(StringUtils.join(ip_node, "."))+" and endIP>="+getIp10(StringUtils.join(ip_node, "."))+" ) ");
//			}
//			if(this.level_name!=null&&this.level_name.trim().length()>0){
//				condition.append(" and level_name='"+ this.level_name.trim()+"' ");
//			}
//			if(this.adsl!=null&&this.adsl.trim().length()>0){
//				condition.append(" and adsl='"+ this.adsl.trim()+"' ");
//			}
//			//list = pageDao.findByCondition(conditions, this.getStart(), limit, Ll_User.class, "id", true, "id");
//			
//			List<Ll_User> lists=pageDao.findByNativeSQL(" ll_user ", condition.toString(), "", "id", start, limit, desc,Ll_User.class,"id");
//			list=new ArrayList<Ll_User>();
//			if(lists!=null&&lists.size()>0){
//				for (Ll_User ll_User : lists) {
//					list.add(ll_User);
//				}
//			}
//			Set<Integer> sets=new HashSet<Integer>();
//			if(lists!=null&&lists.size()>0){
//				for (Ll_User ll : lists) {
//					if(ll.getLevel_2_id()!=null&&ll.getLevel_2_id()>0){
//						sets.add(ll.getLevel_2_id());
//					}
//					if(ll.getLevel_3_id()!=null&&ll.getLevel_3_id()>0){
//						sets.add(ll.getLevel_3_id());
//					}
//				}
//				Map<Integer,String> maps=new HashMap<Integer, String>();
//				if(sets.size()>0){
//					maps=DBConnect.queryMaps2("select id,nodeName from account_group_tree where id in("+StringUtils.join(sets.toArray(), ",")+")", "id", "nodeName");
//				}
//				sets.clear();
//				for (Ll_User account : lists) {
//					if(account.getLevel_3_id()!=null&&account.getLevel_3_id()>0&&maps.get(account.getLevel_3_id())!=null){
//						account.setLevel_3_str(maps.get(account.getLevel_3_id()));
//					}
//					if(account.getLevel_2_id()!=null&&account.getLevel_2_id()>0&&maps.get(account.getLevel_2_id())!=null){
//						account.setLevel_2_str(maps.get(account.getLevel_2_id()));
//					}
//				}
//				maps.clear();
//			}
//			this.totalSize = pageDao.getTotalSize();
		} catch (Exception e) {
//			e.printStackTrace();
//			this.setSuccess(false);
//			this.setMsg("查找数据出错");
		}
		return "SUCCESS";
	}

	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Integer getInputrate() {
		return inputrate;
	}
	public void setInputrate(Integer inputrate) {
		this.inputrate = inputrate;
	}
	public String getMem() {
		return mem;
	}
	public void setMem(String mem) {
		this.mem = mem;
	}
}



