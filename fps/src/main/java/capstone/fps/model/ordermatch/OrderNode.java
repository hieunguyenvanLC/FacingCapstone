package capstone.fps.model.ordermatch;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrderNode {
    private final List<OrderStat> statList;

    public OrderNode() {
        this.statList = new ArrayList<>();
    }

    public List<OrderStat> getStatList() {
        return statList;
    }
}
