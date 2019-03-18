package capstone.fps.service;

import capstone.fps.entity.FRRole;
import capstone.fps.repository.*;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class HomeService {
    private ShipperRepo shipperRepo;
    private AccountRepo accountRepo;
    private RoleRepo roleRepo;
    private OrderRepo orderRepository;
    private OrderDetailRepo orderDetailRepository;
    private StoreRepo storeRepo;

    public HomeService(
            ShipperRepo shipperRepo,
            AccountRepo accountRepo,
            RoleRepo roleRepo,
            StoreRepo storeRepo,
            OrderRepo orderRepository,
            OrderDetailRepo orderDetailRepository
    ) {
        this.shipperRepo = shipperRepo;
        this.accountRepo = accountRepo;
        this.roleRepo = roleRepo;
        this.storeRepo = storeRepo;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public int countNewShipper() {
        FRRole role = this.roleRepo.findById(3).get();
        return this.accountRepo.countByRoleAndStatus(role, 1);
    }

    //    public int countNewCus(int month, int year) {
//        FRRole role = this.roleRepo.findById(2).get();
//        long start = this.dateToUnix(year, month, 1, 0, 0, 0);
//        long end = this.dateToUnix(year, month + 1, 1, 0, 0, 0);
//        return this.accountRepo.countByRoleAndStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(role, 1, start, end);
//    }
    public int countNewCus() {
        FRRole role = this.roleRepo.findById(2).get();
        return this.accountRepo.countByRoleAndStatus(role, 1);
    }
//
//    public int countNewStore(int month, int year) {
//        long start = this.dateToUnix(year, month, 1, 0, 0, 0);
//        long end = this.dateToUnix(year, month + 1, 1, 0, 0, 0);
//        return this.storeRepo.countByStatusAndCreateTimeGreaterThanEqualAndCreateTimeLessThan(1, start, end);
//    }

    public int countNewStore() {
        return this.storeRepo.countByStatus(1);
    }

    //    public int countNewOrder(int month, int year) {
//        long start = this.dateToUnix(year, month, 1, 0, 0, 0);
//        long end = this.dateToUnix(year, month + 1, 1, 0, 0, 0);
//        return this.orderRepository.countByCreateTimeGreaterThanEqualAndCreateTimeLessThan(start, end);
//    }
    public int countNewOrder() {
        return this.orderRepository.countAllBy();
    }


    private long dateToUnix(int year, int month, int days, int hour, int min, int sec) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, days, hour, min, sec);
        return dateTime.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }
}
