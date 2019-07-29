package space.borisgk.taxi.api.model.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class AuthServiceData {

    constructor(id: Int?) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
        set(id) {
            field = this.id
        }

    var service: AuthService? = null
        set(service) {
            field = this.service
        }

    var serviceToken: String? = null
        set(serviceToken) {
            field = this.serviceToken
        }
}