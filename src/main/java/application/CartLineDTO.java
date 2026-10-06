package application;

public final class CartLineDTO {
    private final ItemDTO item;
    private final int quantity;

    public CartLineDTO(ItemDTO item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public ItemDTO getItem() { return item; }
    public int getQuantity() { return quantity; }
}
