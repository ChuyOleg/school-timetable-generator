package ip91.chui.oleh.model.mapping.teacher.limit;

import ip91.chui.oleh.model.dto.teacher.limit.LessonsOrderLimitDto;
import ip91.chui.oleh.model.entity.teacher.limit.LessonsOrderLimit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LessonsOrderLimitMapper {

  LessonsOrderLimitDto toDto(LessonsOrderLimit limit);

  LessonsOrderLimit toEntity(LessonsOrderLimitDto limitDto);
}
