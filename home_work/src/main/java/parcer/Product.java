package parcer;

import lombok.Builder;

@Builder
public class Product {
    private String name;
    private Double price;
    private String link;
    private String imgLink;

    public Product(String name, double price, String link, String imgLink) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.imgLink = imgLink;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Название='" + name + '\'' +
                ", цена(руб)=" + price +
                ", ссылка='" + link + '\'' +
                ", ссылка на картинку='" + imgLink + '\'' +
                '}';
    }
}
