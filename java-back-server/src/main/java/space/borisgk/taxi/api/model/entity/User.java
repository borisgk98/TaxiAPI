package space.borisgk.taxi.api.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "taxi_user_auth_service_data",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "auth_service_data_id",
                    referencedColumnName = "id"
            )
    )
    private Set<AuthServiceData> authServicesData;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "taxi_user_friends",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "friend_id",
                    referencedColumnName = "id"
            )
    )
    @JsonIgnore
    private Set<User> friends;

    @Transient
    private Boolean isFriend;
}
