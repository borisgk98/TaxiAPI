package space.borisgk.taxi.api.converter;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Autowired
    private Mapper mapper;

    @Override
    public UserDto map(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        Map<String, String> socialIds = new HashMap<>();
        for (AuthServiceData authServiceData : user.getAuthServicesData()) {
            socialIds.put(authServiceData.getAuthService().name(), authServiceData.getAuthServiceUserId());
        }
        userDto.setSocialIds(socialIds);
        return userDto;
    }
}
