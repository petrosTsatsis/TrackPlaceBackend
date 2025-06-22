package track.place.com.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import track.place.com.dto.NotificationDTO;
import track.place.com.entities.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mappings({
        @Mapping(source = "user.id", target = "userId")
    })
    NotificationDTO toDTO(Notification entity);

    @Mappings({
        @Mapping(target = "user.id", source = "userId")
    })
    Notification toEntity(NotificationDTO dto);
}

