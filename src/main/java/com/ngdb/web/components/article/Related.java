package com.ngdb.web.components.article;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Game;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.AssetSource;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;

public class Related {

    @Parameter
    private Game game;

    @Property
    private Game relatedGame;

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    @Property
    @Symbol("image.host.url")
    private String host;

    @Property
    Collection<Game> relatedGames;

    @SetupRender
    void init() {
        String ngh = game.getNgh();
        relatedGames = articleFactory.findAllGamesLightByNgh(ngh);
        relatedGames.remove(game);
        relatedGames = filter(relatedGames, new Predicate<Game>() {
            @Override
            public boolean apply(@Nullable Game input) {
                return input.hasCover();
            }
        });
    }

    public JSONObject getComplexParams(){
        JSONObject json = new JSONObject();
        return json;
    }

}
