package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<FRAccount, Integer> {

    FRAccount findByPhoneNumberAndPassword(String phoneNumber, String password);

    FRAccount findByAccountNameAndPassword(String accountName, String password);
}
