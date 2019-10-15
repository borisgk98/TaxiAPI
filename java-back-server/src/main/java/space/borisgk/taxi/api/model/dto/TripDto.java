package space.borisgk.taxi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import space.borisgk.taxi.api.model.TripStatus;
import space.borisgk.taxi.api.model.entity.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private String id;

//    @JsonFormat(pattern = "YYYY-MM-dd'T'hh:mm:ss'Z'")
    private String date;
    private Double latFrom, latTo, longFrom, longTo;
    private TripStatus status= TripStatus.ACTIVE;
    private String addressTo, addressFrom;
    private List<UserDto> users;
    private Boolean hasFriends;
    private Integer numberOfSeats;
}
