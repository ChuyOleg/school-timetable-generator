package ip91.chui.oleh.repository.teacher.limit;

import ip91.chui.oleh.model.entity.teacher.TeacherLimits;
import ip91.chui.oleh.model.entity.teacher.limit.MaxLessonsLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface MaxLessonsLimitRepository extends JpaRepository<MaxLessonsLimit, Long> {

  @Modifying
  void deleteByTeacherLimits(TeacherLimits teacherLimits);
}
