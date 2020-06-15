package inter.venture.project.domain.user.mapper;

import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.dto.privateDto.UserDtoPrivate;
import inter.venture.project.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface UserMapper {
    UserMapper instance = Mappers.getMapper( UserMapper.class );

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    List<UserDto> listOfUsersToListOfUserDto(List<User> user);

    User userDtoPrivateToUser(UserDtoPrivate userDto);
}
