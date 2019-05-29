package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRTransaction;
import com.google.gson.annotations.Expose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<FRTransaction, Integer> {
    List<FRTransaction> findAllByAccount(FRAccount account);
}
