package com.killerbeans.server.services;

import com.killerbeans.server.models.Agent;
import com.killerbeans.server.repositories.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgentService {
    private final AgentRepository agentRepository;

    @Autowired
    public AgentService(AgentRepository agentRepository){
        this.agentRepository = agentRepository;
    }

    public Optional<Agent> getAgentById(Long agentId){
        return agentRepository.findById(agentId);
    }

    public Optional<Agent> getAgentByAlias(String alias){
        return agentRepository.findByAlias(alias);
    }

}
