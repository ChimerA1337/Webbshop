package application;

public class Item {
    private String name;
    private float price;
    private String description;


    private final int itemId;

    public Item(int itemId, String name, float price, String description) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getItemId() {
        return itemId;
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


