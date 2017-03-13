package com.theironyard.services;

import com.theironyard.entities.CrashReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrashReportRepository extends CrudRepository<CrashReport, Integer> {
    @Override
    List<CrashReport> findAll();

    List<CrashReport> findAllByOrderByTime();
    List<CrashReport> findByUser_Username(String username);
    CrashReport findFirstById(Integer id);

    @Query(value = "SELECT u.username FROM crash_reports c JOIN users u ON c.user_id = u.id WHERE c.id = ?1", nativeQuery = true)
    String findUser_UsernameById(Integer id);
}
