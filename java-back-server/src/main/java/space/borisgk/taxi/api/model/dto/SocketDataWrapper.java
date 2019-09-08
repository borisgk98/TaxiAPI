package space.borisgk.taxi.api.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocketDataWrapper {
    private String socket;
    private String payload;
}
