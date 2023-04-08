package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

  @Query("SELECT teacher FROM Teacher teacher " +
      "LEFT JOIN Group group ON teacher.id = group.teacher.id " +
      "WHERE group.teacher IS NULL")
  List<Teacher> findAllWhoAreNotClassTeacher();

}
