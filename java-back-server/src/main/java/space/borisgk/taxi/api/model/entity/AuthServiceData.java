package space.borisgk.taxi.api.model.entity;

import lombok.*;
import space.borisgk.taxi.api.model.AuthService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthServiceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private AuthService authService;
    private String authServiceUserId;
}
