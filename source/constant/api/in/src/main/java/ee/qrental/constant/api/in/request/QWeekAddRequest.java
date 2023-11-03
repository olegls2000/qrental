package ee.qrental.constant.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QWeekAddRequest extends AbstractAddRequest {
    private LocalDate weekDate;
}
