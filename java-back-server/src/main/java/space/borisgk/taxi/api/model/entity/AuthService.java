package space.borisgk.taxi.api.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "auth_service")
@Getter
@AllArgsConstructor
public class AuthService {
    // TODO инициальзация при старте приложения через рефлексию
    public final static AuthService VK = new AuthService(1, "VK"), FB = new AuthService(2, "FB");

    private AuthService(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public static AuthService fromName(String name) {
        switch (name) {
            case "VK":
                return VK;
            case "FB":
                return FB;
        }
        return null;
    }
}
