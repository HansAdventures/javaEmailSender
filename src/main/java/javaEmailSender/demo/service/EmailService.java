package javaEmailSender.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    public EmailService(TemplateEngine templateEngine, JavaMailSender mailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = mailSender;
    }

    //Sending email to multiple email adresses
    public void sendMail(String[] to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);//true indicates multipart message
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(body, true);//true indicates body is html
        javaMailSender.send(message);
    }

    //Sending email to one email adress
    public void sendMail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);//true indicates multipart message
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setTo(to);
        helper.setText(body);//true indicates body is html
        javaMailSender.send(message);
    }

    //Sending email with attachement
    public void sendMAilWithAttachment(String to, String subject, String body) throws MessagingException{
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("raj**********@gmail.com");
        helper.setTo(to);
        helper.setSubject("Testing Mail API with Attachment");
        helper.setText("Please find the attached document below.");

        ClassPathResource classPathResource = new ClassPathResource("groww.png");
        helper.addAttachment(classPathResource.getFilename(), classPathResource);

        javaMailSender.send(message);

    }

    //Sending email with html
    public void sendHtmlEmail(String to, String subject, Model model) throws MessagingException, IOException {
        model.addAttribute("datum", "15/02/2003");
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);//true indicates multipart message
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setTo(to);
        try (var inputStream = Objects.requireNonNull(EmailService.class.getResourceAsStream("/templates/htmlEmail.html"))) {
            helper.setText(
                    new String(inputStream.readAllBytes(), StandardCharsets.UTF_8), true
            );
        }
        javaMailSender.send(message);
    }

    //Sending email with thymleaf

    //https://www.thymeleaf.org/apidocs/thymeleaf/3.0.0.RELEASE/org/thymeleaf/TemplateEngine.html
    public void sendMailWithThymleaf(String to, String subject, Model model) throws MessagingException, IOException {
        Context context = new Context();
        context.setVariable("training", model);
        String process = templateEngine.process("htmlEmail.html", context);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject(subject);
        helper.setText(process, true);
        helper.setTo(to);
        helper.setFrom(from);
        javaMailSender.send(mimeMessage);
    }

}
