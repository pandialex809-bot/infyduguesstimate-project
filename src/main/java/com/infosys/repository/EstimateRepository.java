package com.infosys.repository;

import com.infosys.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    List<Estimate> findByLobLobNameAndYearAndQuarterOrderByIdAsc(String lobName, int year, String quarter);
    Optional<Estimate> findByLobLobNameAndYearAndQuarterAndMonth(String lobName, int year, String quarter, String month);
    void deleteByLobLobName(String lobName);
}
