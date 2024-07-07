package ee.qrental.constant.api.in.request;


import java.time.LocalDate;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QWeekAddRequest extends AbstractAddRequest {
    private LocalDate weekDate;
}
