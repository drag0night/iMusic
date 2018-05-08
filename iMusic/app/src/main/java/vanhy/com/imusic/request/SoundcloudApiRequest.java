package vanhy.com.imusic.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import vanhy.com.imusic.Config;
import vanhy.com.imusic.model.Album;
import vanhy.com.imusic.model.BaiHat;


public class SoundcloudApiRequest {

    public interface SoundcloudInterface{
        void onSuccess(Object songs);
        //void onAlbumSuccess(ArrayList<Album> songs);
        void onError(String message);
    }

    private RequestQueue queue;
    private static final String URL = "http://api.soundcloud.com/tracks?filter=public&limit=100&client_id="+ Config.CLIENT_ID;
    private static final String URLPL = "http://api.soundcloud.com/playlists?client_id=a7Ucuq0KY8Ksn8WzBG6wj4x6pcId6BpU&limit=10";
    private static final String TAG = "APP";

    public SoundcloudApiRequest(RequestQueue queue) {
        this.queue = queue;
    }

    public void getSongList(String query, final SoundcloudInterface callback){

        String url = URL;
        if(query.length() > 0){
            try {
                query = URLEncoder.encode(query, "UTF-8");
                url = URL + "&q=" + query;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "getSongList: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response);

                ArrayList<BaiHat> songs = new ArrayList<>();
                if(response.length() > 0){
                    for (int i = 0; i < response.length() ; i++) {
                        try {
                            JSONObject songObject = response.getJSONObject(i);
                            long id = songObject.getLong("id");
                            String title = songObject.getString("title");
                            String artworkUrl = songObject.getString("artwork_url");
                            String streamUrl = songObject.getString("stream_url");
                            long duration = songObject.getLong("duration");
                            int playbackCount = songObject.has("playback_count") ? songObject.getInt("playback_count") : 0;
                            JSONObject user = songObject.getJSONObject("user");
                            String artist = user.getString("username");

                            BaiHat song = new BaiHat(id, title, artist, artworkUrl, duration, streamUrl, playbackCount);
                            songs.add(song);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            callback.onError("Không kết nối được server");
                            e.printStackTrace();
                        }
                    }

                    callback.onSuccess(songs);

                }else{
                    callback.onError("Không tìm thấy bài hát nào");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + error.getMessage());
                callback.onError("Không kết nối được server");
            }
        });

        queue.add(request);

    }

    public void getAlbumList(String query, final SoundcloudInterface callback){

        String url = URLPL;
        if(query.length() > 0){
            try {
                query = URLEncoder.encode(query, "UTF-8");
                url = URLPL + "&q=" + query;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "getAlbumList: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response);

                ArrayList<Album> songs = new ArrayList<>();
                if(response.length() > 0){
                    for (int i = 0; i < response.length() ; i++) {
                        try {
                            JSONObject songObject = response.getJSONObject(i);
                            long id = songObject.getLong("id");
                            String title = songObject.getString("title");
                            String artworkUrl = songObject.getString("artwork_url");

                            //JSONArray tracks=songObject.getJSONArray("tracks");
                            long track_count = songObject.getLong("track_count");
                            JSONObject user = songObject.getJSONObject("user");
                            String artist = user.getString("username");

                            Album song = new Album(id, title, artist, artworkUrl,track_count);
                            songs.add(song);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: " + e.getMessage());
                            callback.onError("Không kết nối được server");
                            e.printStackTrace();
                        }
                    }

                    callback.onSuccess(songs);

                }else{
                    callback.onError("Không tìm thấy playlist nào");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + error.getMessage());
                callback.onError("Không kết nối được server");
            }
        });

        queue.add(request);

    }

    public void getAlbum(String query, final SoundcloudInterface callback){

        String url = "http://api.soundcloud.com/playlists/"+query+"?client_id="+ Config.CLIENT_ID;


        Log.d(TAG, "getAlbum: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONArray arr=response.getJSONArray("tracks");
                    ArrayList<BaiHat> songs = new ArrayList<>();
                    if(arr.length() > 0){
                        for (int i = 0; i < arr.length() ; i++) {
                            JSONObject songObject = arr.getJSONObject(i);
                            long id = songObject.getLong("id");
                            String title = songObject.getString("title");
                            String artworkUrl = songObject.getString("artwork_url");
                            String streamUrl = songObject.getString("stream_url");
                            long duration = songObject.getLong("duration");
                            int playbackCount = songObject.has("playback_count") ? songObject.getInt("playback_count") : 0;
                            JSONObject user = songObject.getJSONObject("user");
                            String artist = user.getString("username");
                            BaiHat song = new BaiHat(id, title, artist, artworkUrl, duration, streamUrl, playbackCount);
                            songs.add(song);
                        }

                        callback.onSuccess(songs);

                    }else{
                        callback.onError("Không tìm thấy playlist nào");
                    }
                } catch (JSONException e) {
                Log.d(TAG, "onResponse: " + e.getMessage());
                callback.onError("Không kết nối được server");
                e.printStackTrace();
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + error.getMessage());
                callback.onError("Không kết nối được server");
            }
        });

        queue.add(request);

    }

    public void getTopMusic(String query, final SoundcloudInterface callback){

        String url = "https://api-v2.soundcloud.com/charts?kind=top&genre=soundcloud%3Agenres%3A"+query+"&client_id=a7Ucuq0KY8Ksn8WzBG6wj4x6pcId6BpU&limit=6&offset=0";


        Log.d(TAG, "getTopMusic: " + url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONArray arr=response.getJSONArray("collection");
                    ArrayList<BaiHat> songs = new ArrayList<>();
                    if(arr.length() > 0){
                        for (int i = 0; i < arr.length() ; i++) {
                            JSONObject item = arr.getJSONObject(i);
                            JSONObject songObject = item.getJSONObject("track");
                            long id = songObject.getLong("id");
                            String title = songObject.getString("title");
                            String artworkUrl = songObject.getString("artwork_url");
                            String streamUrl = songObject.getString("uri")+"/stream";
                            Log.d(TAG, "url: " + streamUrl);
                            long duration = songObject.getLong("duration");
                            int playbackCount = songObject.has("playback_count") ? songObject.getInt("playback_count") : 0;
                            JSONObject user = songObject.getJSONObject("user");
                            String artist = user.getString("username");
                            BaiHat song = new BaiHat(id, title, artist, artworkUrl, duration, streamUrl, playbackCount);
                            songs.add(song);
                        }

                        callback.onSuccess(songs);

                    }else{
                        callback.onError("Không tìm thấy playlist nào");
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    callback.onError("Không kết nối được server");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: " + error.getMessage());
                callback.onError("Không kết nối được server");
            }
        });

        queue.add(request);


    }

}
