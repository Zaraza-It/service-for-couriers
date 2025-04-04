package org.example.serviceforcouriers.repository;

import org.example.serviceforcouriers.entity.RequestChangeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<RequestChangeStatus, Long> {
}
