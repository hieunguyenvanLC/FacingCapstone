package capstone.fps.model.ordermatch;

import capstone.fps.entity.FROrder;
import capstone.fps.entity.FROrderDetail;
import capstone.fps.entity.FRStore;
import capstone.fps.repository.OrderDetailRepo;

import java.util.ArrayList;
import java.util.List;

public class OrderMap {

    private final OrderNode[][] map;

    private final ArrayList<Delta> layer0;
    private final ArrayList<Delta> layer1;
    private final ArrayList<Delta> layer2;
    private final ArrayList<Delta> layer3;
    private final ArrayList<Delta> layer4;

    public OrderMap() {
        this.map = new OrderNode[1483][732];
        for (int i = 0; i < 1483; i++) {
            for (int j = 0; j < 732; j++) {
                map[i][j] = new OrderNode();
            }
        }
        layer0 = new ArrayList<>();
        layer0.add(new Delta(0, 0));

        layer1 = new ArrayList<>();
        layer1.add(new Delta(0, -1));
        layer1.add(new Delta(0, 1));
        layer1.add(new Delta(-1, 0));
        layer1.add(new Delta(1, 0));

        layer1.add(new Delta(-1, -1));
        layer1.add(new Delta(1, -1));
        layer1.add(new Delta(-1, 1));
        layer1.add(new Delta(1, 1));


        layer2 = new ArrayList<>();
        layer2.add(new Delta(2, -1));
        layer2.add(new Delta(2, 0));
        layer2.add(new Delta(2, 1));

        layer2.add(new Delta(-2, -1));
        layer2.add(new Delta(-2, 0));
        layer2.add(new Delta(-2, 1));

        layer2.add(new Delta(-1, 2));
        layer2.add(new Delta(0, 2));
        layer2.add(new Delta(1, 2));

        layer2.add(new Delta(-1, -2));
        layer2.add(new Delta(0, -2));
        layer2.add(new Delta(1, -2));


        layer3 = new ArrayList<>();
        layer3.add(new Delta(3, -1));
        layer3.add(new Delta(3, 0));
        layer3.add(new Delta(3, 1));

        layer3.add(new Delta(-3, -1));
        layer3.add(new Delta(-3, 0));
        layer3.add(new Delta(-3, 1));

        layer3.add(new Delta(-1, 3));
        layer3.add(new Delta(0, 3));
        layer3.add(new Delta(1, 3));

        layer3.add(new Delta(-1, -3));
        layer3.add(new Delta(0, -3));
        layer3.add(new Delta(1, -3));

        layer3.add(new Delta(-2, -2));
        layer3.add(new Delta(-2, 2));
        layer3.add(new Delta(2, -2));
        layer3.add(new Delta(2, 2));


        layer4 = new ArrayList<>();
        layer4.add(new Delta(4, -1));
        layer4.add(new Delta(4, 0));
        layer4.add(new Delta(4, 1));

        layer4.add(new Delta(-4, -1));
        layer4.add(new Delta(-4, 0));
        layer4.add(new Delta(-4, 1));

        layer4.add(new Delta(-1, 4));
        layer4.add(new Delta(0, 4));
        layer4.add(new Delta(1, 4));

        layer4.add(new Delta(-1, -4));
        layer4.add(new Delta(0, -4));
        layer4.add(new Delta(1, -4));


        layer4.add(new Delta(3, 2));
        layer4.add(new Delta(3, 3));
        layer4.add(new Delta(2, 3));

        layer4.add(new Delta(-3, 2));
        layer4.add(new Delta(-3, 3));
        layer4.add(new Delta(-2, 3));

        layer4.add(new Delta(3, -2));
        layer4.add(new Delta(3, -3));
        layer4.add(new Delta(2, -3));

        layer4.add(new Delta(-3, -2));
        layer4.add(new Delta(-3, -3));
        layer4.add(new Delta(-2, -3));


    }

    public ArrayList<Delta> getLayer(int layer) {
        switch (layer) {
            case 0:
                return layer1;
            case 1:
                return layer1;
            case 2:
                return layer2;
            case 3:
                return layer3;
            case 4:
                return layer4;
            default:
                return null;
        }
    }


    public OrderStat addOrder(FROrder frOrder, OrderDetailRepo orderDetailRepo) {

        List<FROrderDetail> frOrderDetails = orderDetailRepo.findAllByOrder(frOrder);
        FRStore store = frOrderDetails.get(0).getProduct().getStore();
        int col = convertLon(store.getLongitude());
        int row = convertLat(store.getLatitude());


        OrderStat orderStat = new OrderStat(frOrder, store.getLongitude(), store.getLatitude());
        map[row][col].getStatList().add(orderStat);
        return orderStat;

    }

    public OrderStat removeOrder(OrderStat orderStat, int col, int row) {
        map[row][col].getStatList().remove(orderStat);
        return orderStat;
    }


    public int convertLon(double longitude) {
        int col = ((int) longitude) * 100 - 732;
        return col;
    }

    public int convertLat(double latitude) {
        int row = ((int) latitude) * 100 - 1483;
        return row;
    }

    public OrderNode getNode(int col, int row) {
        return map[row][col];
    }

}
