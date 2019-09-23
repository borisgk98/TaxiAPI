package space.borisgk.taxi.api.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.borisgk.taxi.api.model.TripStatus;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserGerTripsRequest {
    private String userId;
    private TripStatus tripStatus;
}
