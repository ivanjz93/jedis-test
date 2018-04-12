package org.dan.list;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SingleArticle {
    public static void main(String[] args) throws Exception {
        String url = "https://www.huxiu.com/article/102062/1.html";
        Document document = Jsoup.connect(url).get();

        Elements titleElement = document.getElementsByTag("title");
        String title = titleElement.get(0).text();

        Elements elements = document.select("div #article_content");
        String content = elements.text();

        System.out.println("title: " + title);
        System.out.println("content: " + content);
    }
}
