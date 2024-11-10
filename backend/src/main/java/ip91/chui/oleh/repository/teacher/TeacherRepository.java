package ip91.chui.oleh.repository.teacher;

import ip91.chui.oleh.model.dto.projection.TeacherProjection;
import ip91.chui.oleh.model.entity.teacher.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

  @Query("""
      SELECT teacher FROM Teacher teacher
      INNER JOIN FETCH teacher.subjects
      LEFT JOIN Group group ON teacher.id = group.teacher.id
      WHERE group.teacher IS NULL AND teacher.user.id = :userId
      """)
  List<Teacher> findAllWhoAreNotClassTeacher(@Param("userId") Long userId);

  @EntityGraph(attributePaths = { "subjects" })
  List<Teacher> findAllByUserId(Long userId);

  @Query(value = """
     WITH subject_limits_by_user_id AS (
        SELECT sl.teacher_id, teacher_2_id, hours FROM
         (
            SELECT * FROM class_group
            WHERE user_id = :userId
         ) AS cg
          JOIN group_limits gl ON cg.id = gl.class_group_id
          JOIN subject_limits sl ON gl.id = sl.group_limits_id
     )

     SELECT teacher_id as id, SUM(sum_hours) as totalHours
     FROM (
        SELECT teacher_id, SUM(hours) as sum_hours FROM subject_limits_by_user_id
        GROUP BY teacher_id
        UNION ALL
        SELECT teacher_2_id as teacher_id, SUM(hours) as sum_hours FROM subject_limits_by_user_id
        WHERE teacher_2_id IS NOT null
        GROUP BY teacher_2_id
     ) as subquery
     GROUP BY teacher_id
     """, nativeQuery = true)
  List<TeacherProjection> findActualHoursForTeachersByUserId(@Param("userId") Long userId);

  @Modifying
  @Query(value = """
    DELETE FROM teacher
    WHERE user_id IN :userIdSet AND modified_date < (NOW() - INTERVAL '28 DAY')
  """, nativeQuery = true)
  void deleteStaleTeachers(@Param("userIdSet") Set<Long> userIdSet);

}
