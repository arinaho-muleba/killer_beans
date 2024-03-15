package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeanRepository extends JpaRepository<Bean, Long> {
    List<Bean> findByTimeToKillBetween(int minTimeToKill, int maxTimeToKill);
    List<Bean> findByTimeToKill(int ttk);

    List<Bean> findByTimeToKillGreaterThanEqual(int minTimeToKill);
}
