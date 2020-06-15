package inter.venture.project.domain.user.mapper;

import inter.venture.project.domain.user.dto.privateDto.UserDtoPrivate;
import inter.venture.project.domain.user.dto.publicDto.UserDto;
import inter.venture.project.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-06-15T22:15:25+0200",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 13 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername( user.getUsername() );
        userDto.setFirstName( user.getFirstName() );
        userDto.setLastName( user.getLastName() );

        return userDto;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userDto.getUsername() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        return user;
    }

    @Override
    public List<UserDto> listOfUsersToListOfUserDto(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( user.size() );
        for ( User user1 : user ) {
            list.add( userToUserDto( user1 ) );
        }

        return list;
    }

    @Override
    public User userDtoPrivateToUser(UserDtoPrivate userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userDto.getUsername() );
        user.setPassword( userDto.getPassword() );
        user.setFirstName( userDto.getFirstName() );
        user.setLastName( userDto.getLastName() );

        return user;
    }
}
