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

    private void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    private void setPrice(Double price) {
        if(price < 0)
            price *= -1;

        this.price = price;
    }
}
