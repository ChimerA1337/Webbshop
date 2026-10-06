package application;

import java.sql.Timestamp;

public final class OrderDTO {
    private final int orderid;
    private final Timestamp ordered;
    private final Timestamp packed;
    private final int userid;

    public OrderDTO(int orderid, Timestamp ordered, Timestamp packed, int userid) {
        this.orderid = orderid;
        this.ordered = copyTimestamp(ordered);
        this.packed = copyTimestamp(packed);
        this.userid = userid;
    }

    public int getOrderid() { return orderid; }
    public Timestamp getOrdered() { return copyTimestamp(ordered); }
    public Timestamp getPacked() { return copyTimestamp(packed); }
    public int getUserid() { return userid; }

    private static Timestamp copyTimestamp(Timestamp timestamp) {
        return timestamp == null ? null : Timestamp.from(timestamp.toInstant());
    }
}
