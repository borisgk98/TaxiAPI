package space.borisgk.taxi.api.model.entity;

import lombok.*;
import space.borisgk.taxi.api.model.TripStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private Double latFrom, latTo, longFrom, longTo;
    private TripStatus status= TripStatus.ACTIVE;
    private String addressTo, addressFrom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trip_users")
    private Set<User> users;
}
