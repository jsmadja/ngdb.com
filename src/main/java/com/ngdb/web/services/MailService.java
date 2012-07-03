package com.ngdb.web.services;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ngdb.entities.user.User;

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

	private static final String HTML_BEGIN = "<html><body>";
	private static final String HTML_END = "</body></html>";
	private static final boolean HTML_CONTENT = true;

	public void sendMail(User receiver, String template, Map<String, String> paramsTemplate) {
		String body = emailBuilderService.build(template, paramsTemplate);
		String subject = emailBuilderService.subject(template);
		sendMail(receiver.getEmail(), body, subject);
	}

	public void sendMail(String receiver, String body, String subject) {
		if (disable) {
			logger.info(String.format("Receiver : %s", receiver));
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
		if (StringUtils.isBlank(testDestinataire)) {
			helper.setTo(receiver);
		} else {
			helper.setTo(testDestinataire);
			logger.info(String.format("Receiver : %s", receiver));
			logger.info(String.format("Body : %s", body));
		}
		helper.setFrom(sender);
		helper.setText(HTML_BEGIN + body + HTML_END, HTML_CONTENT);
		helper.setSubject(subject);
		return mimeMessage;
	}

}
