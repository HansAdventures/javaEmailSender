package javaEmailSender.demo.controller;

import jakarta.mail.MessagingException;
import javaEmailSender.demo.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
@AllArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private EmailService emailService;


    @RequestMapping("/send")
    public String sendEmailToOne() throws MessagingException {
        String[] emails = new String[] {"ha************net.be", "ha*************nt.be"};
        emailService.sendMail(emails, "test1", "Hallo Hans");
        return "email verstuurd";
    }

    @RequestMapping("/send")
    public String sendEmailToMultiple() throws MessagingException {

        emailService.sendMail("ha************net.be", "test1", "Hallo Hans");
        return "email verstuurd";
    }

    @RequestMapping("/send")
    public String sendEmailWithAttachment() throws MessagingException {

        emailService.sendMAilWithAttachment("ha************net.be", "test1", "Hallo Hans");
        return "email verstuurd";
    }

    @RequestMapping("/send")
    public String sendHtmlEmail(@ModelAttribute Model model) throws MessagingException, IOException {

        emailService.sendHtmlEmail("ha************net.be", "test1", model);
        return "email verstuurd";
    }

    @RequestMapping("/send")
    public String sendHtmlWithThymleaf(@ModelAttribute Model model) throws MessagingException, IOException {

        emailService.sendMailWithThymleaf("ha************net.be", "test1", model);
        return "email verstuurd";
    }

}
