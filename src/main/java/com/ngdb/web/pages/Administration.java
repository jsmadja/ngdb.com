package com.ngdb.web.pages;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.ngdb.TagSplitter;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.*;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.FileService;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.google.common.collect.Collections2.filter;

@RequiresUser
public class Administration {

    @Property
    private Long articleId;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private Session session;

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private FileService fileService;

    @Inject
    private PictureService pictureService;

    private static final Logger LOG = LoggerFactory.getLogger(Administration.class);

    @Property
    private List<Game> games;

    @Property
    private Game game;

    @Property
    private Tag tag;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @InjectComponent
    private Zone tagZone;

    @Property
    private String search;

    @Inject
    private Registry registry;

    private Collection<String> suggestions = new TreeSet<String>();

    void onActivate() {
        suggestions.addAll(registry.findAllTags());
    }

    @SetupRender
    public void init() {
        this.games = articleFactory.retrieveGamesToTag();
    }

    public Set<Tag> getTags() {
        return game.getTags().all();
    }

    @CommitAfter
    @OnEvent(value = EventConstants.SUCCESS, component = "deleteAnArticleForm")
    public Object onSuccessFromDeleteAnArticleForm() {
        if (currentUser.isContributor()) {
            deleteArticle();
        }
        return this;
    }

    private void deleteArticle() {
        Article article = articleFactory.findById(articleId);
        pictureService.invalidateCoverOf(article);
        ArticlePictures pictures = article.getPictures();
        for (Picture picture : pictures) {
            pictureService.delete(picture);
        }
        Files files = article.getFiles();
        for (File file : files) {
            fileService.delete(file, article);
        }
        session.delete(article);
        session.flush();
        LOG.info(currentUser.getUsername() + " has just deleted " + articleId);
    }

    @CommitAfter
    @OnEvent(value = EventConstants.SUCCESS, component = "tagForm")
    public void onSuccessFromTagForm(Game game) {
        this.game = game;
        game.updateModificationDate();
        Set<String> tags = new TagSplitter().extractTags(search);
        for (String tag : tags) {
            currentUser.addTagOn(game, tag);
        }
        this.search = null;
        ajaxResponseRenderer.addRender(createZoneIdFrom(game), tagZone.getBody());
    }

    public String getJoinedTags() {
        return Joiner.on(", ").join(game.getTags());
    }

    public String getZoneId() {
        return createZoneIdFrom(game);
    }

    private String createZoneIdFrom(Game game) {
        return "tagZone_" + game.getId().toString();
    }

    @OnEvent("provideCompletions")
    List<String> autoCompete(String partial) {
        if (suggestions.isEmpty()) {
            onActivate();
        }
        final String filterLowerCase = partial.toLowerCase();
        suggestions = filter(suggestions, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.toLowerCase().startsWith(filterLowerCase);
            }
        });
        return new ArrayList<String>(suggestions);
    }

}
