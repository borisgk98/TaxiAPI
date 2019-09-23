package space.borisgk.taxi.api.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripSearchRequest {

    private Date startDate, finishDate;
    private Double latFrom, latTo, longFrom, longTo;
    private String userId;
}
