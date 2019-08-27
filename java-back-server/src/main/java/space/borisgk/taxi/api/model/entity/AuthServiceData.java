package space.borisgk.taxi.api.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import space.borisgk.taxi.api.model.IEntity;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_service_data")
public class AuthServiceData implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "auth_service")
    private AuthService authService;
    @Column(name = "social_id")
    private String socialId;

    @Column(
            columnDefinition = "BIGINT COMMENT " +
            "'Hash for all user in this social network for user. " +
            "Compute as the sum modulo 2^64 of users\' ids." +
            "This field should be interpreted as a UNSIGNED LONG.'",
            name = "friends_hash"
    )
    @Range(min = 0)
    private Long friendsHash;
}
