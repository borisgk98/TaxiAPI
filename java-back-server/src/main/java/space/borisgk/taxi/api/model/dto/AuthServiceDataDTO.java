package space.borisgk.taxi.api.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthServiceDataDTO {

    private Integer id;

    private String authService;
    private String socialId;
    private Long friendsHash;
}
