package application;

public class Item {
    private final int itemid;
    private String name;
    private float price;
    private String description;
    private int amount;
    private Category category;

    public Item(int itemid, String name, float price, String description, int amount, Category category) {
        this.itemid = itemid;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public int getItemid() {
        return itemid;
    }
    public String getName() { return name; }
    public float getPrice() { return price; }
    public String getDescription() { return description; }
    public int getAmount() { return amount; }
    public Category getCategory() { return category; }

    @Override
    public String toString() {
        return "Item{" +
                "itemid=" + itemid +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", category=" + category +
                '}';
    }
}


