package com.ngdb.web.pages;

import com.ngdb.web.services.MailService;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionReport implements ExceptionReporter {

    @Property
    private Throwable exception;

    @Inject
    private Environment environment;

    @Inject
    private MailService mailService;

    @Inject
    private Request request;

    private static final String NEW_LINE = System.getProperty("line.separator");

    void setupRender() {
        if (environment.peek(FormSupport.class) != null) {
            environment.pop(FormSupport.class);
        }
    }

    public void reportException(Throwable exception) {
        this.exception = exception;

        String page = recupererDernierePageVisitee();

        String body = page + NEW_LINE + NEW_LINE + genererStackTrace(exception);
        mailService.sendMail("julien.smadja@gmail.com", body, "Une erreur est survenue sur neogeodb");
    }

    String enjoliverStacktrace(String stacktrace) {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(stacktrace);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Pattern p = Pattern.compile("Caused by: (.*)");
            Matcher m = p.matcher(line);
            if (m.matches()) {
                String group = m.group();
                line = "<span style='color:red;font-weight:bold;'>" + group + "</span>";
            } else {
                line = "<span style='margin-left:5px'>" + line + "</span>";
            }
            sb.append(line).append(NEW_LINE);
        }
        return sb.toString().trim();
    }

    private String genererStackTrace(Throwable exception) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        exception.printStackTrace(new PrintStream(os));
        return new String(os.toByteArray());
    }


    private String recupererDernierePageVisitee() {
        Session session = request.getSession(false);
        String page = "";
        if (session != null) {
            Object attribute = session.getAttribute("derniere-page-visitee");
            if (attribute != null) {
                page = attribute.toString();
            }
        }
        return page;
    }
}
