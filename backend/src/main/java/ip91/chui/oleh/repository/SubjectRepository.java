package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

  List<Subject> findAllByUserId(Long userId);

}
