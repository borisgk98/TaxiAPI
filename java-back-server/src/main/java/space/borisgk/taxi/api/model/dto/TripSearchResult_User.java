package space.borisgk.taxi.api.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripSearchResult_User {
    private String id;

    private String firstName, lastName;
    private String avatarUrl;
    private Boolean isFriend;
}
