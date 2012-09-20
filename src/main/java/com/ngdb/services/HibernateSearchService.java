package com.ngdb.services;

import com.ngdb.entities.article.Game;
import org.apache.lucene.search.Query;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.ScrollableResults;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.TermMatchingContext;

import java.text.Normalizer;
import java.util.List;

import static org.hibernate.CacheMode.IGNORE;
import static org.hibernate.FlushMode.MANUAL;
import static org.hibernate.ScrollMode.FORWARD_ONLY;

public class HibernateSearchService {

    @Inject
    private HibernateSessionManager sessionManager;

    private FullTextSession fullTextSession;

    public void launchFullIndexation() {
        index(Game.class);
    }

    public List<Game> searchGames(String terme) {
        terme = normalize(terme);
        terme = terme+"*";

        return search(terme, Game.class,
                "upc",
                "title",
                "details",
                "publisher",
                "reference",
                "originTitle",
                "tags.tags.name",
                "platformShortName",
                "comments.comments.text",
                "notes.notes.name", "notes.notes.text",
                "files.files.name", "files.files.type",
                "reviews.reviews.url", "reviews.reviews.label"
        );
    }

    public String normalize(String string) {
        return removeDiacriticalMarks(string.trim().toLowerCase());
    }

    private String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private void index(Class<?> clazz) {
        fullTextSession = Search.getFullTextSession(sessionManager.getSession());
        fullTextSession.setFlushMode(MANUAL);
        fullTextSession.setCacheMode(IGNORE);
        ScrollableResults results = fullTextSession.createCriteria(clazz).scroll(FORWARD_ONLY);
        while(results.next()) {
            fullTextSession.index(results.get(0));
            fullTextSession.flushToIndexes();
            fullTextSession.clear();
        }
    }

    private List search(String term, Class<?> clazz, String premierChamp, String... champs) {
        FullTextSession fullTextSession = Search.getFullTextSession(sessionManager.getSession());
        QueryBuilder builder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(clazz).get();
        TermMatchingContext context = builder.keyword().wildcard().onField(premierChamp);
        for (String champ : champs) {
            context.andField(champ);
        }
        Query query = context.matching(term).createQuery();
        return fullTextSession.
                createFullTextQuery(query, clazz).
                list();
    }

    public void indexer(Object object) {
        Search.getFullTextSession(sessionManager.getSession()).index(object);
    }
}