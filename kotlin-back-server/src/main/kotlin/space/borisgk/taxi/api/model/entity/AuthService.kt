package space.borisgk.taxi.api.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class AuthService {

    constructor(id: Int?) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
        set(id) {
            field = this.id
        }
    var name: String? = null
        set (name) {
            field = this.name
        }
}