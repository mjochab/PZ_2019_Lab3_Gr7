package models;

public class OrderProductRecord {
    private Product product;
    private Integer count;
    private Double totalPrice;

    public OrderProductRecord(Product product, Integer count)
    {
        setProduct(product);
        setCount(count);
        calculateTotalPrice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        if(count < 0)
            count *= -1;

        this.count = count;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void calculateTotalPrice()
    {
        if(this.product != null)
            this.totalPrice = this.product.getPrice() * this.count;
        else
            this.totalPrice = new Double(0);
    }


}
