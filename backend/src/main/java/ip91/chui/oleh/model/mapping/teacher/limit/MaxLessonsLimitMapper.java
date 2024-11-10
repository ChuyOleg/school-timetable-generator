package ip91.chui.oleh.model.mapping.teacher.limit;

import ip91.chui.oleh.model.dto.teacher.limit.MaxLessonsLimitDto;
import ip91.chui.oleh.model.entity.teacher.limit.MaxLessonsLimit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaxLessonsLimitMapper {

  MaxLessonsLimitDto toDto(MaxLessonsLimit limit);

  MaxLessonsLimit toEntity(MaxLessonsLimitDto limitDto);
}
