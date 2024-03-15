package com.killerbeans.server.repositories;

import com.killerbeans.server.models.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
