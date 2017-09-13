package com.news.lu.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.news.lu.db.JDBC;

public class Tools {
	
	//注册帐号
	public static List<Map<String,Object>> register(String username, String password,String contact){
		//boolean b = false;
		//根据md5加密加盐算法加密
		String salt = getSalt();
		String pass = md5(password+salt)+salt;
		String sql = "insert user(user_name,user_password,user_contact) values(?,?,?)";
		JDBC.executeUpdate(sql, username, pass, contact);
		
		sql = "select id from user where user_name=? and user_password=?";
		List<Map<String,Object>> ret =  JDBC.executeQuery(sql, username, pass);//数据唯一性，由盐的特性保证
		return ret;
	}
	//登录检查
	public static List<Map<String,Object>> login(String username, String password){
		boolean b = false;
		String sql = "select user_password from user where user_name=?";
		List<Map<String,Object>> list =  JDBC.executeQuery(sql, username);
		List<Map<String, Object>> ret = null;
		if(list != null){
			//循环遍历,看该帐号的所有对应密码是否有符合要求的
			for(Map<String,Object> map : list){
				String pass = (String) map.get("user_password");
				String id = String.valueOf(map.get("id"));//转化为int
				String salt = pass.substring(32);
				String thishash = getHash(password, salt);
				b = thishash.equals(pass);
				if(b) {
					sql = "select id from user where user_name=? and user_password=?";
					ret =  JDBC.executeQuery(sql, username, pass);
				}
			}
		}
		return ret;
	}
	//哈希，用来密码的判断
	public static String getHash(String orign, String salt){
		return md5(orign + salt) + salt;
	}
	//获得盐
	public static String getSalt(){
		return md5(new Date().toString()).substring(0,8);
	}
	//md5
	public static String md5(String str){
		
		String ret = str;
		try {
			MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(str.getBytes());
			byte b[] = mDigest.digest();
			int i;
			StringBuffer buffer = new StringBuffer("");
			for(int offset = 0; offset < b.length; offset++){
				i = b[offset];
				if(i < 0)
					i += 256;
				if(i < 16)
					buffer.append("0");
				buffer.append(Integer.toHexString(i));
			}
			ret = buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ret;
	}
	//添加表
	public static void addTable(String tb_name, String tb_title, String[] fd_names, String[] fd_types,
			String[] fd_titles) {
		
		StringBuffer sql = new StringBuffer("create table " + tb_name + "(");
		sql.append("id int(11) unsigned not null auto_increment");
		for(int i = 0; i < fd_names.length; i++){
			
			if("id".equalsIgnoreCase(fd_names[i])) continue;//不需要新增id，会自动建立的
			sql.append(", "+fd_names[i]+" "+fd_types[i]);
		}
		sql.append(", primary key(`id`))");
		//新建表
		JDBC.executeUpdate(sql.toString());
		
		//添加中文信息到数据字典
		tb_name = tb_name.substring(3);
		String sqld = "insert datadict(name,translate) value(?,?)";
		JDBC.executeUpdate(sqld, tb_name ,tb_title);
		for(int i = 0; i< fd_names.length ; i++){
			JDBC.executeUpdate(sqld, fd_names[i],fd_titles[i]);
		}
	}
	//权限操作
	public static boolean operate(int user_id, String tbname) {
		boolean b = false;
		String sql = "select `right` from privilege where user_id=?";
		List<Map<String, Object>> list = JDBC.executeQuery(sql, user_id);
		if(list != null){
			for(Map<String, Object> map : list){
				String right = (String)map.get("right");
				if(right != null && right.equals(tbname+"$operate")){
					b = true;
				}
			}
		}
		return b;
	}
	//展示数据
	public static String list(String tbname, int pageNum) {
		StringBuffer  table = new StringBuffer("");
		//分页,待完善...
		String sql = "select *from " + tbname;// + " order by id limit " + 6*pageNum+","+(6*pageNum+6);
		List<List> list = JDBC.list(sql);
		if(list != null){
			table.append("<table id=\"list_data\">");
			//标题
			List dataTitle = list.get(0);
			table.append("<tr>");
			for(int i= 0; i < dataTitle.size(); i++){
				table.append("<td>"+JDBC.translate((String)dataTitle.get(i))+"</td>");
			}
			table.append("<td><a href=\"#\" class=\"add_data\">增加</a></tr>");
			list.remove(0);
			//内容
			for(List data : list){
				table.append("<tr id=\""+data.get(0)+"\">");
				for(Iterator it = data.iterator(); it.hasNext();){
					table.append("<td>"+it.next()+"</td>");
				}
				table.append("<td><a href=\"#\" class=\"update_data\">修改</a></td>"
						+ "<td><a href=\"#\" class=\"delete_data\">删除</a></td></tr>");
			}
			table.append("</table>");
		}
		return table.toString();
	}
	//修改表数据展示
	public static String alter(int id, String tbname) {
		StringBuffer ret = new StringBuffer("");
		String sql = "select * from "+tbname+" where id=?";
		List<List> list = JDBC.list(sql, id);
		if(list != null){
			ret.append("<form method=post onSubmit='upd(\"alter\")' id='upd' ><table id=list_data>");
			List title = list.get(0);//标题
			List data = list.get(1);//获得数据行
			
			ret.append("<input type=hidden name=tbname value="+tbname+"> ");
			ret.append("<tr><td>字段名</td><td>值</td></tr>");
			ret.append("<tr><td>"+title.get(0)+"</td><td><input type=\"text\" name=\"id\" readonly value=\""+data.get(0)+"\"></td></tr>");
			//data.remove(0);
			//title.remove(0);
			//Iterator it = data.iterator();
			//Iterator ti = title.iterator();
			for(int i = 1; i < data.size(); i++){
				if(getColumnType(tbname, (String)title.get(i)).equals("text"))
					ret.append("<tr><td>"+JDBC.translate((String)title.get(i))+"</td><td><textarea class=\"ckeditor\" cols=\"80\" id=\"content_id\" name=\"fd_values\" rows=\"10\">"+data.get(i)+"</textarea> </td></tr>");
				else ret.append("<tr><td>"+JDBC.translate((String)title.get(i))+"</td><td><input name=\"fd_values\" type=\"text\" value=\""+data.get(i)+"\"></td></tr>");
			}
			ret.append("<tr><td><input type=button onclick='upd(\"alter\")' value=\"确定\"></td></tr></table></form>");
		}
		
		return ret.toString();
	}
	//修改数据
	public static void alter(int id, String tbname, String[] fd_values) {
		StringBuffer sql = new StringBuffer("");
		sql.append("update "+tbname+" set ");
		
		String sel = "select *from "+tbname+" where id=?";
		List<List> list = JDBC.list(sel, id);
		//list.remove(1);//删除数据留下字段名
		List data = list.get(0);//获得字段名
		//经过mysql插入测试，证明：
		//插入或更新时，以字符型数据插入，即便时数值型也能插入，前提时字符型数字
		//从1开始,id不更新
		for(int i = 1; i < data.size(); i++){
			sql.append(data.get(i)+" = '"+fd_values[i-1]+"',");
		}
		sql.replace(sql.length()-1, sql.length(), "");
		sql.append(" where id=?");
		boolean b = JDBC.executeUpdate(sql.toString(), id);
		if(b) System.out.println("数据修改成功");
	}
	//删除数据
	public static boolean delete(int id, String tbname) {
		String sql = "delete from "+tbname+" where id=?";
		boolean b = JDBC.executeUpdate(sql,id);
		return b;
	}
	//获得列名(可以在空表或非空表下查询)
	public static List<List> getColumnNames(String tbname){ 
		//指明是news数据库，防止user这类不同数据库重名查出多余数据
		String sql = "SELECT column_name from information_schema.columns  WHERE TABLE_SCHEMA='comsys' and  table_name = '"+tbname+"'";
		List<List> list = JDBC.list(sql);
		if(list != null){
			list.remove(0);//移除字段名的字段名
			list.remove(0);//移除"id"
		}
		return list;
	}
	//获得字段对应数据类型
	public static String getColumnType(String tbname, String ColumnName){
		String ret = null;
		String sql = "SELECT data_type FROM information_schema.columns WHERE table_name='"
				+ tbname + "'  and column_name='"+ColumnName+"'";
		List<List> list = JDBC.list(sql);
		if(list != null){
			ret = (String)list.get(1).get(0);
		}
		return ret;
	}
	//添加数据展示
	public static String addData(String tbname) {
		StringBuffer ret = new StringBuffer("");
		List<List> list = getColumnNames(tbname);
		if(list != null){
			ret.append("<form method=post onSubmit='upd(\"add\")' id='upd' ><table id=list_data>");
			
			ret.append("<input type=hidden name=tbname value="+tbname+"> ");
			ret.append("<tr><td>字段名</td><td>值</td></tr>");
			//System.out.println("测试："+list);
			for(List data : list){
				for(int i = 0; i < data.size(); i++){
					if(getColumnType(tbname, (String)data.get(i)).equals("text"))
						ret.append("<tr><td>"+JDBC.translate((String)data.get(i))+"</td><td><textarea class=\"ckeditor\" cols=\"80\" id=\"content_id\" name=\"fd_values\" rows=\"10\"></textarea> </td></tr>");
					else ret.append("<tr><td>"+JDBC.translate((String)data.get(i))+"</td><td><input name=fd_values type=\"text\" value=\"\"></td></tr>");
				}
			}
			ret.append("<tr><td><input type=button onclick='upd(\"add\")' value=\"确定\"></td></tr></table></form>");
		}
		
		return ret.toString();
	}
	//添加数据
	public static void addData(String tbname, String[] fd_values) {
		StringBuffer sql = new StringBuffer("");
		sql.append("insert "+tbname+"(");
		
		List<List> list = getColumnNames(tbname);
		//字段名
		for(List data : list){
			for(Iterator it = data.iterator();it.hasNext();){
				sql.append((String)it.next()+",");
			}
		}
		sql.replace(sql.length()-1, sql.length(), "");
		sql.append(") values(");
		//插入的值
		for(String data : fd_values){
			sql.append("'"+data+"',");
		}
		sql.replace(sql.length()-1, sql.length(), "");
		sql.append(")");
		boolean b = JDBC.executeUpdate(sql.toString());
		if(b) System.out.println("插入操作成功");
	}
	
	//忘记密码后重置密码的更新
	public static void forgetPassWord(String passwd, String user_email) {
	//根据md5加密加盐算法加密
		String salt = getSalt();
		String pass = md5(passwd+salt)+salt;
		String sql = "update user set user_password=? where user_contact=?";
		JDBC.executeUpdate(sql, pass, user_email);		
	}
	//全局搜索
	public static String search(String search_keyword, String tb_name) {
		//tb_name = dbs_tb_name(tb_name);
		StringBuffer  table = new StringBuffer(""); 
		List<List> list = new LinkedList<List>();//存储查询出来的数据
		//判断查询的表中每个字段（除了id）中是否含有该关键字
		List<List> col_names = getColumnNames(tb_name);
		if(col_names != null){
			for(List col_name : col_names){
			String sql = "select *from " + tb_name + " where "+col_name.get(0)+" like '%" + search_keyword + "%'";
			List<List> ret = JDBC.list(sql);
			//排除仅有title一行的数据,精妙的取巧～
			if(ret != null && ret.size() != 1){
				for(List ret2 : ret){
					list.add(ret2);
					}
				}
			}
		}
		
		if(list != null){
			table.append("<table id=\"list_data\">");
			//标题
			List dataTitle = list.get(0);
			table.append("<tr>");
			for(int i= 0; i < dataTitle.size(); i++){
				table.append("<td>"+JDBC.translate((String)dataTitle.get(i))+"</td>");
			}
			table.append("<td><a href=\"#\" class=\"add_data\">增加</a></tr>");
			list.remove(0);
			//内容
			for(List data : list){
				table.append("<tr id=\""+data.get(0)+"\">");
				for(Iterator it = data.iterator(); it.hasNext();){
					table.append("<td>"+it.next()+"</td>");
				}
				table.append("<td><a href=\"#\" class=\"update_data\">修改</a></td>"
						+ "<td><a href=\"#\" class=\"delete_data\">删除</a></td></tr>");
			}
			table.append("</table>");
		}
		return table.toString();
	}
	/*//根据表的中文翻译成英文
	private static String dbs_tb_name(String tb_name) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
