package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<FRAccount, Integer> {


    Optional<FRAccount> findByPhone(String phone);
}
