package space.borisgk.taxi.api.model.entity;

import lombok.*;
import space.borisgk.taxi.api.model.IEntity;
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
public class Trip implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private Date date;
    @Column(name = "lat_from")
    private Double latFrom;
    @Column(name = "lat_to")
    private Double latTo;
    @Column(name = "long_from")
    private Double longFrom;
    @Column(name = "long_to")
    private Double longTo;
    @Column(name = "status")
    private TripStatus status= TripStatus.ACTIVE;
    @Column(name = "address_to")
    private String addressTo;
    @Column(name = "address_from")
    private String addressFrom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "trip_users",
            joinColumns = @JoinColumn(
                    name = "trip_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            )
    )
    private Set<User> users;
}
