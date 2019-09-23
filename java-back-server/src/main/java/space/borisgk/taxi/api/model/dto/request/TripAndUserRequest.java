package space.borisgk.taxi.api.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class TripAndUserRequest {
    private String tripId;
    private String userId;
}
