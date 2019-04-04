package capstone.fps.model.ordermatch;

import capstone.fps.common.Fix;
import capstone.fps.entity.FROrder;

import java.util.ArrayList;

public class OrderMap {

    private final OrderNode[][] map;

    //    private final ArrayList<Delta> layer0;
//    private final ArrayList<Delta> layer1;
//    private final ArrayList<Delta> layer2;
//    private final ArrayList<Delta> layer3;
//    private final ArrayList<Delta> layer4;
    private ArrayList<ArrayList<Delta>> layers;

    public OrderMap() {
        this.map = new OrderNode[1483][732];
        for (int i = 0; i < 1483; i++) {
            for (int j = 0; j < 732; j++) {
                map[i][j] = new OrderNode();
            }
        }
//        layer0 = new ArrayList<>();
//        layer0.add(new Delta(0, 0));
//
//        layer1 = new ArrayList<>();
//        layer1.add(new Delta(0, -1));
//        layer1.add(new Delta(0, 1));
//        layer1.add(new Delta(-1, 0));
//        layer1.add(new Delta(1, 0));
//
//        layer1.add(new Delta(-1, -1));
//        layer1.add(new Delta(1, -1));
//        layer1.add(new Delta(-1, 1));
//        layer1.add(new Delta(1, 1));
//
//
//        layer2 = new ArrayList<>();
//        layer2.add(new Delta(2, -1));
//        layer2.add(new Delta(2, 0));
//        layer2.add(new Delta(2, 1));
//
//        layer2.add(new Delta(-2, -1));
//        layer2.add(new Delta(-2, 0));
//        layer2.add(new Delta(-2, 1));
//
//        layer2.add(new Delta(-1, 2));
//        layer2.add(new Delta(0, 2));
//        layer2.add(new Delta(1, 2));
//
//        layer2.add(new Delta(-1, -2));
//        layer2.add(new Delta(0, -2));
//        layer2.add(new Delta(1, -2));
//
//
//        layer3 = new ArrayList<>();
//        layer3.add(new Delta(3, -1));
//        layer3.add(new Delta(3, 0));
//        layer3.add(new Delta(3, 1));
//
//        layer3.add(new Delta(-3, -1));
//        layer3.add(new Delta(-3, 0));
//        layer3.add(new Delta(-3, 1));
//
//        layer3.add(new Delta(-1, 3));
//        layer3.add(new Delta(0, 3));
//        layer3.add(new Delta(1, 3));
//
//        layer3.add(new Delta(-1, -3));
//        layer3.add(new Delta(0, -3));
//        layer3.add(new Delta(1, -3));
//
//        layer3.add(new Delta(-2, -2));
//        layer3.add(new Delta(-2, 2));
//        layer3.add(new Delta(2, -2));
//        layer3.add(new Delta(2, 2));
//
//
//        layer4 = new ArrayList<>();
//        layer4.add(new Delta(4, -1));
//        layer4.add(new Delta(4, 0));
//        layer4.add(new Delta(4, 1));
//
//        layer4.add(new Delta(-4, -1));
//        layer4.add(new Delta(-4, 0));
//        layer4.add(new Delta(-4, 1));
//
//        layer4.add(new Delta(-1, 4));
//        layer4.add(new Delta(0, 4));
//        layer4.add(new Delta(1, 4));
//
//        layer4.add(new Delta(-1, -4));
//        layer4.add(new Delta(0, -4));
//        layer4.add(new Delta(1, -4));
//
//
//        layer4.add(new Delta(3, 2));
//        layer4.add(new Delta(3, 3));
//        layer4.add(new Delta(2, 3));
//
//        layer4.add(new Delta(-3, 2));
//        layer4.add(new Delta(-3, 3));
//        layer4.add(new Delta(-2, 3));
//
//        layer4.add(new Delta(3, -2));
//        layer4.add(new Delta(3, -3));
//        layer4.add(new Delta(2, -3));
//
//        layer4.add(new Delta(-3, -2));
//        layer4.add(new Delta(-3, -3));
//        layer4.add(new Delta(-2, -3));
        initLayer(Fix.shipperRange);
    }


    private void initLayer(int range) {
        layers = new ArrayList<>();
        for (int i = 0; i <= range; i++) {
            layers.add(new ArrayList<>());
        }
        layers.get(0).add(new Delta(0, 0));
        for (int x = 1; x <= range; x++) {
            layers.get(x).add(new Delta(x, 0));
            layers.get(x).add(new Delta(0, -x));
            layers.get(x).add(new Delta(-x, 0));
            layers.get(x).add(new Delta(0, x));
            for (int y = 1; y <= range; y++) {
                int dis = (int) Math.round(Math.sqrt(x * x + y * y));
                if (dis <= range) {
                    layers.get(dis).add(new Delta(x, y));
                    layers.get(dis).add(new Delta(-y, x));
                    layers.get(dis).add(new Delta(-x, -y));
                    layers.get(dis).add(new Delta(y, -x));
                }
            }
        }
    }


//    public ArrayList<Delta> getLayer(int layer) {
//        return layers.get(layer);
////        switch (layer) {
////            case 0:
////                return layer0;
////            case 1:
////                return layer1;
////            case 2:
////                return layer2;
////            case 3:
////                return layer3;
////            case 4:
////                return layer4;
////            default:
////                return null;
////        }
//    }


    public ArrayList<ArrayList<Delta>> getLayers() {
        return layers;
    }

    public OrderStat addOrder(FROrder frOrder, double storeLon, double storeLat) {
        int col = convertLon(storeLon);
        int row = convertLat(storeLat);

        OrderStat orderStat = new OrderStat(frOrder, storeLon, storeLat);
        map[row][col].getStatList().add(orderStat);
        return orderStat;

    }

    public OrderStat removeOrder(OrderStat orderStat, int col, int row) {
        map[row][col].getStatList().remove(orderStat);
        return orderStat;
    }


    public int convertLon(double longitude) {
        return (int) (longitude * 100) - 10214;
    }

    public int convertLat(double latitude) {
        return (int) (latitude * 100) - 856;
    }

    public OrderNode getNode(int col, int row) {
        return map[row][col];
    }

}
