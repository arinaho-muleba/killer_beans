package com.killerbeans.server.services;

import com.killerbeans.server.models.Status;
import com.killerbeans.server.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusService {
    private  final StatusRepository statusRepository;

    @Autowired
    StatusService(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    Status getStatusById(Long id){
        return statusRepository.findById(id).orElse(null);
    }

    Optional<Status> initialStatus(){
        return statusRepository.findById(1L);
    }
    Long getNumberOfStatuses(){return statusRepository.count();}

}
