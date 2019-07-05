package space.borisgk.taxi.api.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import space.borisgk.taxi.api.model.entity.Message

@Repository
interface MessageRepository : JpaRepository<Message, Int> {

    @Query("select m from Message m where m.trip.id = tripId order by m.date")
    fun getAllByTripId(tripId: Int?): List<Message>
}
