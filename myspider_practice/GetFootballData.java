package com.tes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GetFootballData {

	public static void main(String[] args) throws IOException {
		// 借助URL&Java IO获取网页内容
		String address = "http://www.footballresults.org/league.php?league=EngDiv1";
		// 初始化URL类
		URL url = new URL(address);
		// 获取url对应的流数据,包装成reader
		InputStreamReader isr 
			= new InputStreamReader(url.openStream(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		
		String content = "";
		while( (content = br.readLine()) != null ) {
			System.out.println(content);
		}
		
	}

}
