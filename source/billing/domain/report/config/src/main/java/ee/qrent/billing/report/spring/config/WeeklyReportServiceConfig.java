package ee.qrent.billing.report.spring.config;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.deposit.api.in.query.GetDepositQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.query.GetFirmLinkQuery;
import ee.qrent.billing.report.api.in.query.GetWeeklyReportCalculationQuery;
import ee.qrent.billing.report.api.in.query.GetWeeklyReportQuery;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportPdfUseCase;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportSendByEmailUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationLoadPort;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.core.mapper.*;
import ee.qrent.billing.report.core.service.*;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportToPdfConverter;
import ee.qrent.billing.report.core.service.pdf.WeeklyReportToPdfModelMapper;
import ee.qrent.billing.report.core.validator.WeeklyReportCalculationAddRequestValidator;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class WeeklyReportServiceConfig {

    @Bean
    GetWeeklyReportCalculationQuery getWeeklyReportCalculationQueryService(
            final WeeklyReportCalculationLoadPort loadPort,
            final WeeklyReportCalculationResponseMapper mapper,
            final GetQWeekQuery qWeekQuery) {

        return new WeeklyReportCalculationQueryService(loadPort, mapper, qWeekQuery);
    }

    @Bean
    GetWeeklyReportQuery getWeeklyReportQueryService(
            final WeeklyReportLoadPort loadPort, final WeeklyReportResponseMapper mapper) {

        return new WeeklyReportQueryService(loadPort, mapper);
    }

    @Bean
    WeeklyReportCalculationUseCaseService getWeeklyReportCalculationUseCaseService(
            final WeeklyReportCalculationAddRequestValidator addRequestValidator,
            final WeeklyReportCalculationAddRequestMapper addRequestMapper,
            final WeeklyReportCalculationAddPort addPort,
            final GetObligationQuery obligationQuery,
            final GetQWeekQuery qWeekQuery,
            final GetDriverQuery driverQuery,
            final GetCallSignLinkQuery callSignLinkQuery,
            final GetCarLinkQuery carLinkQuery,
            final GetFirmLinkQuery firmLinkQuery,
            final GetBalanceQuery balanceQuery,
            final GetTransactionQuery getTransactionQuery,
            final GetContractQuery contractQuery,
            final GetDepositQuery depositQuery,
            final WeeklyReportSendByEmailUseCase sendByEmailUseCase) {

        return new WeeklyReportCalculationUseCaseService(
                addRequestValidator,
                addRequestMapper,
                addPort,
                obligationQuery,
                qWeekQuery,
                driverQuery,
                callSignLinkQuery,
                carLinkQuery,
                firmLinkQuery,
                balanceQuery,
                getTransactionQuery,
                contractQuery,
                depositQuery,
                sendByEmailUseCase);
    }

    @Bean
    WeeklyReportToPdfConverter getWeeklyReportToPdfConverter() {

        return new WeeklyReportToPdfConverter();
    }

    @Bean
    WeeklyReportToPdfModelMapper getWeeklyReportToPdfModelMapper(
            final GetQWeekQuery qWeekQuery,
            final GetDriverQuery driverQuery,
            final GetCallSignQuery callSignQuery) {

        return new WeeklyReportToPdfModelMapper(
                qWeekQuery, driverQuery, callSignQuery);
    }

    @Bean
    WeeklyReportSendByEmailUseCase getWeeklyReportSendByEmailUseCase(
            final QueueEntryPushUseCase notificationQueuePushUseCase,
            final WeeklyReportLoadPort weeklyReportLoadPort,
            final WeeklyReportPdfUseCase weeklyReportPdfUseCase,
            final GetDriverQuery driverQuery,
            final GetCallSignLinkQuery callSignLinkQuery,
            final QDateTime qDateTime) {

        return new WeeklyReportSendByEmailService(
                notificationQueuePushUseCase,
                weeklyReportLoadPort,
                weeklyReportPdfUseCase,
                driverQuery,
                callSignLinkQuery,
                qDateTime);
    }

    @Bean
    WeeklyReportPdfUseCase getWeeklyReportPdfUseCase(
            final WeeklyReportLoadPort loadPort,
            final WeeklyReportToPdfModelMapper mapper,
            final WeeklyReportToPdfConverter converter) {

        return new WeeklyReportPdfUseCaseImpl(loadPort, mapper, converter);
    }
}
