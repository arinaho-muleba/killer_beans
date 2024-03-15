package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
