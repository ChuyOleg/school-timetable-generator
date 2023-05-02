package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

  Optional<TimeTable> findByUserId(Long userId);

}
