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

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class Mybatis {

	public void main(HttpServletResponse response) {
		System.out.println("Mybatis Start!");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//String result = "";
		try {
			PrintWriter pw = response.getWriter();
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
			
			// JSON 파일 만들기
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("data", list);
			
			JSONObject jo = new JSONObject(); 
			jo = JSONObject.fromObject(JSONSerializer.toJSON(map));
			pw.write(jo.toString());
			
			/*
			for(int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = (HashMap<String, Object>) list.get(i);
				Set set =map.keySet();
				Iterator it = set.iterator();
				while(it.hasNext()) {
					String key = it.next().toString();
					pw.append(key + " : " + map.get(key) + "\t");
					//result += key + " : " + map.get(key) + "\t";
					//System.out.print(key + " : " + map.get(key) + "\t");
				}
				//System.out.println("");
				//result += "<br>";
				pw.append("<br>");
			}
			*/
			//pw.write(result);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
