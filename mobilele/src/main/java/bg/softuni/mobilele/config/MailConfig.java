package bg.softuni.mobilele.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

  @Value("${mail.host}")
  private String host;

  @Value("${mail.port}")
  private Integer port;

  @Value("${mail.username}")
  private String username;

  @Value("${mail.password}")
  private String password;

  @Value("${mail.characterEncoding}")
  private String characterEncoding;

  @Value("${mail.protocol}")
  private String protocol;

  @Bean
  public JavaMailSender javaMailSender() {

    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setHost(host);
    javaMailSender.setPort(port);
    javaMailSender.setUsername(username);
    javaMailSender.setPassword(password);
    javaMailSender.setDefaultEncoding(characterEncoding);
    javaMailSender.setJavaMailProperties(createJavaMailProperties());
    return javaMailSender;
  }

  private Properties createJavaMailProperties() {

    Properties properties = new Properties();
    properties.setProperty("mail.smtp.auth", "true");
    properties.setProperty("mail.transport.protocol", protocol);
    return properties;
  }

}
