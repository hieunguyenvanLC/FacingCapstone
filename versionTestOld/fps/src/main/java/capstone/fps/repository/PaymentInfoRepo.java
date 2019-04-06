package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRPaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public interface PaymentInfoRepo extends JpaRepository<FRPaymentInformation, Integer> {

    List<FRPaymentInformation> findAllByAccount(FRAccount frAccount);

}
