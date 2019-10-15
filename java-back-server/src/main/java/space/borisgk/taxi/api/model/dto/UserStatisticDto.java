package space.borisgk.taxi.api.model.dto;

import lombok.Data;

@Data
public class UserStatisticDto {
    private Long reportsCount;
    private Long tripsCount;
}
