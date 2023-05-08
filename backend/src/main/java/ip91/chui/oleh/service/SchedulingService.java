package ip91.chui.oleh.service;

import ip91.chui.oleh.model.entity.User;
import ip91.chui.oleh.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SchedulingService {

  private final UserRepository userRepository;
  private final TimeTableRepository timeTableRepository;
  private final GroupRepository groupRepository;
  private final TeacherRepository teacherRepository;
  private final SubjectRepository subjectRepository;
  private final RoomRepository roomRepository;

  @Scheduled(cron = "@weekly")
  protected void deleteStaleTimetables() {
    log.info("Scheduled task for deleting stale timetables has been started: " + LocalDateTime.now());
    timeTableRepository.deleteStaleTimetables();
    log.info("Scheduled task for deleting stale timetables has been finished: " + LocalDateTime.now());
  }

  @Scheduled(cron = "@monthly")
  protected void deleteStaleBaseEntities() {
    log.info("Scheduled task for deleting stale base entities has been started: " + LocalDateTime.now());
    List<User> users = userRepository.findAllWithoutTimetable();
    Set<Long> userIdSet = users.stream().map(User::getId).collect(Collectors.toSet());

    log.info("Affected users by this task: " + userIdSet);

    groupRepository.deleteStaleGroups(userIdSet);
    teacherRepository.deleteStaleTeachers(userIdSet);
    subjectRepository.deleteStaleSubjects(userIdSet);
    roomRepository.deleteStaleRooms(userIdSet);
    log.info("Scheduled task for deleting stale base entities has been finished: " + LocalDateTime.now());
  }

}
