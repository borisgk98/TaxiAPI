package space.borisgk.taxi.api.model.mapping;

import org.junit.Assert;
import org.junit.Test;
import space.borisgk.taxi.api.model.dto.AuthServiceDataDTO;
import space.borisgk.taxi.api.model.dto.UserDto;
import space.borisgk.taxi.api.model.entity.User;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StaxMapperTest {

    private final StaxMapper mapper = StaxMapper.instance;

    @Test
    public void dto2e() {
        UserDto userDto = UserDto.builder()
                .firstName("Boris")
                .lastName("K")
                .authServicesData(new HashMap<>(){{
                    put("VK", AuthServiceDataDTO.builder().friendsHash(1l).socialId("sid").build());
                }})
                .avatarUrl("url")
                .id("1")
                .build();
        User user = mapper.dto2e(userDto);
        Assert.assertEquals(user.getId().longValue(), 1l);
    }

    @Test
    public void e2dto() {
    }

    @Test
    public void dto2e1() {
    }

    @Test
    public void e2dto1() {
    }

    @Test
    public void dto2e2() {
    }

    @Test
    public void e2dto2() {
    }

    @Test
    public void authServiceDataDtoToAuthServiceData() {
    }

    @Test
    public void authServiceDataToAuthServiceDataDto() {
    }
}