package myspider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetTuitang {

	public static void main(String[] args) throws IOException {
		String url = "https://www.duitang.com/category/?cat=wallpaper";
		Connection conn = Jsoup.connect(url);
		Document document = conn.get();
		Elements pictures = document.select("div.mbpho");
		Iterator<Element> pictureIterator = pictures.iterator();
		int i = 0;
		while (pictureIterator.hasNext()) {
			Element picturelinks = pictureIterator.next();
			Elements picturelink = picturelinks.getElementsByTag("img");
			Iterator<Element> picturesHerf = picturelink.iterator();
			while (picturesHerf.hasNext()) {
				Element ie = picturesHerf.next();
				String herf = ie.attr("src");
				URL address = new URL(herf);
				InputStream is = address.openStream();
				byte[] buffer = new byte[1024];
				String local = "d:\\" + i + ".jpeg";
				OutputStream fos = new FileOutputStream(local);
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
				i++;
			}
		}
		System.out.println(i);
	}
}
