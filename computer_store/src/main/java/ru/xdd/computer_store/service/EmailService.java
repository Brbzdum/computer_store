package ru.xdd.computer_store.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("<p>Здравствуйте!</p>" +
                    "<p>Для активации вашего аккаунта, перейдите по <a href='" + content + "'>этой ссылке</a>.</p>" +
                    "<p>С уважением, Компьютерный магазин Komputer Shop!.</p>", true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email", e);
        }
    }
}
