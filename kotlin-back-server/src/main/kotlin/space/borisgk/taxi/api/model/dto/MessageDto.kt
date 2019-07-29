package space.borisgk.taxi.api.model.dto

import lombok.Builder
import lombok.Getter
import lombok.Setter
import space.borisgk.taxi.api.model.entity.Trip
import space.borisgk.taxi.api.model.entity.User

import javax.persistence.*
import java.util.Date

class MessageDto {
    var id: Int? = null
        set(id) {
            field = this.id
        }

    var userId: Int? = null
        set(userId) {
            field = this.userId
        }
    var tripId: Int? = null
        set(tripId) {
            field = this.tripId
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
