package capstone.fps.repository;

import capstone.fps.entity.FRPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTypeRepo extends JpaRepository<FRPaymentType, Integer> {

    Optional<FRPaymentType> findByType(String type);
}
