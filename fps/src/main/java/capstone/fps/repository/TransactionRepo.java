package capstone.fps.repository;

import capstone.fps.entity.FRTransaction;
import com.google.gson.annotations.Expose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends JpaRepository<FRTransaction, Integer> {

}
