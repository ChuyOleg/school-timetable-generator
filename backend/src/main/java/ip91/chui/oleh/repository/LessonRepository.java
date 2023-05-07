package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

  @Modifying
  @Query(value = """
    DELETE FROM lesson
    WHERE time_table_id = :timetableId
  """, nativeQuery = true)
  void deleteAllByTimeTableId(@Param("timetableId") Long timetableId);

}
