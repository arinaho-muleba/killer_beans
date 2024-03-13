package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByBeanId(Long beanId);
    @Query("SELECT p FROM Price p WHERE p.beanId = :beanId AND p.endDate IS NULL")
    Optional<Price> findCurrentPriceByBeanId(@Param("beanId") Long beanId);
}
