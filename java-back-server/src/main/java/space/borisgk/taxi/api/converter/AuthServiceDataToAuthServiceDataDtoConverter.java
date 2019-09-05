//package space.borisgk.taxi.api.converter;
//
//import org.springframework.stereotype.Component;
//import space.borisgk.taxi.api.model.dto.AuthServiceDataDTO;
//import space.borisgk.taxi.api.model.entity.AuthServiceData;
//
//@Component
//public class AuthServiceDataToAuthServiceDataDtoConverter implements IConverter<AuthServiceData, AuthServiceDataDTO> {
//    @Override
//    public AuthServiceDataDTO map(AuthServiceData o) {
//        return AuthServiceDataDTO.builder()
//                .authService(o.getAuthService().getName())
//                .friendsHash(o.getFriendsHash())
//                .socialId(o.getSocialId())
//                .id(o.getId())
//                .build();
//    }
//}
