package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeanRepository extends JpaRepository<Bean, Long> {
}
