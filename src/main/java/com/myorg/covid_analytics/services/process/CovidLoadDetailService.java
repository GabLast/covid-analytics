package com.myorg.covid_analytics.services.process;

import com.myorg.covid_analytics.dto.response.dashboard.DashboardOneDataDetails;
import com.myorg.covid_analytics.dto.response.dashboard.DashboardTwoData;
import com.myorg.covid_analytics.models.process.CovidLoadDetail;
import com.myorg.covid_analytics.models.process.CovidLoadHeader;
import com.myorg.covid_analytics.repositories.process.CovidLoadDetailRepository;
import com.myorg.covid_analytics.services.BaseService;
import com.myorg.covid_analytics.utils.DateUtilities;
import com.myorg.covid_analytics.utils.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CovidLoadDetailService extends BaseService<CovidLoadDetail, Long> {

    private final CovidLoadDetailRepository repository;

    @Override
    protected JpaRepository<CovidLoadDetail, Long> getRepository() {
        return repository;
    }

    @Transactional(readOnly = true)
    public List<CovidLoadDetail> findAllByHeaderAndEnabled(CovidLoadHeader header,
            boolean enabled) {
        return repository.findAllByHeaderAndEnabled(header, enabled);
    }

    @Transactional(readOnly = true)
    public List<CovidLoadDetail> findAllFilter(boolean enabled, String timeZoneId,
            Long headerId, String countryCode, LocalDate dateStart, LocalDate dateEnd,
            Integer limit, Integer offset, Sort sort) {

        return repository.findAllFilter(enabled, headerId, countryCode,
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateStart,
                        true),
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateEnd,
                        false), sort == null ? new OffsetBasedPageRequest(limit, offset)
                                             : new OffsetBasedPageRequest(limit, offset,
                                                     sort));
    }

    @Transactional(readOnly = true)
    public Integer countAllFilter(boolean enabled, String timeZoneId, Long headerId,
            String countryCode, LocalDate dateStart, LocalDate dateEnd) {

        return repository.countAllFilter(enabled, headerId, countryCode,
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateStart,
                        true),
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateEnd,
                        false));
    }

    @Transactional(readOnly = true)
    public List<DashboardOneDataDetails> getDataDashboardOne() {
        return repository.getDataDashboardOne();
    }

    @Transactional(readOnly = true)
    public DashboardTwoData getDataDashboardTwo(String timeZoneId,
            String countryCode, LocalDate dateStart, LocalDate dateEnd) {

        return repository.getDataDashboardTwo(countryCode,
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateStart,
                        true),
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateEnd,
                        false));
    }
}
