package ru.xdd.computer_store.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    /**
     * Отправка email для активации аккаунта.
     *
     * @param recipientEmail Email получателя
     * @param activationLink Ссылка для активации
     */
    public void sendActivationEmail(String recipientEmail, String activationLink) {
        String subject = "Активация аккаунта";
        String message = "<p>Здравствуйте!</p>" +
                "<p>Для активации вашего аккаунта, перейдите по <a href='" + activationLink + "'>этой ссылке</a>.</p>" +
                "<p>С уважением, команда <strong>Компьютерный магазин Komputer Shop</strong>.</p>";

        sendEmail(recipientEmail, subject, message);
    }

    /**
     * Отправка email с кастомным содержимым.
     *
     * @param to      Email получателя
     * @param subject Тема письма
     * @param content HTML-содержимое письма
     */
    public void sendEmail(String to, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // Поддержка HTML

            mailSender.send(mimeMessage);
            log.info("Email отправлен на адрес: {}", to);
        } catch (MessagingException e) {
            log.error("Ошибка при отправке email на адрес: {}", to, e);
            throw new IllegalStateException("Ошибка отправки email", e);
        }
    }
}
