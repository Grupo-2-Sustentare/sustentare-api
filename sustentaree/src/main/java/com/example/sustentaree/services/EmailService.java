package com.example.sustentaree.services;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Properties;

@Service
public class EmailService {

  public void sendEmail(List<String> recipients, String subject, String header, String body, String footer, String imagePath) {
    try {
      // Configuração da sessão de e-mail diretamente no método
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");

      Session mailSession = Session.getInstance(props, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication("sustentare.paralelo19@gmail.com", "sriy egwe cbdd xzgi\n");
        }
      });

      // Criar a mensagem
      MimeMessage message = new MimeMessage(mailSession);
      message.setFrom(new InternetAddress("sustentare.paralelo19@gmail.com", "Equipe Sustentare"));

      // Adicionar destinatários
      if (recipients == null || recipients.isEmpty()) {
        throw new IllegalArgumentException("A lista de destinatários não pode estar vazia.");
      }
      for (String recipient : recipients) {
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
      }

      message.setSubject(subject);

      // Criar a estrutura do e-mail
      MimeMultipart multipart = new MimeMultipart();

      // Parte HTML (conteúdo)
      MimeBodyPart htmlPart = new MimeBodyPart();
      String htmlContent = """
                <div style="font-family: Arial, sans-serif; color: #333;">
                    <h2 style="color: #007BFF;">%s</h2>
                    <p>%s</p>
                    <footer style="font-size: 12px; color: #666;">%s</footer>
                </div>
                """.formatted(header, body, footer);
      htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
      multipart.addBodyPart(htmlPart);

      // Parte da imagem embutida (opcional)
      if (imagePath != null && !imagePath.isEmpty()) {
        MimeBodyPart imagePart = new MimeBodyPart();
        imagePart.attachFile(new File(imagePath));
        imagePart.setContentID("<image001>");
        imagePart.setDisposition(MimeBodyPart.INLINE);
        multipart.addBodyPart(imagePart);
      }

      // Adicionar o conteúdo à mensagem
      message.setContent(multipart);

      // Enviar o e-mail
      Transport.send(message);
      System.out.println("E-mail enviado com sucesso para os destinatários: " + String.join(", ", recipients));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Erro ao enviar o e-mail: " + e.getMessage());
    }
  }
}