package space.borisgk.taxi.api.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import space.borisgk.taxi.api.model.dto.TripStatus
import space.borisgk.taxi.api.model.entity.Trip

import java.util.Date

@Repository
interface TripRepository : JpaRepository<Trip, Int> {
    @Query("select t from Trip t where t.toLat < toLat")
    fun search(date: Date, fromLat: Double?, toLat: Double?, fromLong: Double?, toLong: Double?, deltaTime: Double?): List<Trip>

    fun findByStatus(status: TripStatus): List<Trip>
}
