package space.borisgk.taxi.api.model

import lombok.Builder
import lombok.Getter
import lombok.Setter
import space.borisgk.taxi.api.model.entity.Trip

import java.util.Date

@Setter
@Getter
@Builder
class TripSearchRequest : SearchRequest<Trip> {
    var time: Date? = null
        set(time) {
            field = this.time
        }
    var deltaTimeMin: Int? = null
        set(deltaTimeMin) {
            field = this.deltaTimeMin
        }
    var xcoord: Double? = null
        set(xcoord) {
            field = this.xcoord
        }
    var ycoord: Double? = null
        set(ycoord) {
            field = this.ycoord
        }
    var searchRadius: Double? = null
        set(searchRadius) {
            field = this.searchRadius
        }
}
