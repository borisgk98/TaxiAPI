package space.borisgk.taxi.api.converter;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.model.AuthService;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;

import java.util.stream.Collectors;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Autowired
    private Mapper mapper;

    // TODO обработка ошибок (например неверный ключ в мапе (нет такого enum)) или socialIds == null
    @Override
    public User map(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setAuthServicesData(userDto.getSocialIds().entrySet().stream()
                .map(x -> AuthServiceData
                        .builder()
                        .authService(AuthService.valueOf(x.getKey()))
                        .authServiceUserId(x.getValue()).build()).collect(Collectors.toSet()));
        return user;
    }
}
