package net.rhuanrocha.eventcdiexample.observer;

import net.rhuanrocha.eventcdiexample.event.EmailEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

public class EmailSender {

    @Resource(lookup = "mail/MyMailSession")
    private Session session;

    private Logger logger = LogManager.getLogger(this.getClass());

    public void send(@Observes @net.rhuanrocha.eventcdiexample.qualifier.EmailSender EmailEvent emailEvent) throws NamingException {

        runSend(emailEvent);

        logger.info("Email sent!");
    }

    public void sendAsyn(@ObservesAsync @net.rhuanrocha.eventcdiexample.qualifier.EmailSender EmailEvent emailEvent) throws NamingException {

        runSend(emailEvent);
        logger.info("Email sent Async!");

    }

    private void runSend(EmailEvent emailEvent) throws NamingException {

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(session.getProperty("mail.address"));
            message.setRecipients(Message.RecipientType.TO, emailEvent.getEmailTo());
            message.setSubject("Enviando email com JavaMail");

            message.setText(emailEvent.getMessage());
            /**Método para enviar a mensagem criada*/
            Transport.send(message,
                    session.getProperty("mail.smtp.user"),
                    session.getProperty("mail.smtp.pass"));



        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
