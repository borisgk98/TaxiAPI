package space.borisgk.taxi.api.model.entity

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

import javax.persistence.*
import java.util.Date

class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
        set(id) {
            field = this.id
        }

    @OneToOne
    var user: User? = null
        set(user) {
            field = this.user
        }
    @OneToOne
    var trip: Trip? = null
        set(trip) {
            field = this.trip
        }
    var message: String? = null
        set(message) {
            field = this.message
        }
    var date: Date? = null
        set(date) {
            field = this.date
        }
}
