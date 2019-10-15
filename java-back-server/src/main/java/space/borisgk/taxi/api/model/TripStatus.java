package space.borisgk.taxi.api.model;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public enum TripStatus {
    ACTIVE, FINISHED, DELETED, COLLECTED;

    public static List<Integer> getNotDeletedIds() {
        return Arrays.stream(TripStatus.values()).filter(x -> x != DELETED).map(s -> s.ordinal()).collect(Collectors.toList());
    }
}
