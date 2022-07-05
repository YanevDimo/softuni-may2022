package bg.softuni.mobilele.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;

  public EmailService(JavaMailSender javaMailSender,
                      TemplateEngine templateEngine) {
    this.javaMailSender = javaMailSender;
    this.templateEngine = templateEngine;
  }

  public void sendRegistrationEmail(String registrationEmail,
                                    String userName) {

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
    try {
      mimeMessageHelper.setFrom("mobilele@mobilele.com");
      mimeMessageHelper.setTo(registrationEmail);
      mimeMessageHelper.setSubject("Welcome to Mobilele!");
      mimeMessageHelper.setText(content(userName), true);

      javaMailSender.send(mimeMessageHelper.getMimeMessage());
    } catch (MessagingException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  static public Context getContext() {
    return new Context();
  }

  private String content(String name) {
    Context ctx = getContext();
    ctx.setVariable("name", name);
    return templateEngine.process("mail/welcome", ctx);
  }

}
