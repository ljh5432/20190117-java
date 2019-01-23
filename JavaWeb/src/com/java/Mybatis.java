package com.java;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis {

	public void main(HttpServletResponse response) {
		System.out.println("Mybatis Start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String result = "";
		try {
			String resource = "mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			SqlSession session = sqlSessionFactory.openSession();
			List<HashMap<String, Object>> list = null;
			try {
				list = session.selectList("TestMapper.select");
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
			  session.close();
			}
			
			for(int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) list.get(i);
				Set set =map.keySet();
				Iterator it = set.iterator();
				while(it.hasNext()) {
					String key = it.next().toString();
					result += key + " : " + map.get(key) + "\t";
					//System.out.print(key + " : " + map.get(key) + "\t");
				}
				//System.out.println("");
				result += "<br>";
			}
			
			PrintWriter pw = response.getWriter();
			pw.write(result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
