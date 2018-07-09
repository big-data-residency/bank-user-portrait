package com.tes;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetDoubanData {

	public static void main(String[] args) throws IOException {
		String url = "https://movie.douban.com/review/best";
		int pageNum = 0;
		String pagePrefix = "?start=";
		url = url + pagePrefix + String.valueOf(pageNum * 20);
		
		//使用JSoup进行页面数据的爬取
		Connection conn = Jsoup.connect(url);
		// 一次性获取网页的所有数据
		Document document = conn.get();
		
		Elements headers = document.select("header.main-hd");
		
		Iterator<Element> headerIterator = headers.iterator();
		while(headerIterator.hasNext()) {
			Element header = headerIterator.next();
			Elements name = header.getElementsByClass("name");
			String username = name.text();
			System.out.println(username);
		}
		
		
		
//		int size = headers.size();
//		System.out.println(size);

	}

}
