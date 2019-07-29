package space.borisgk.taxi.api.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id, vkId;
    private String firstName, lastName;
    private String photoUrl;
}
