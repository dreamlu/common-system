package com.news.lu.config;
 
import java.io.FileInputStream; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class Config implements ServletContextListener{

	private static String path = "/WEB-INF/db.properties";
	private static Properties prop = new Properties();
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	//初始化加载配置文件对象
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext context = arg0.getServletContext();
		String realPath = context.getRealPath(path);
		System.out.println("配置文件路径:" + realPath);
		try {
			InputStreamReader reader =new InputStreamReader(new FileInputStream(realPath),"utf-8");
			prop.load(reader);
		} catch (IOException e) {
			System.out.println("加载配置失败");
		}
	}
	//获取配置文件中信息
	public static String get(String key){
		//System.out.println(prop.getProperty("db.url"));
		return prop.getProperty(key);
	}
	/*public static void main(String[] args) {
		System.out.println(Config.get("db.drivername"));
	}*/
}
