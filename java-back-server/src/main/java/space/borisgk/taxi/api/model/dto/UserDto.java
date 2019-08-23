package space.borisgk.taxi.api.model.dto;

import lombok.*;
import space.borisgk.taxi.api.model.entity.AuthServiceData;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Map;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String id;

    private String firstName, lastName;
    private String avatarUrl;
    private Map<String, String> socialIds;
}
