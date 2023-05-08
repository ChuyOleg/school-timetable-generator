package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

  List<Subject> findAllByUserId(Long userId);

  @Modifying
  @Query(value = """
    DELETE FROM subject
    WHERE user_id IN :userIdSet AND modified_date < (NOW() - INTERVAL '28 DAY')
  """, nativeQuery = true)
  void deleteStaleSubjects(@Param("userIdSet") Set<Long> userIdSet);

}
