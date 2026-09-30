package application;

public class ItemDTO {
    private final int itemId;
    private final String name;
    private final float price;
    private final String description;
    public ItemDTO(int itemId, String name, float price, String description) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.description = description;
    }
    public int getItemId() {
        return itemId;
    }
    public String getName() { return name; }
    public float getPrice() { return price; }
    public String getDescription() { return description; }
}

