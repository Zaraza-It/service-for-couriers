package org.example.serviceforcouriers.repository;

import org.example.serviceforcouriers.entity.RequestChangeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestChangeStatus, Long> {
}
