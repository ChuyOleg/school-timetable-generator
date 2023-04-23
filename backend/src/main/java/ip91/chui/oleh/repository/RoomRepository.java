package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  List<Room> findAllByUserId(Long userId);

}
