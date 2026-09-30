package application;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private final int orderid;
    private final Timestamp ordered;
    private final Timestamp packed;
    private final Timestamp shipped;
    private final int userid;
    private final List<OrderItem> items;

    public Order(int orderid, Timestamp ordered, Timestamp packed, Timestamp shipped, int userid, List<OrderItem> items) {
        this.orderid = orderid;
        this.ordered = ordered;
        this.packed = packed;
        this.shipped = shipped;
        this.userid = userid;
        this.items = items;
    }

    public int getOrderid() { return orderid; }
    public Timestamp getOrdered() { return ordered; }
    public Timestamp getPacked() { return packed; }
    public Timestamp getShipped() { return shipped; }
    public int getUserid() { return userid; }
}
