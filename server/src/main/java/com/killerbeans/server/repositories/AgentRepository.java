package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Agent;
import com.killerbeans.server.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByAlias(String alias);
}
