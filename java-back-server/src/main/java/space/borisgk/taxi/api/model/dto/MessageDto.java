package space.borisgk.taxi.api.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.model.entity.User;

import javax.persistence.*;
import java.util.Date;

@Builder
@Setter
@Getter
public class MessageDto {
    private Integer id;

    private Integer userId;
    private Integer tripId;
    private String message;
    private Date date;
}
