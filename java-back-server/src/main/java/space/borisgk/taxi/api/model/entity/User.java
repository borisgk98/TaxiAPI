package space.borisgk.taxi.api.model.entity;

import lombok.*;
import space.borisgk.taxi.api.model.IEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "taxi_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements IEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName, lastName;
    private String avatarUrl;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "taxi_user_auth_service_data")
    private Set<AuthServiceData> authServicesData;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "taxi_user_friends")
    private Set<User> friends;
}
