package inter.venture.project.domain.user.mapper;

import inter.venture.project.domain.user.dto.UserDto;
import inter.venture.project.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface UserMapper {
    UserMapper instance = Mappers.getMapper( UserMapper.class );

    UserDto userToUserDto(User user);

    List<UserDto> listOfUsersToListOfUserDto(List<User> user);
}
