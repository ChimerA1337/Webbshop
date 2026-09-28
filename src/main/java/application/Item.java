package application;

public class Item {
    private String name;
    private float price;
    private String description;

    public Item(String name, float price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    protected String getName() { return name; }
    public float getPrice() { return price; }
    protected String getDescription() { return description; }

    protected void setName(String name) { this.name = name; }
    protected void setPrice(float price) { this.price = price; }
    protected void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}


