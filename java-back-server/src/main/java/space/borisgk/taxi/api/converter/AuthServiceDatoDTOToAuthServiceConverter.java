package space.borisgk.taxi.api.converter;

import org.springframework.stereotype.Component;
import space.borisgk.taxi.api.model.dto.AuthServiceDataDTO;
import space.borisgk.taxi.api.model.entity.AuthService;
import space.borisgk.taxi.api.model.entity.AuthServiceData;

@Component
public class AuthServiceDatoDTOToAuthServiceConverter implements IConverter<AuthServiceDataDTO, AuthServiceData>{

    @Override
    public AuthServiceData map(AuthServiceDataDTO o) {
        return AuthServiceData.builder()
                .authService(AuthService.fromName(o.getAuthService()))
                .friendsHash(o.getFriendsHash())
                .id(o.getId())
                .socialId(o.getSocialId())
                .build();
    }
}
