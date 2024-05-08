package com.project.layer.Services.Mail;

import com.project.layer.Persistence.Repository.IEmailRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import javax.management.RuntimeErrorException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.List;

@Service
public class MailService{

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public MailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(String receiver,String subject, String messageCont) throws MessagingException {

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(receiver);
            helper.setSubject(subject);

            Context context = new Context();
            context.setVariable("message", messageCont);
            String contentHTML = templateEngine.process("Registro", context);

            helper.setText(contentHTML, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }

    }

    public void sendMail(String receiver,String subject, List<String> messages) throws MessagingException {

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(receiver);
            helper.setSubject(subject);

            Context context = new Context();
            for (int i = 0; i < messages.size(); i++) {
                context.setVariable("message", messages.get(i));
            }
            String contentHTML = templateEngine.process("Registro", context);

            helper.setText(contentHTML, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }

    }

}

