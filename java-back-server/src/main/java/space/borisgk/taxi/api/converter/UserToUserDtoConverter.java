package space.borisgk.taxi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.model.dto.AuthServiceDataDTO;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;

import java.util.stream.Collectors;

@Component
public class UserToUserDtoConverter implements IConverter<User, UserDto> {

    @Autowired
    private IConverter<AuthServiceData, AuthServiceDataDTO> authServiceDataAuthServiceDataDTOIConverter;

    @Override
    public UserDto map(User o) {
        UserDto.UserDtoBuilder userDtoBuilder = UserDto.builder();
        if (o.getId() != null) {
            userDtoBuilder = userDtoBuilder.id(o.getId().toString());
        }
        userDtoBuilder = userDtoBuilder.authServicesData(
                    o.getAuthServicesData().stream()
                            .map(x -> authServiceDataAuthServiceDataDTOIConverter.map(x))
                            .collect(Collectors.toSet())
        );
        userDtoBuilder = userDtoBuilder.avatarUrl(o.getAvatarUrl());
        userDtoBuilder = userDtoBuilder.firstName(o.getFirstName());
        userDtoBuilder = userDtoBuilder.lastName(o.getLastName());
        return userDtoBuilder.build();
    }
}
