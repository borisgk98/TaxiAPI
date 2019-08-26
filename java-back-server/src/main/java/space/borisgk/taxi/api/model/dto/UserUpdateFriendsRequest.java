package space.borisgk.taxi.api.model.dto;

import space.borisgk.taxi.api.model.entity.AuthServiceData;

import java.util.Set;

public class UserUpdateFriendsRequest {
    private String userId;
    private AuthServiceData authServiceData;
    private Set<String> newFriendsSocialIds;
}
