package space.borisgk.taxi.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import space.borisgk.taxi.api.model.MessageStatus;
import space.borisgk.taxi.api.model.MessageType;
import space.borisgk.taxi.api.model.entity.Trip;
import space.borisgk.taxi.api.model.entity.User;

import javax.persistence.*;
import java.util.Date;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class MessageDto {
    private String id;

    private String userId;
    private String tripId;
    /**
     * base64 data
     */
    private String data;
    private Date date;
    private MessageStatus status;
    private MessageType type;
}
