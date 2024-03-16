package com.killerbeans.server.repositories;

import com.killerbeans.server.models.CustomerPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerPhoneNumberRepository extends JpaRepository<CustomerPhoneNumber, Long> {

}
