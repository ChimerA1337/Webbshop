package application;

public final class ItemDTO {
    private final int itemid;
    private final String name;
    private final float price;
    private final String description;
    private final int amount;
    private final Category category;

    public ItemDTO(int itemid, String name, float price, String description, int amount, Category category) {
        this.itemid = itemid;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public int getItemid() { return itemid; }
    public String getName() { return name; }
    public float getPrice() { return price; }
    public String getDescription() { return description; }
    public int getAmount() { return amount; }
    public Category getCategory() { return category; }
}
