package application;

public class CartLine {
    private final Item item;
    private final int quantity;

    public CartLine(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }
}