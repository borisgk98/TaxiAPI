package space.borisgk.taxi.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.borisgk.taxi.api.model.MessageStatus;
import space.borisgk.taxi.api.model.MessageType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;
    @OneToOne
    private Trip trip;
    private byte[] data;
    private Date date;
    private MessageStatus status;
    private MessageType type;
}
