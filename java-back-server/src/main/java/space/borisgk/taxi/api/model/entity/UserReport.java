package space.borisgk.taxi.api.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "user_report")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    private User reporter;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    private Trip trip;

    @Column(name = "date")
    private Date date;
}
