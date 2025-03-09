package org.example.mailservice.repository;

import org.example.mailservice.dto.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepository extends JpaRepository<Mail, Long> {

    Mail findByEmail(String email);
}
