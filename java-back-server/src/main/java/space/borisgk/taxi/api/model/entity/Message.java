package space.borisgk.taxi.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import space.borisgk.taxi.api.model.IEntity;
import space.borisgk.taxi.api.model.MessageStatus;
import space.borisgk.taxi.api.model.MessageType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class Message implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @Column(name = "data")
    private byte[] data;
    @Column(name = "date")
    private Date date;
    @Column(name = "status")
    private MessageStatus status;
    @Column(name = "type")
    private MessageType type;
}
