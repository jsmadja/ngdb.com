package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.playlist.Uploader;
import com.ngdb.entities.playlist.Video;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DailymotionPlaylistManager extends PlaylistManager {

    public List<Video> videosOfPlaylist(String playlist) throws
            IOException {
        List<Video> videos = new ArrayList<Video>();
        String content = readDailymotionContentOfPlaylist(playlist);
        JSONObject jsonObject = new JSONObject(content);
        for (Object o : jsonObject.getJSONArray("list")) {
            JSONObject object = (JSONObject) o;
            String id = object.getString("id");
            String uploaderId = object.getString("owner");
            String title = object.getString("title");
            Uploader uploader = getUploaderById(uploaderId);
            videos.add(new Video(uploader, id, title));
        }
        return videos;
    }

    private Uploader getUploaderById(String uploaderId) throws IOException {
        String content = readContentOf("https://api.dailymotion.com/user/" + uploaderId+"?fields=url,screenname");
        JSONObject jsonObject = new JSONObject(content);
        String displayName = jsonObject.getString("screenname");
        String url = jsonObject.getString("url");
        return new Uploader(uploaderId, displayName, url);
    }

    private String readDailymotionContentOfPlaylist(String dailymotionPlaylistId) throws IOException {
        return readContentOf("https://api.dailymotion.com/playlist/"+dailymotionPlaylistId+"/videos");
    }

}
