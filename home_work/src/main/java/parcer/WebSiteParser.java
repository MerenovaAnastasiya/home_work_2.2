package parcer;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashSet;

import java.util.Set;

public class WebSiteParser {
    private final String domen;
    private String userAgent;
    private Set<Product> productSet;

    public WebSiteParser(String domen, String userAgent) {
        this.domen = domen;
        this.userAgent = userAgent;
        this.productSet = new HashSet<>();
    }

    private Set<String> parseCategories() throws Exception {
        Set<String> linkSet = new HashSet<>();
        try {
            Connection connection = Jsoup.connect(domen).ignoreHttpErrors(true).ignoreHttpErrors(true).timeout(10000).followRedirects(true);
            if((connection.execute().contentType().matches("text/.*")) && (connection.execute().statusCode() == 200)) {
                Document document = connection.get();
                Set<String> hrefs = new HashSet<>(Xsoup.compile("a/@href").evaluate(document).list());
                for(String href: hrefs) {
                    if(!href.contains("http:/")) {
                        href = domen + href;
                        if(href.matches("^" + domen + "/catalog/\\w*/|^" + domen + "/catalog/\\w*/\\w*/")) {
                            URL url = new URL(href);
                            String s = url.getProtocol() + "://" + url.getHost() + url.getPath();
                            System.out.println(s);
                            linkSet.add(s);
                        }
                    }
                }
            }
            else {
                System.out.println("Error in " + connection.get().baseUri());
            }

        } catch (MalformedURLException malformedURLException) {
            throw new Exception("URL address isn't normal");
        } catch (HttpStatusException httpStatusException) {
            throw new Exception("Incorrect server response");
        } catch (UnsupportedMimeTypeException unsupportedMimeTypeException) {
            throw new Exception("Incorrect MIME-type");
        } catch (SocketTimeoutException socketTimeoutException) {
            throw new Exception("Server timeout");
        } catch (IOException exception) {
            throw new IOException("Incorrect request");
        }
        return linkSet;
    }

    public Set<Product> parseProducts() throws Exception {
        Set<String> categories = this.parseCategories();
        if (categories.size() != 0) {
            try {
                for (String categoryPage : categories) {
                    Connection connection = Jsoup.connect(categoryPage).ignoreHttpErrors(true).ignoreContentType(true).timeout(10000).followRedirects(true);
                    if ((connection.execute().contentType().matches("text/.*")) && (connection.execute().statusCode() == 200)) {
                        Document document = Jsoup.connect(categoryPage).userAgent(userAgent).get();
                        int n = 1;
                        Elements pagination = Xsoup.compile("div/[@class=pages__numb").evaluate(document).getElements();
                        if(pagination.size()!= 0) {
                            n = Integer.parseInt(pagination.get(pagination.size() - 1).text());
                        }
                        for (int i = 1; i <= n; i++) {
                            Document doc = Jsoup.connect(categoryPage + "?PAGEN_1=" + i).userAgent(userAgent).get();
                            Elements catalogItems = Xsoup.compile("div[@class=list__item]").evaluate(doc).getElements();
                            for (Element catalogItem : catalogItems) {
                                String name = Xsoup.compile("div[@class=product__title]").evaluate(catalogItem).getElements().first().text();
                                Element prc;
                                String sPrice;
                                if((prc = Xsoup.compile("div[@class=new-price]").evaluate(catalogItem).getElements().first()) != null) {
                                    sPrice = prc.text();
                                }
                                else {
                                    sPrice = Xsoup.compile("div[@class=product__sum]").evaluate(catalogItem).getElements().first().text();
                                }
                                Double price = Double.parseDouble(sPrice.substring(0, sPrice.length() - 2).replace(" ", ""));
                                String link = domen + Xsoup.compile("a/@href").evaluate(catalogItem).get();
                                String style = Xsoup.compile("div[@class=product__img]/@style").evaluate(catalogItem).get();
                                String imgLnk = domen + "/" + style.split("'")[1];
                                Product product = Product.builder()
                                        .name(name)
                                        .price(price)
                                        .link(link)
                                        .imgLink(imgLnk)
                                        .build();
                                productSet.add(product);
                            }
                        }
                    }
                    else {
                        System.out.println("Error in " + connection.get().baseUri());
                    }
                }
            } catch (MalformedURLException malformedURLException) {
                throw new Exception("URL address isn't normal");
            } catch (HttpStatusException httpStatusException) {
                System.out.println("Incorrect server response");
                categories.iterator().next();
            } catch (UnsupportedMimeTypeException unsupportedMimeTypeException) {
                System.out.println("Incorrect MIME type");
                categories.iterator().next();
            } catch (SocketTimeoutException socketTimeoutException) {
                throw new Exception("Server timeout");
            } catch (IOException exception) {
                throw new IOException("Incorrect request");
            }
        } else {
            System.out.println("This site doesn't have any categories!");
        }
        return productSet;

    }
    public String getDomen() {
        return domen;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

}
