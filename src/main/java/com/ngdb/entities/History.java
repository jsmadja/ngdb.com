package com.ngdb.entities;

import static com.google.common.io.Files.createParentDirs;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang.RandomStringUtils.randomNumeric;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngdb.entities.article.Game;
import com.ngdb.entities.user.User;

public class History {

	private static final Logger LOG = LoggerFactory.getLogger(History.class);

	private static JAXBContext context;

	static {
		try {
			context = JAXBContext.newInstance(Game.class);
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	public void add(Game game, User author) {
		try {
			File historyFile = createDestinationFile(game.getId(), author.getLogin());
			Marshaller marshaller = createMarshaller();
			marshaller.marshal(game, historyFile);
		} catch (IOException e) {
			LOG.error("Unable to add history for article " + game, e);
		} catch (JAXBException e) {
			LOG.error("Unable to add history for article " + game, e);
		}
	}

	private Marshaller createMarshaller() throws JAXBException, PropertyException {
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		return marshaller;
	}

	private File createDestinationFile(Long articleId, String author) throws IOException {
		String dayDirectory = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String folder = format("/ngdb/history/{0}/articles/{1}", dayDirectory, articleId.toString());
		File to = new File(new File(folder), author + "_" + randomNumeric(5) + ".txt");
		createParentDirs(to);
		return to;
	}

}
