package vanhy.com.imusic.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedManagers {
    private static final String KEY = "imusic";
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
