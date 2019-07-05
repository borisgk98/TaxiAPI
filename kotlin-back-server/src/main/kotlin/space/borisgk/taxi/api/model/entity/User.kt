package space.borisgk.taxi.api.model.entity

import lombok.*

import javax.persistence.*

@Entity
@Setter
@Getter
@Table(name = "taxi_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(unique = true)
    var vkId: Int? = null
        set(vkId) {
            field = this.vkId
        }
    var firstName: String? = null
        set(firstName) {
            field = this.firstName
        }
    var lastName: String? = null
        set(lastName) {
            field = this.lastName
        }
    var photoUrl: String? = null
        set(photoUrl) {
            field = this.photoUrl
        }
}
