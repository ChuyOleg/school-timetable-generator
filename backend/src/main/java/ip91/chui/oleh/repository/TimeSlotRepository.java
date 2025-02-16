package ip91.chui.oleh.repository;

import ip91.chui.oleh.model.entity.TimeSlot;
import ip91.chui.oleh.model.enumeration.WeekType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

  Optional<TimeSlot> findTimeSlotByWeekTypeAndDayAndShiftAndLessonNumber(
      WeekType weekType, DayOfWeek day, int shift, int lessonNumber);
}
