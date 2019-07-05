package space.borisgk.taxi.api.model.entity

import lombok.*
import space.borisgk.taxi.api.model.dto.TripStatus

import javax.persistence.*
import java.util.Date

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Trip {
    constructor(id: Int?) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
        set(id) {
            field = this.id
        }

    var date: Date? = null
        set(date) {
            field = this.date
        }
    var fromLat: Double? = null
        set(fromLat) {
            field = this.fromLat
        }
    var toLat: Double? = null
        set(toLat) {
            field = this.toLat
        }
    var fromLong: Double? = null
        set(fromLong) {
            field = this.fromLong
        }
    var toLong: Double? = null
        set(toLong) {
            field = this.toLong
        }
    var status = TripStatus.ACTIVE
        set(status) {
            field = this.status
        }
    var toString: String? = null
        set(toString) {
            field = this.toString
        }

    @ManyToMany
    var users: List<User>? = null
        set(users) {
            field = this.users
        }
}
