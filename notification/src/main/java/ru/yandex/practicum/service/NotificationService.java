package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.NotificationDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    //private final JavaMailSender javaMailSender;

    public void notify(NotificationDto notificationDto) {
        log.info("Sending notification: {}", notificationDto);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationDto.getEmail());
        message.setSubject(notificationDto.getSubject());
        message.setText(notificationDto.getMessage());
        //javaMailSender.send(message);
        log.info("Successfully sent: {}", notificationDto);
    }
}
