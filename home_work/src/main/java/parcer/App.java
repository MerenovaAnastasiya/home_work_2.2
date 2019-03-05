package parcer;

import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        WebSiteParser webSiteParser = new WebSiteParser("http://www.alenaakhmadullina.ru", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36");
        Set<Product> products = webSiteParser.parseProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
