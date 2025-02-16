package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.ShiftsIntersection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftsIntersectionRepository extends JpaRepository<ShiftsIntersection, Long> {

  List<ShiftsIntersection> findAllByUserId(Long userId);
}
