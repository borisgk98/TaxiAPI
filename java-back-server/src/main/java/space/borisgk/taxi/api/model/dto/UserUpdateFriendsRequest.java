package space.borisgk.taxi.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import space.borisgk.taxi.api.model.entity.AuthServiceData;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateFriendsRequest {
    private String userId;
    private AuthServiceData authServiceData;
    private Set<String> newFriendsSocialIds;
}

