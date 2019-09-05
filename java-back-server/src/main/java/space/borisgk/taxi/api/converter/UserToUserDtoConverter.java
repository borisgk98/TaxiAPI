package space.borisgk.taxi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.model.dto.AuthServiceDataDTO;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserToUserDtoConverter implements IConverter<User, UserDto> {

//    @Autowired
//    private IConverter<AuthServiceData, AuthServiceDataDTO> authServiceDataAuthServiceDataDTOIConverter;

    @Override
    public UserDto map(User o) {
        UserDto.UserDtoBuilder userDtoBuilder = UserDto.builder();
        if (o.getId() != null) {
            userDtoBuilder = userDtoBuilder.id(o.getId().toString());
        }
        Map<String, AuthServiceDataDTO> map = new HashMap<>();
        o.getAuthServicesData().stream().forEach(x -> {
            map.put(x.getAuthService().getName(), AuthServiceDataDTO.builder()
                    .socialId(x.getSocialId())
                    .friendsHash(x.getFriendsHash())
                    .build());
        });
        userDtoBuilder = userDtoBuilder.authServicesData(map);
        userDtoBuilder = userDtoBuilder.avatarUrl(o.getAvatarUrl());
        userDtoBuilder = userDtoBuilder.firstName(o.getFirstName());
        userDtoBuilder = userDtoBuilder.lastName(o.getLastName());
        return userDtoBuilder.build();
    }
}
