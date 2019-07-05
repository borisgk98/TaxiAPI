package space.borisgk.taxi.api.model.dto

import lombok.*

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class UserDto {
    var id: Int? = null
        set(id) {
            field = this.id
        }
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
