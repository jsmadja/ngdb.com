package com.ngdb.base;

import com.google.common.base.Function;
import com.ngdb.entities.Participation;
import com.ngdb.entities.article.Article;

import javax.annotation.Nullable;
import java.util.Date;

public class ParticipationInGame {

    private Article article;
    private Date date;
    private String role;

    public static Function<Participation, ParticipationInGame> toParticipationInGame = new Function<Participation, ParticipationInGame>() {
        @Nullable
        @Override
        public ParticipationInGame apply(@Nullable Participation participation) {
            return new ParticipationInGame(participation);
        }
    };


    public ParticipationInGame(Participation participation) {
        this.article = participation.getArticle();
        this.date = participation.getArticle().getReleaseDate();
        this.role = participation.getRole();
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}