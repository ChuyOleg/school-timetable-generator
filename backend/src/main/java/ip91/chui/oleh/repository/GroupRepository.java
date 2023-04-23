package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

  List<Group> findAllByUserId(Long userId);

}
