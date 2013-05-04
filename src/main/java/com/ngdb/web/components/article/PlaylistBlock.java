package com.ngdb.web.components.article;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Note;
import com.ngdb.web.services.infrastructure.*;
import com.ngdb.entities.playlist.Video;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistBlock {

    @Property
    @Parameter
    private Article article;

    @Property
    private Note note;

    private static final Logger LOG = LoggerFactory.getLogger(PlaylistBlock.class);

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private YoutubePlaylistManager youtube;

    @Inject
    private DailymotionPlaylistManager dailymotion;

    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<Note>();
        try {
            insertYoutubePlaylistContentIn(notes);
        } catch (IOException e) {
            LOG.error("Impossible d'obtenir les videos youtube", e);
        }
        try {
            insertDailymotionPlaylistContentIn(notes);
        } catch (IOException e) {
            LOG.error("Impossible d'obtenir les videos dailymotion", e);
        }
        return notes;
    }


    private void insertYoutubePlaylistContentIn(List<Note> notes) throws IOException {
        String playlistUrl = getYoutubePlaylist();
        if (playlistUrl != null) {
            String playlistId = playlistUrl.split("=")[1];
            List<Video> videos = youtube.videosOfPlaylist(playlistId);
            for (Video video : videos) {
                String label = "<a href='http://www.youtube.com/watch?v=" + video.getId() + "'>" + video.getTitle() + "</a>";
                String value = "<a href='" + video.getUploaderUrl() + "'>" + video.getUploaderName() + "</a>";
                notes.add(new Note(label, value, article));
            }
        }
    }

    private void insertDailymotionPlaylistContentIn(List<Note> notes) throws IOException {
        String playlistId = getDailymotionPlaylist();
        if (playlistId != null) {
            List<Video> videos = dailymotion.videosOfPlaylist(playlistId);
            for (Video video : videos) {
                String label = "<a href='http://www.dailymotion.com/video/" + video.getId() + "'>" + video.getTitle() + "</a>";
                String value = "<a href='" + video.getUploaderUrl() + "'>" + video.getUploaderName()+ "</a>";
                notes.add(new Note(label, value, article));
            }
        }
    }

    public String getCssClass() {
        if (getNotes().isEmpty()) {
            return "";
        }
        return "table table-striped table-bordered table-condensed";
    }

    public String getYoutubePlaylist() {
        if (article.isGame()) {
            return articleFactory.findYoutubePlaylistOf((Game) article);
        }
        return null;
    }

    private String getDailymotionPlaylist() {
        if (article.isGame()) {
            return articleFactory.findDailymotionPlaylistOf((Game) article);
        }
        return null;
    }

}
