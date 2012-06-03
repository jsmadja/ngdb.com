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
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.user.User;

public class History {

	private static final Logger LOG = LoggerFactory.getLogger(History.class);

	private static JAXBContext gameContext;
	private static JAXBContext hardwareContext;

	static {
		try {
			gameContext = JAXBContext.newInstance(Game.class);
			hardwareContext = JAXBContext.newInstance(Hardware.class);
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	public void add(Game game, User author) {
		try {
			File historyFile = createDestinationFile(game.getId(), author.getLogin());
			Marshaller marshaller = createGameMarshaller();
			marshaller.marshal(game, historyFile);
		} catch (IOException e) {
			LOG.error("Unable to add history for article " + game, e);
		} catch (JAXBException e) {
			LOG.error("Unable to add history for article " + game, e);
		}
	}

	public void add(Hardware hardware, User author) {
		try {
			File historyFile = createDestinationFile(hardware.getId(), author.getLogin());
			Marshaller marshaller = createHardwareMarshaller();
			marshaller.marshal(hardware, historyFile);
		} catch (IOException e) {
			LOG.error("Unable to add history for article " + hardware, e);
		} catch (JAXBException e) {
			LOG.error("Unable to add history for article " + hardware, e);
		}
	}

	private Marshaller createGameMarshaller() throws JAXBException, PropertyException {
		Marshaller marshaller = gameContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		return marshaller;
	}

	private Marshaller createHardwareMarshaller() throws JAXBException, PropertyException {
		Marshaller marshaller = hardwareContext.createMarshaller();
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
