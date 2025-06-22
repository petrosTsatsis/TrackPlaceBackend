package track.place.com.util.mapper;

import org.mapstruct.Mapper;
import track.place.com.dto.UserDTO;
import track.place.com.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);
}
