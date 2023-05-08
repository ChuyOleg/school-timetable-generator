package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.Group;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

  @EntityGraph(attributePaths = { "teacher", "groupLimits", "groupLimits.interschoolCombine" })
  List<Group> findAllByUserId(Long userId);

  @Modifying
  @Query(value = """
    DELETE FROM class_group
    WHERE user_id IN :userIdSet AND modified_date < (NOW() - INTERVAL '28 DAY')
  """, nativeQuery = true)
  void deleteStaleGroups(@Param("userIdSet") Set<Long> userIdSet);

}
