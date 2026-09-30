package application;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }
    public void removeItem(Item item) {
        items.remove(item);
    }
    public List<Item> getItems() {
        return List.copyOf(items);
    }

    public void clearList(){
        items.clear();
    }
}
