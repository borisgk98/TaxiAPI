package space.borisgk.taxi.api.model.dto.request;

import lombok.Data;

@Data
public class IsReportedRequest {
    private Integer userId;
    private Integer tripId;
}
