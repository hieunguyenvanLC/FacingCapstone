package capstone.fps.repository;

import capstone.fps.entity.FRAccount;
import capstone.fps.entity.FRReceiveMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiveMemberRepo extends JpaRepository<FRReceiveMember, Integer> {

    List<FRReceiveMember> findAllByAccount(FRAccount frAccount);
}
