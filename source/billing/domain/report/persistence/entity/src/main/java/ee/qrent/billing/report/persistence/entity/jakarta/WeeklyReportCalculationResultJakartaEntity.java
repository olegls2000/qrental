package ee.qrent.billing.report.persistence.entity.jakarta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "weekly_report_calculation_result")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeeklyReportCalculationResultJakartaEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "calculation_id")
    private WeeklyReportCalculationJakartaEntity calculation;

    @ManyToOne
    @JoinColumn(name = "weekly_report_id")
    private WeeklyReportJakartaEntity invoice;
}
