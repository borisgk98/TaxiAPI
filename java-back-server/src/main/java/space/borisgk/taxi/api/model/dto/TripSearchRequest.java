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
public class TripSearchRequest {

    private Date startDate, finishDate;
    private Double latFrom, latTo, longFrom, longTo;
}
