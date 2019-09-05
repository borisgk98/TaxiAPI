package space.borisgk.taxi.api.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthServiceDataDTO {
    
    private String socialId;
    private Long friendsHash;
}
