package capstone.fps.service;

import capstone.fps.repository.OrderDetailRepo;
import capstone.fps.repository.OrderRepo;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    private OrderRepo orderRepository;
    private OrderDetailRepo orderDetailRepository;

    public HomeService(
            OrderRepo orderRepository,
            OrderDetailRepo orderDetailRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;

    }

    public int countNewShipper(int month, long year) {
        Integer cnt = this.orderRepository.countByStatusAndCreateTimeGreaterThanEqual(4, 0L);
        return cnt;
    }
}
