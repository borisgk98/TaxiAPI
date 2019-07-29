package space.borisgk.taxi.api.model.entity

import javax.persistence.*

@Entity
class User {

    constructor(id: Int?) {
        this.id = id
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
        set(id) {
            field = this.id
        }
    var firstName: String? = null
        set(firstName) {
            field = this.firstName
        }
    var lastName: String? = null
        set(lastName) {
            field = this.lastName
        }
    var avatarUrl: String? = null
        set(avatarUrl) {
            field = this.avatarUrl
        }
    @ManyToMany
    var authServiceDatas: List<AuthServiceData>? = null
        set (authServiceDatas) {
            field = this.authServiceDatas
        }
}
