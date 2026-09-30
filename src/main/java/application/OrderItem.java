package application;

public class OrderItem {
    private final int itemid;
    private final int amount;

    public OrderItem(int itemid, int amount) {
        this.itemid = itemid;
        this.amount = amount;
    }

    public int getItemid() { return itemid; }
    public int getAmount() {return amount; }
}
