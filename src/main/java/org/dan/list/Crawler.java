package org.dan.list;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import redis.clients.jedis.Jedis;



public class Crawler {

    private static String redisUrlsWillKey = "crawler:urls:will";

    public static void main(String[] args) throws Exception {
        String startUrl = "http://www.huxiu.com/";
        String domain = "http://www.huxiu.com/";

        //获取文章url
        getUrl(startUrl, domain);

        //处理url，下载文章的内容并打印
        parseUrl();
    }

    private static void getUrl(String startUrl, String domain) throws Exception {
        Jedis jedis = new Jedis("nn1", 6379);
        Document document = Jsoup.connect(startUrl).get();
        Elements elements = document.getElementsByAttribute("href");
        for (Element element : elements) {
            String endUrl = element.attr("href");
            if(endUrl.contains("article")) {
                String url = domain + endUrl;
                System.out.println(url);
                jedis.lpush(redisUrlsWillKey, url);
            }
        }
    }

    private static void parseUrl() throws Exception {
        Jedis jedis = new Jedis("nn1", 6379);

        while(true) {
            String url = jedis.rpop(redisUrlsWillKey);
            Article article = parser(url);
            System.out.println(article);
        }

    }

    private static Article parser(String url) throws Exception {
        Document articleDocument = Jsoup.connect(url).get();
        Article article = new Article();

        Elements author = articleDocument.getElementsByClass("author-name");
        article.setAuthor(StringUtil.isBlank(author.text()) ? "noname" : author.text());

        Elements titleElement = articleDocument.getElementsByTag("title");
        String title = titleElement.get(0).text();
        article.setTitle(title);

        Elements elements = articleDocument.select("div #article_content");
        String content = elements.text();
        article.setContent(content);

        return article;
    }
}
