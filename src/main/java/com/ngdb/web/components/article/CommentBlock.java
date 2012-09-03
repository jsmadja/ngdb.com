package com.ngdb.web.components.article;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class CommentBlock {

    @Property
    @Parameter
    private Article article;

    @Property
    private Comment comment;

    @Property
    private String commentText;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private GameFactory gameFactory;

    @CommitAfter
    public void onSuccess() {
        if (isNotBlank(commentText)) {
            currentUser.addCommentOn(article, commentText);
        }
    }

    public boolean getHasNoComments() {
        return getComments().isEmpty();
    }

    public User getUser() {
        return currentUser.getUser();
    }

    public Collection<Comment> getComments() {
        Set<Comment> comments;
        if (article.isGame()) {
            Game game = (Game) article;
            comments = new TreeSet<Comment>(game.getComments().all());
            List<Game> relatedGames = gameFactory.findAllByNgh(game.getNgh());
            for (Game relatedGame : relatedGames) {
                comments.addAll(relatedGame.getComments().all());
            }
            return comments;
        } else {
            comments = new TreeSet<Comment>(article.getComments().all());
        }
        return comments;
    }

}
