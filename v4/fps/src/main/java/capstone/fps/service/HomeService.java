package capstone.fps.service;

import capstone.fps.entity.FROrder;
import capstone.fps.entity.FRRole;
import capstone.fps.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
//
//    public int countNewStore(int month, int year) {
//        long start = this.dateToUnix(year, month, 1, 0, 0, 0);
//        long end = this.dateToUnix(year, month + 1, 1, 0, 0, 0);
//        return this.storeRepo.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(1, start, end);
//    }

    public int countStore() {
        return this.storeRepo.countByStatus(1);
    }

    //    public int countNewOrder(int month, int year) {
//        long start = this.dateToUnix(year, month, 1, 0, 0, 0);
//        long end = this.dateToUnix(year, month + 1, 1, 0, 0, 0);
//        return this.orderRepository.countByCreateTimeGreaterThanEqualAndCreateTimeLessThan(start, end);
//    }
    public int countOrder() {
        return this.orderRepository.countAllBy();
    }

    public int countOrderBy(int month, int year, int day) {
        long start = this.dateToUnix(year, month, day, 0, 0, 0);
        long end = this.dateToUnix(year, month, day + 1, 0, 0, 0);
        return this.orderRepository.countByCreateTimeGreaterThanEqualAndCreateTimeLessThan(start, end);
    }

    public int countOrderCancelBy(int month, int year, int day) {
        int status;
        long start = this.dateToUnix(year, month, day, 0, 0, 0);
        long end = this.dateToUnix(year, month, day + 1, 0, 0, 0);
        return this.orderRepository.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(5, start, end);
    }

    public int countOrderSuccessBy(int month, int year, int day) {
        int status;
        long start = this.dateToUnix(year, month, day, 0, 0, 0);
        long end = this.dateToUnix(year, month, day + 1, 0, 0, 0);
        return this.orderRepository.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(4, start, end);
    }


    private long dateToUnix(int year, int month, int days, int hour, int min, int sec) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, days, hour, min, sec);
        return dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }
}
