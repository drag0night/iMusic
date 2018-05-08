package vanhy.com.imusic.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vanhy.com.imusic.model.BaiHat;

import static android.provider.MediaStore.Audio.AudioColumns.TRACK;

public class SharedManagers {
    private static final String KEY = "imusic";
    private static final String FAVORITE = "FAVORITE";
    private static final String SHUFFLE = "SHUFFLE";
    private static final String REPEAT = "REPEAT";
    private SharedPreferences mSharedPreferences;
    private static SharedManagers instance;

    public static SharedManagers getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new SharedManagers(context);
    }

    public SharedManagers(Context context) {
        mSharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public BaiHat getTrack() {
        return new Gson().fromJson(mSharedPreferences.getString(TRACK, null), BaiHat.class);
    }

    public void putTrack(String track) {
        mSharedPreferences.edit().putString(TRACK, track).apply();
    }

    public boolean addTrack(BaiHat track) {
        if (track == null) {
            return false;
        }
        List<BaiHat> tracks = getListTrack();
        if (tracks == null) {
            tracks = new ArrayList<>();
            tracks.add(track);
            SharedManagers.getInstance().putListTrack(new Gson().toJson(tracks));
            return true;
        } else {
            for (BaiHat trackTemp : tracks) {
                if (track.getStreamUrl().equals(trackTemp.getStreamUrl())) {
                    return false;
                }
            }
            tracks.add(track);
            SharedManagers.getInstance().putListTrack(new Gson().toJson(tracks));
        }
        return true;
    }

    public List<BaiHat> getListTrack() {
        String tracks = mSharedPreferences.getString(FAVORITE, null);
        Type listType = new TypeToken<ArrayList<BaiHat>>() {
        }.getType();
        return new Gson().fromJson(tracks, listType);
    }

    public void putListTrack(String tracks) {
        mSharedPreferences.edit().putString(FAVORITE, tracks).apply();
    }

    public void putSuff(boolean shuff) {
        mSharedPreferences.edit().putBoolean(SHUFFLE, shuff).apply();
    }

    public void putRepeat(boolean repeate) {
        mSharedPreferences.edit().putBoolean(REPEAT, repeate).apply();
    }

    public boolean getSuff() {
        return mSharedPreferences.getBoolean(SHUFFLE, false);
    }

    public boolean getRepeat() {
        return mSharedPreferences.getBoolean(REPEAT, false);
    }
}
