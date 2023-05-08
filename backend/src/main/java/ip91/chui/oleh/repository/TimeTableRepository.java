package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.TimeTable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

  @EntityGraph(attributePaths = {
      "lessons", "lessons.group", "lessons.group.groupLimits", "lessons.teacher",
      "lessons.subject", "lessons.timeSlot", "lessons.room"
  })
  Optional<TimeTable> findByUserId(Long userId);

  @Modifying
  @Query(value = """
    DELETE FROM time_table
    WHERE modified_date < (NOW() - INTERVAL '7 DAY')
  """, nativeQuery = true)
  void deleteStaleTimetables();

}
