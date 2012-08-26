package com.ngdb.entities;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.hibernate.Session;

import com.ngdb.entities.user.Token;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.MailService;
import com.ngdb.web.services.TokenService;

public class Population {

    @Inject
    private Session session;

    @Inject
    private TokenService tokenService;

    @Inject
    private MailService mailService;

    @Inject
    @Symbol("host.url")
    private String host;

    public User findById(Long id) {
        return (User) session.load(User.class, id);
    }

    public Collection<User> findEverybody() {
        return session.createCriteria(User.class).setCacheable(true).addOrder(asc("login")).list();
    }

    public Long getNumUsers() {
        return (Long) session.createCriteria(User.class).setProjection(count("id")).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }

    public User findByLogin(String login) {
        return (User) session.createCriteria(User.class).add(eq("login", login)).uniqueResult();
    }

    public void addUser(User user) {
        session.persist(user);
        sendSubscriptionConfirmationEmail(user);
    }

    private void sendSubscriptionConfirmationEmail(User user) {
        Token token = tokenService.createToken(user);
        Map<String, String> params = new HashMap<String, String>();
        params.put("recipient", user.getEmail());
        params.put("url", host + "user/validation?token=" + token.getValue() + "&email=" + user.getEmail());
        mailService.sendMail(user, "subscription", params);
    }

    public boolean exists(String login) {
        return findByLogin(login) != null;
    }

    public void resetPasswordOf(User user) {
        sendResetPasswordEmail(user);
    }

    private void sendResetPasswordEmail(User user) {
        Token token = tokenService.createToken(user);
        Map<String, String> params = new HashMap<String, String>();
        params.put("login", user.getLogin());
        params.put("url", host + "user/changePassword?token=" + token.getValue() + "&email=" + user.getEmail() + "&login=" + user.getLogin());
        mailService.sendMail(user, "reset_password", params);
    }

}
