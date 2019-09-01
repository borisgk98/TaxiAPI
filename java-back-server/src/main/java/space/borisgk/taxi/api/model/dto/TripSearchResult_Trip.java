package space.borisgk.taxi.api.model.dto;

import lombok.*;
import space.borisgk.taxi.api.model.TripStatus;

import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripSearchResult_Trip {
    private String id;

    private Date date;
    private Double latFrom, latTo, longFrom, longTo;
    private TripStatus status= TripStatus.ACTIVE;
    private String addressTo, addressFrom;
    private List<TripSearchResult_User> users;
}
