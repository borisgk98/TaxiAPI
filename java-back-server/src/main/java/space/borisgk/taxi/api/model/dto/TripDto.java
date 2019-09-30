package space.borisgk.taxi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import space.borisgk.taxi.api.model.TripStatus;
import space.borisgk.taxi.api.model.entity.User;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private String id;

    @JsonFormat(pattern = "YYYY-MM-DD'T'hh:mm:ss")
    private Date date;
    private Double latFrom, latTo, longFrom, longTo;
    private TripStatus status= TripStatus.ACTIVE;
    private String addressTo, addressFrom;
    private List<UserDto> users;
    private Boolean hasFriends;
}
