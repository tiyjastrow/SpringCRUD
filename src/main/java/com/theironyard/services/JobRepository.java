package com.theironyard.services;

import com.theironyard.entities.Job;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by joshuakeough on 10/11/16.
 */
public interface JobRepository extends CrudRepository<Job, Integer> {
    List<Job> findBySalaryGreaterThan(Integer salary);

    List<Job> findByPosition(String position);

    List<Job> findByYearsOfExperienceNeededLessThan(Integer yearsOfExperienceNeeded);

    List<Job> findByCityLocated(String cityLocated);

    List<Job> findByUser(User byUser);
}
