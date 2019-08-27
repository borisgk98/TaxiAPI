package space.borisgk.taxi.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.borisgk.taxi.api.model.entity.Trip;

import java.util.Date;

@Setter
@Getter
@Builder
public class TripSearchRequest implements ISearchRequest<Trip> {
    private Date time;
    private Integer deltaTimeMin;
    private Double xcoord, ycoord, searchRadius;
}
