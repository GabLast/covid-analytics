package com.myorg.covid_analytics.services.process;

import com.myorg.covid_analytics.models.process.CovidLoadHeader;
import com.myorg.covid_analytics.repositories.process.CovidLoadHeaderRepository;
import com.myorg.covid_analytics.repositories.security.PermitRepository;
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
public class CovidLoadHeaderService extends BaseService<CovidLoadHeader, Long> {

    private final CovidLoadHeaderRepository repository;

    @Override
    protected JpaRepository<CovidLoadHeader, Long> getRepository() {
        return repository;
    }

    @Transactional(readOnly = true)
    public List<CovidLoadHeader> findAllFilter(
            boolean enabled, String timeZoneId,
            Long userId, String description,
            LocalDate dateStart, LocalDate dateEnd,
            Integer limit, Integer offset, Sort sort) {

        return repository.findAllFilter(
                enabled,
                userId,
                description,
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateStart, true),
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateEnd, false),
                sort == null ? new OffsetBasedPageRequest(limit, offset) : new OffsetBasedPageRequest(limit, offset, sort)
        );
    }

    @Transactional(readOnly = true)
    public Long countAllFilter(
            boolean enabled, String timeZoneId,
            Long userId, String description,
            LocalDate dateStart, LocalDate dateEnd) {

        return repository.countAllFilter(
                enabled,
                userId,
                description,
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateStart, true),
                DateUtilities.getLocalDateAtTimeZoneAtStartOrEnd(timeZoneId, dateEnd, false)
        );
    }
}
