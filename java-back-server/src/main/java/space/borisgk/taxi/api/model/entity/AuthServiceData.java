package space.borisgk.taxi.api.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import space.borisgk.taxi.api.model.AuthService;

import javax.persistence.*;
import javax.validation.Constraint;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth_service_data")
public class AuthServiceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private AuthService authService;
    private String authServiceUserId;

    @Column(columnDefinition = "BIGINT COMMENT " +
            "'Hash for all user in this social network for user. " +
            "Compute as the sum modulo 2^64 of users\' ids." +
            "This field should be interpreted as a UNSIGNED LONG.'")
    @Range(min = 0)
    private Long friendsHash;
}
