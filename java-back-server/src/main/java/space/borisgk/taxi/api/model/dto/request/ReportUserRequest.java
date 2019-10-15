package space.borisgk.taxi.api.model.dto.request;

import lombok.Data;

@Data
public class ReportUserRequest {
    private Integer userId;
    private Integer tripId;
    private Integer reporterId;
    private String date;
}
