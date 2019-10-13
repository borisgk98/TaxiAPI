package space.borisgk.taxi.api.model.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import space.borisgk.taxi.api.model.dto.AuthServiceDataDTO;
import space.borisgk.taxi.api.model.dto.TripDto;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.AuthService;
import space.borisgk.taxi.api.model.entity.AuthServiceData;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.model.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Mapper
public interface StaxMapper {

    StaxMapper instance = Mappers.getMapper(StaxMapper.class);

    @Mapping(source = "socialId", target = "socialId")
    @Mapping(source = "friendsHash", target = "friendsHash")
    AuthServiceData dto2e(AuthServiceDataDTO authServiceDataDTO);

    @Mapping(source = "socialId", target = "socialId")
    @Mapping(source = "friendsHash", target = "friendsHash")
    AuthServiceDataDTO e2dto(AuthServiceData authServiceData);

    @Mapping(source = "authServicesData", target = "authServicesData", qualifiedByName = "authServiceDataDtoToAuthServiceData")
    User dto2e(UserDto userDto);

    @Mapping(source = "authServicesData", target = "authServicesData", qualifiedByName = "authServiceDataToAuthServiceDataDto")
    UserDto e2dto(User user);

    @Mapping(source = "date", target = "date", qualifiedByName = "string2date")
    Trip dto2e(TripDto tripDto);

    @Mapping(source = "date", target = "date", dateFormat = "YYYY-MM-dd'T'HH:mm:ss'Z'")
    TripDto e2dto(Trip trip);

    @Named("string2date")
    default Date string2date(String s) throws ParseException {
        Pattern pattern = Pattern.compile("(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)Z");
        Matcher matcher = pattern.matcher(s);
        matcher.find();
        Integer y = Integer.parseInt(matcher.group(1));
        Integer mo = Integer.parseInt(matcher.group(2));
        Integer d = Integer.parseInt(matcher.group(3));
        Integer min = Integer.parseInt(matcher.group(5));
        Integer hh = Integer.parseInt(matcher.group(4));
        Integer ms = Integer.parseInt(matcher.group(6));
        Date date = new Date();
        date.setHours(hh);
        date.setDate(d);
        date.setMinutes(min);
        date.setMonth(mo - 1);
        date.setYear(y - 1900);
        return date;
    }

    @Named("authServiceDataDtoToAuthServiceData")
    default Set<AuthServiceData> authServiceDataDtoToAuthServiceData(Map<String, AuthServiceDataDTO> authServiceDataDTOMap) {
        return authServiceDataDTOMap.entrySet().stream()
                .map(x -> AuthServiceData.builder()
                        .authService(AuthService.fromName(x.getKey()))
                        .socialId(x.getValue().getSocialId())
                        .friendsHash(x.getValue().getFriendsHash())
                        .build()
                )
                .collect(Collectors.toSet());
    }

    @Named("authServiceDataToAuthServiceDataDto")
    default Map<String, AuthServiceDataDTO> authServiceDataToAuthServiceDataDto(Set<AuthServiceData> authServiceData) {
        Map<String, AuthServiceDataDTO> res = new HashMap<>();
        authServiceData.forEach(x -> {
            res.put(x.getAuthService().getName(), e2dto(x));
        });
        return res;
    }
}
