package org.example.mailservice.service;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.mailservice.dto.Mail;
import org.example.mailservice.repository.MailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
@RequiredArgsConstructor
@Service
public class MailService {
    public final MailRepository mailRepository;
    Logger logger = LogManager.getLogger(MailService.class);
    @Value("${email.password}") String password;
    @Value("${email.username}") String username;

    public Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }

    final Session session = Session.getDefaultInstance(getMailProperties(), new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });


    public boolean checkMail(@Email String email) {
       Mail mail = mailRepository.findByEmail(email);
       if (mail == null) {
           return true;
       }
        return false;
    }



public void SendMessageInMail(@Email String email,Long id) throws MessagingException {
    try {
        if (email != null && email != null) {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(email);
            message.setText("Заказ " + id + " был успешно взят");
            Transport.send(message);
            logger.info("Сообщение было успешно отправлено");
            if (checkMail(email) == true) {
                Mail mail = new Mail();
                mail.setEmail(email);
                mailRepository.save(mail);
            }
            else logger.info("Почта уже существует в базе");
            }
        }
     catch (Exception e) {
        logger.error(e);
    }
}

}
