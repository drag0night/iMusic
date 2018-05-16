package vanhy.com.imusic;

import android.content.Context;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;

import vanhy.com.imusic.SQLite.SQLite;

public class HistoryLoader {
    private static HistoryLoader ourInstance = new HistoryLoader();

    private ArrayList<String> historyList;

    public ArrayList<String> getHistory(Context context){
        if(historyList==null){
            SQLite.createHistoryDatabase(context);
            historyList=new ArrayList<String>();
            historyList= SQLite.getHistory(context);
        }

        return historyList;
    }

    public void addHistory(String value,Context context){
        if(historyList==null) return;
        if(!historyList.contains(value)){
            historyList.add(value);
            SQLite.addHistory(context,historyList);
        }
    }

    public void deleteHistory(int index,Context context){
        if(historyList==null) return;
        historyList.remove(index);
        SQLite.addHistory(context,historyList);
    }

    public static HistoryLoader getInstance() {
        return ourInstance;
    }

    private HistoryLoader() {

    }
}
