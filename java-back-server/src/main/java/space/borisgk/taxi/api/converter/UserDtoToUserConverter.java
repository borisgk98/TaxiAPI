package space.borisgk.taxi.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.model.dto.AuthServiceDataDTO;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.AuthService;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.User;

import java.util.stream.Collectors;

@Component
public class UserDtoToUserConverter implements IConverter<UserDto, User> {

//    @Autowired
//    private IConverter<AuthServiceDataDTO, AuthServiceData> authServiceDataDTOAuthServiceDataConverter;

    @Override
    public User map(UserDto o) {
        User.UserBuilder userBuilder = User.builder();
        if (o.getId() != null) {
            userBuilder = userBuilder.id(Integer.parseInt(o.getId()));
        }
        userBuilder = userBuilder.authServicesData(
                        o.getAuthServicesData().entrySet().stream()
                                .map(x -> AuthServiceData.builder()
                                        .authService(AuthService.fromName(x.getKey()))
                                        .socialId(x.getValue().getSocialId())
                                        .friendsHash(x.getValue().getFriendsHash())
                                        .build()
                                )
                                .collect(Collectors.toSet())
                );
        userBuilder = userBuilder.avatarUrl(o.getAvatarUrl());
        userBuilder = userBuilder.firstName(o.getFirstName());
        userBuilder = userBuilder.lastName(o.getLastName());
        return userBuilder.build();
    }
}
