package com.ngdb.web.services;

import com.ngdb.entities.user.User;
import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class MailService {

	@Inject
	private JavaMailSender mailSender;

	@Inject
	@Symbol("mail.sender")
	private String sender;

	@Inject
	@Symbol("mail.disable")
	private boolean disable;

	@Inject
	@Symbol("mail.test.destinataire")
	private String testDestinataire;

	@Inject
	private EmailBuilderService emailBuilderService;

	private Logger logger = Logger.getLogger(MailService.class);

	public void sendMail(User receiver, String template, Map<String, String> paramsTemplate) {
		String body = emailBuilderService.build(template, paramsTemplate);
		String subject = emailBuilderService.subject(template);
		sendMail(receiver.getEmail(), body, subject);
	}

    public void sendMail(User receiver, String template, String subject, Map<String,String> paramsTemplate) {
        String body = emailBuilderService.build(template, paramsTemplate);
        sendMail(receiver.getEmail(), body, subject);
    }

	public void sendMail(String receiver, String body, String subject) {
        if (disable) {
			logger.info(String.format("Receiver : %s", receiver));
			logger.info(String.format("Subject: %s", subject));
			logger.info(String.format("Body : %s", body));
			return;
		}
		checkNotNull(subject, receiver, body);
		try {
            MimeMessage mimeMessage = createMessage(receiver, body, subject);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			logger.error(e);
		}
	}

	private MimeMessage createMessage(String receiver, String body, String subject) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		if (!disable) {
			helper.setTo(receiver);
		} else {
			helper.setTo(testDestinataire);
			logger.info(String.format("Receiver : %s", receiver));
			logger.info(String.format("Body : %s", body));
		}
        helper.setFrom(sender);
		helper.setText(body);
		helper.setSubject(subject);
		return mimeMessage;
	}

}
