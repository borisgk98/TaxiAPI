package space.borisgk.taxi.api.model.entity;

import lombok.*;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName, lastName;
    private String avatarUrl;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "taxi_user_auth_service_data")
    private Set<AuthServiceData> authServicesData;
}
