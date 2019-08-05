package space.borisgk.taxi.api.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany
    private List<AuthServiceData> authServicesData;
}
