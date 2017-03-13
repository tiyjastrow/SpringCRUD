package com.theironyard.services;

import com.theironyard.entities.CrashReport;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CrashReportRepository extends CrudRepository<CrashReport, Integer> {
    @Override
    List<CrashReport> findAll();
}
