package capstone.fps.service;

import capstone.fps.entity.FROrder;
import capstone.fps.entity.FROrderDetail;
import capstone.fps.entity.FRRole;
import capstone.fps.entity.FRStore;
import capstone.fps.model.Response;
import capstone.fps.model.order.MdlOrder;
import capstone.fps.model.order.MdlOrderBuilder;
import capstone.fps.model.store.MdlStore;
import capstone.fps.model.store.MdlStoreBuilder;
import capstone.fps.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {
    private ShipperRepo shipperRepo;
    private AccountRepo accountRepo;
    private RoleRepo roleRepo;
    private OrderRepo orderRepository;
    private OrderDetailRepo orderDetailRepository;
    private StoreRepo storeRepo;
    private ProductRepo productRepo;
    long localTime = 0;
    long less = (12 * 60 * 60 * 1000) - 1; // <12h
    long equal12 = (12 * 60 * 60 * 1000); // =12h
    long equal24 = (24 * 60 * 60 * 1000); // =24h
    long more = (24 * 60 * 60 * 1000) + 1; //>24h

    public HomeService(
            ProductRepo productRepo,
            ShipperRepo shipperRepo,
            AccountRepo accountRepo,
            RoleRepo roleRepo,
            StoreRepo storeRepo,
            OrderRepo orderRepository,
            OrderDetailRepo orderDetailRepository
    ) {
        this.productRepo = productRepo;
        this.shipperRepo = shipperRepo;
        this.accountRepo = accountRepo;
        this.roleRepo = roleRepo;
        this.storeRepo = storeRepo;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

//    public int soldProductBy(int month, int year, int day) {
//        List<FROrder> orderList = this.orderRepository.findAllByStatus(4);
//
//        return
//    }

    public int countShipper() {
        FRRole role = this.roleRepo.findById(3).get();
        return this.accountRepo.countByRoleAndStatus(role, 1);
    }

    public Integer countShipper(Long start, Long end) {
        FRRole role = this.roleRepo.findById(3).get();
        return this.accountRepo.countByRoleAndStatusAndCreateTimeGreaterThanAndCreateTimeLessThanEqual(role, 1, start, end);
    }

    //    public int countNewCus(int month, int year) {
//        FRRole role = this.roleRepo.findById(2).get();
//        long start = this.dateToUnix(year, month, 1, 0, 0, 0);
//        long end = this.dateToUnix(year, month + 1, 1, 0, 0, 0);
//        return this.accountRepo.countByRoleAndStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(role, 1, start, end);
//    }
    public int countCus() {
        FRRole role = this.roleRepo.findById(2).get();
        return this.accountRepo.countByRoleAndStatus(role, 1);
    }
    public Integer countCus(Long start, Long end) {
        FRRole role = this.roleRepo.findById(2).get();
        return this.accountRepo.countByRoleAndStatusAndCreateTimeGreaterThanAndCreateTimeLessThanEqual(role, 1, start, end);
    }
//
//    public int countNewStore(int month, int year) {
//        long start = this.dateToUnix(year, month, 1, 0, 0, 0);
//        long end = this.dateToUnix(year, month + 1, 1, 0, 0, 0);
//        return this.storeRepo.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(1, start, end);
//    }

    public int countStore() {
        return this.storeRepo.countByStatus(1);
    }

    public int countOrder() {
        return this.orderRepository.countAllBy();
    }

    public int countOrderBy(Long start, Long end) {
        return this.orderRepository.countByCreateTimeGreaterThanEqualAndCreateTimeLessThan(start, end);
    }

    public int countOrderLess() {
        localTime = System.currentTimeMillis();
        return this.orderRepository.countByStatusAndCreateTimeGreaterThanAndCreateTimeLessThan(1, (localTime - less), localTime);
    }

    public int countOrderMore() {
        localTime = System.currentTimeMillis();
        return this.orderRepository.countByStatusAndCreateTimeLessThan(1, (localTime - more));
    }

    public int countOrderEqual() {
        localTime = System.currentTimeMillis();
        long timeFirst = localTime - equal12;
        long timeSecond = localTime - equal24;
        return this.orderRepository.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(1, timeSecond, timeFirst);
    }

    public int sumProductByOrder() {
        long endTime = System.currentTimeMillis();
        return this.sumProductByOrder(0L, endTime);
    }

    public double allSuccessRate() {
        long endTime = System.currentTimeMillis();
        int success = this.countOrderSuccessBy(0L, endTime);
        int canceled = this.countOrderCancelBy(0L, endTime);
        return success * 100.0 / (success + canceled);
    }

    public Integer sumTotalAmount() {
        long endTime = System.currentTimeMillis();
        return this.sumTotalAmount(0L, endTime);
    }

    public Integer sumShipperEarn() {
        long endTime = System.currentTimeMillis();
        return this.sumShipperEarn(0L, endTime);
    }

    public int countOrderCancelBy(Long start, Long end) {
        return this.orderRepository.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(5, start, end);
    }

    public int countOrderSuccessBy(Long start, Long end) {
        Integer res = 0;
        try {
            res = this.orderRepository.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(4, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res == null ? 0 : res;
    }

    public Double successRate(Long start, Long end) {
        int success = this.countOrderSuccessBy(start, end);
        int canceled = this.countOrderCancelBy(start, end);

        if (success + canceled == 0) {
            return 0.0;
        }

        return success * 100.0 / (success + canceled);
    }


    public Integer sumProductByOrder(Long start, Long end) {
        Integer res = 0;
        try {
            res = this.orderRepository.sumSoldProduct(4, start, end); // doi status = 4
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res == null ? 0 : res;
    }

    public Integer sumTotalAmount(Long start, Long end) {
        Integer res = 0;
        try {
            res = this.orderRepository.sumTotalAmount(4, start, end); // doi status = 4
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res == null ? 0 : res;
    }

    public Integer sumShipperEarn(Long start, Long end) {
        Integer res = 0;
        try {
            res = this.orderRepository.sumShipperEarn(4, start, end); // doi status = 4
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res == null ? 0 : res;
    }

    public Integer countOrders(Integer status, Long start, Long end) {
        return this.orderRepository.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(status, start, end);
    }

    public List<MdlOrder> getOrderList(Integer status, Long start, Long end) {
        MdlOrderBuilder orderBuilder = new MdlOrderBuilder();
        List<FROrder> orders;
        List<MdlOrder> mdlOrders = new ArrayList<>();

        if (status == -1) {
            orders = this.orderRepository.findByCreateTimeGreaterThanEqualAndCreateTimeLessThan(start, end);
        } else {
            orders = this.orderRepository.findByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(status, start, end);
        }

        for (FROrder order : orders) {
            mdlOrders.add(orderBuilder.buildAdminTableRow(order, this.orderDetailRepository));
        }

        return mdlOrders;
    }

    private long dateToUnix(int year, int month, int days, int hour, int min, int sec) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        Integer daysInMonth = yearMonthObject.lengthOfMonth();

        if (days > daysInMonth) {
            days = 1;
            if (month == 12) {
                month = 1;
                year++;
            } else {
                month++;
            }
        }

        LocalDateTime dateTime = LocalDateTime.of(year, month, days, hour, min, sec);
        return dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }
}
