package javaEmailSender.demo.controller;

import jakarta.mail.MessagingException;
import javaEmailSender.demo.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private EmailService emailService;


    @RequestMapping("/send")
    public String sendEmail() throws MessagingException {
        String[] emails = new String[] {"hans.vercruysse@telenet.be", "hans.vercruysse@ugent.be"};
        emailService.sendMail(emails, "test1", "Hallo Hans");
        return "email verstuurd";
    }

}
