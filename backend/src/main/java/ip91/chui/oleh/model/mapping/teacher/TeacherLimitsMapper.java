package ip91.chui.oleh.model.mapping.teacher;

import ip91.chui.oleh.model.dto.teacher.TeacherLimitsDto;
import ip91.chui.oleh.model.entity.teacher.TeacherLimits;
import ip91.chui.oleh.model.mapping.teacher.limit.DesiredPeriodLimitMapper;
import ip91.chui.oleh.model.mapping.teacher.limit.FreeDayLimitMapper;
import ip91.chui.oleh.model.mapping.teacher.limit.LessonsOrderLimitMapper;
import ip91.chui.oleh.model.mapping.teacher.limit.MaxLessonsLimitMapper;
import java.util.Optional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
    DesiredPeriodLimitMapper.class, FreeDayLimitMapper.class,
    LessonsOrderLimitMapper.class, MaxLessonsLimitMapper.class })
public interface TeacherLimitsMapper {

  TeacherLimitsDto toDto(TeacherLimits limits);

  TeacherLimits toEntity(TeacherLimitsDto limitsDto);

  @AfterMapping
  default void setReversedDependency(@MappingTarget TeacherLimits teacherLimits) {
    Optional.ofNullable(teacherLimits.getDesiredPeriodLimits())
        .ifPresent(limits -> limits.forEach(limit -> limit.setTeacherLimits(teacherLimits)));

    Optional.ofNullable(teacherLimits.getFreeDayLimits())
        .ifPresent(limits -> limits.forEach(limit -> limit.setTeacherLimits(teacherLimits)));

    Optional.ofNullable(teacherLimits.getMaxLessonsLimit())
        .ifPresent(limit -> limit.setTeacherLimits(teacherLimits));

    Optional.ofNullable(teacherLimits.getLessonsOrderLimit())
        .ifPresent(limit -> limit.setTeacherLimits(teacherLimits));
  }
}
