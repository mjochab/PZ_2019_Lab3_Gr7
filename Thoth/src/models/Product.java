package models;

public class Product {
    private String name;
    private Double price;

    public Product(String name, Double price)
    {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if(price < 0)
            price *= -1;

        this.price = price;
    }
}
