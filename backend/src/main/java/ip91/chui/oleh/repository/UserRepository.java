package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  @Query(value = """
    SELECT u.* FROM users u
    LEFT JOIN time_table tt on u.id = tt.user_id
    WHERE tt.id is NULL
  """, nativeQuery = true)
  List<User> findAllWithoutTimetable();

}
