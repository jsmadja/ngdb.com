package com.ngdb.web.services.infrastructure;

import com.ngdb.entities.playlist.Uploader;
import com.ngdb.entities.playlist.Video;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class YoutubePlaylistManager extends PlaylistManager {

    public List<Video> videosOfPlaylist(String playlist) throws IOException {
        List<Video> videos = new ArrayList<Video>();
        String content = readContentOfPlaylist(playlist);
        JSONObject jsonObject = new JSONObject(content);
        JSONObject data = (JSONObject) jsonObject.get("data");
        for (Object o : (JSONArray) data.get("items")) {
            JSONObject video = (JSONObject) ((JSONObject) o).get("video");
            String uploaderId = video.getString("uploader");
            String id = video.getString("id");
            String title = video.getString("title");
            Uploader uploader = getUploaderById(uploaderId);
            videos.add(new Video(uploader, id, title));
        }
        return videos;
    }

    private Uploader getUploaderById(String uploaderId) throws IOException {
        String content = readContentOf("http://gdata.youtube.com/feeds/api/users/" + uploaderId + "?v=2&alt=json");
        JSONObject jsonObject = new JSONObject(content);
        JSONObject entry = (JSONObject) jsonObject.get("entry");
        JSONObject yt$username = (JSONObject) entry.get("yt$username");
        String displayName = yt$username.getString("display");
        JSONArray link = (JSONArray) entry.get("link");
        String uploaderUrl = ((JSONObject) link.get(0)).getString("href");
        return new Uploader(uploaderId, displayName, uploaderUrl);
    }

    private String readContentOfPlaylist(String playlist) throws IOException {
        return readContentOf("http://gdata.youtube.com/feeds/api/playlists/"+ playlist + "?v=2&alt=jsonc");
    }

}
