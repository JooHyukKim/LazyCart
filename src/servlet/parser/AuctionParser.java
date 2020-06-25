package servlet.parser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.sun.org.apache.xerces.internal.parsers.CachingParserPool.ShadowedGrammarPool;

import model.productVO;
import sun.java2d.pipe.SpanClipRenderer;

public class AuctionParser implements Parser {

	@Override
	public productVO handle(String url) {
		productVO product = null;
		String shop ="Musinsa";
		//url
		System.out.println("url-------------------------");
		Document document;
		try {
			document = Jsoup.connect(url).get();
			System.out.println(document);
			//이름
			System.out.println("name-------------------------");
			Elements nameParentNode = document.getElementsByClass("itemtit");
//			Element nameChild = nameParentNode.child(0);
			String name = nameParentNode.text();

//			

			//가격
			System.out.println("price-------------------------");
			String priceTag = document.select("span[id=goods_price]").text();
			int price = Integer.parseInt(priceTag.replaceAll(",", ""));
			System.out.println(price);

			//카테고리
			System.out.println("category-------------------------");
			String category = document.select("p[class=item_categories]").text();
			System.out.println(category);
			
			//이미지
			System.out.println("image-------------------------");
			ArrayList<String> imglist = new ArrayList<String>();
			Elements imageParentNode = document.select("ul[class=product_thumb]");
			for (Element imageChild : imageParentNode) {
				imglist.add(imageChild.select("img").toString());
			}
			String img = imglist.get(0);
			System.out.println(imglist);
			//옵션
//			System.out.println("option-------------------------");
//			Elements optionElementlist = document.select("div[class=opt]");
//			for (Element optionElement : optionElementlist) {
//				System.out.println(optionElement.text());
//			}
			System.out.println("option-------------------------");
			ArrayList<String> optionlist = new ArrayList<String>();
			Element selectTag = document.getElementsByClass("option1").get(0);
			String options = selectTag.children().get(0).toString();


			product = new productVO(name, price, shop, url, category, img, options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
		
	}

}
