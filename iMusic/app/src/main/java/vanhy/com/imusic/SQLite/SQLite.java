package vanhy.com.imusic.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import vanhy.com.imusic.model.BaiHat;
import vanhy.com.imusic.model.Playlist;

import static android.content.Context.MODE_PRIVATE;

public class SQLite {

    private static final String db_name = "imusic.db";

    public static boolean checkDbExist(Context context) {
        SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
        try {
            Cursor cs = db.rawQuery("SELECT * FROM playlist", null);
            if (cs.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        } finally {
            db.close();
        }
        return false;
    }

    public static void createDatabase(Context context) {
        String sqltext = "DROP TABLE IF EXISTS song;\n" +
                "DROP TABLE IF EXISTS playlist;\n"+
                "DROP TABLE IF EXISTS favorite;\n"+
                "CREATE TABLE song(id integer PRIMARY KEY AUTOINCREMENT, title text, artist text, artworkUrl text, duration long, streamUri text, playbackCount integer, id_playlist integer);\n" +
                "CREATE TABLE playlist(id integer PRIMARY KEY, title text);\n" +
                "CREATE TABLE favorite(id integer PRIMARY KEY AUTOINCREMENT, id_song integer, title text, artist text, artworkUrl text, duration long, streamUri text, playbackCount integer);";
        SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
        for (String sql : sqltext.split("\n"))
            db.execSQL(sql);
        db.close();
    }

    public static void createPlaylist(Context context, String name, ArrayList<BaiHat> listBh) {
        String sqltext = "INSERT INTO playlist(id,title) VALUES("+(getPlaylistCount(context)+1)+",'"+name+"');\n";
        for (int i = 0; i < listBh.size(); i++) {
            String temp = "'"+listBh.get(i).getTitle()+"','"+listBh.get(i).getArtist()+"','"+listBh.get(i).getArtworkUrl()+"',"+listBh.get(i).getDuration()+",'"+listBh.get(i).getStreamUrl()+"',"+listBh.get(i).getPlaybackCount()+","+(getPlaylistCount(context)+1);
            sqltext+="INSERT INTO song(title,artist,artworkUrl,duration,streamUri,playbackCount,id_playlist) VALUES("+temp+");\n";
        }
        SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
        for (String sql : sqltext.split("\n"))
            db.execSQL(sql);
        db.close();
    }

    public static boolean addSongToPlaylist(Context context, int id_playlist, BaiHat bh) {
        try {
            if (!checkSongExist(context,id_playlist,bh)) {
                SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
                String temp = "'"+bh.getTitle()+"','"+bh.getArtist()+"','"+bh.getArtworkUrl()+"',"+bh.getDuration()+",'"+bh.getStreamUrl()+"',"+bh.getPlaybackCount()+","+id_playlist;
                String sql = "INSERT INTO song(title,artist,artworkUrl,duration,streamUri,playbackCount,id_playlist) VALUES("+temp+");";
                db.execSQL(sql);
                db.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
            return false;
        }
    }

    public static int getPlaylistCount(Context context) {
        int result = 0;
        try {
            SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE,null);
            Cursor cs = db.rawQuery("SELECT * FROM playlist", null);
            while (cs.moveToNext()) {
                result = cs.getInt(0);
            }
            db.close();
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
        }
        return result;
    }

    public static ArrayList<Playlist> getAllPlaylist(Context context) {
        ArrayList<Playlist> result = new ArrayList<Playlist>();
        try {
            boolean[] unused = new boolean[1000];
            SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE,null);
            Cursor cs_pl = db.rawQuery("SELECT * FROM playlist", null);
            while (cs_pl.moveToNext()) {
                int id_pl = cs_pl.getInt(0);
                String playlist_title = cs_pl.getString(1);
                Cursor cs = db.rawQuery("SELECT * FROM song", null);
                int kt = 0;
                while (cs.moveToNext()) {
                    int song_id = cs.getInt(0);
                    String song_title = cs.getString(1);
                    String artist = cs.getString(2);
                    String artworkUrl = cs.getString(3);
                    int duration = cs.getInt(4);
                    String streamUri = cs.getString(5);
                    int playbackCount = cs.getInt(6);
                    int playlist_id = cs.getInt(7);
                    if (playlist_id == id_pl) {
                        kt = 1;
                        if (!unused[playlist_id]) {
                            unused[playlist_id] = true;
                            BaiHat s = new BaiHat(song_id,song_title,artist,artworkUrl,duration,streamUri,playbackCount);
                            ArrayList<BaiHat> listBh = new ArrayList<BaiHat>();
                            listBh.add(s);
                            Playlist pl = new Playlist(playlist_id, playlist_title, listBh);
                            result.add(pl);
                        } else {
                            //if (playlist_id == result.size()) {
                                ArrayList<BaiHat> listBh = result.get(result.size()-1).getListBh();
                                BaiHat s = new BaiHat(song_id,song_title,artist,artworkUrl,duration,streamUri,playbackCount);
                                listBh.add(s);
                                Playlist pl = new Playlist(playlist_id, playlist_title, listBh);
                                result.set(result.size()-1, pl);
                            //}
                        }
                    }
                }
                if (kt  == 0) {
                    Playlist pl = new Playlist(id_pl, playlist_title, new ArrayList<BaiHat>());
                    result.add(pl);
                }
            }
//            while (cs.moveToNext()) {
//                int playlist_id = cs.getInt(0);
//                String playlist_title = cs.getString(1);
//                int song_id = cs.getInt(2);
//                String song_title = cs.getString(3);
//                String artist = cs.getString(4);
//                String artworkUrl = cs.getString(5);
//                int duration = cs.getInt(6);
//                String streamUri = cs.getString(7);
//                int playbackCount = cs.getInt(8);
//                if (result.size() < playlist_id) {
//                    BaiHat s = new BaiHat(song_id,song_title,artist,artworkUrl,duration,streamUri,playbackCount);
//                    ArrayList<BaiHat> listBh = new ArrayList<BaiHat>();
//                    listBh.add(s);
//                    Playlist pl = new Playlist(playlist_id, playlist_title, listBh);
//                    result.add(pl);
//                } else {
//                    if (playlist_id == result.size()) {
//                        ArrayList<BaiHat> listBh = result.get(playlist_id-1).getListBh();
//                        BaiHat s = new BaiHat(song_id,song_title,artist,artworkUrl,duration,streamUri,playbackCount);
//                        listBh.add(s);
//                        Playlist pl = new Playlist(playlist_id, playlist_title, listBh);
//                        result.set(playlist_id-1, pl);
//                    }
//                }
//            }
            db.close();
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
        }
        return result;
    }

    public static boolean checkSongExist(Context context, int id_playlist, BaiHat s) {
        try {
            SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE,null);
            Cursor cs = db.rawQuery("SELECT * FROM song WHERE streamUri = '"+s.getStreamUrl()+"' AND id_playlist = "+id_playlist, null);
            if (cs.moveToNext()) {
                db.close();
                return true;
            }
            db.close();
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
            return true;
        }
        return false;
    }

    public static boolean checkPlaylistNameExist(Context context, String name) {
        try {
            SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE,null);
            Cursor cs = db.rawQuery("SELECT * FROM playlist WHERE title = '"+name+"'", null);
            if (cs.moveToNext()) {
                db.close();
                return true;
            }
            db.close();
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
            return true;
        }
        return false;
    }

    public static boolean deletePlaylist(Context context, Playlist pl) {
        SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE,null);
        try {
            String sqltext = "DELETE FROM playlist WHERE id = "+pl.getId()+";\n";
            ArrayList<BaiHat> listS = pl.getListBh();
            for (int i = 0; i < listS.size(); i++) {
                sqltext +="DELETE FROM song WHERE streamUri = '"+listS.get(i).getStreamUrl()+"' AND id_playlist = "+pl.getId()+";\n";
            }
            for (String sql : sqltext.split("\n"))
                db.execSQL(sql);
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public static boolean editNamePlaylist(Context context, int id_playlist, String newName) {
        SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE,null);
        try {
            db.execSQL("UPDATE playlist SET title = '"+newName+"' WHERE id = "+id_playlist+";");
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public static boolean createHistoryDatabase(Context context){
        try {
            SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
            String query="CREATE TABLE IF NOT EXISTS search_history(id integer PRIMARY KEY AUTOINCREMENT, history text)";
            db.execSQL(query);
            db.close();

            return true;
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
            return false;
        }
    }


    public static boolean addHistory(Context context, ArrayList<String> history) {
        try {
            if(history==null) return false;
            SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
            String query="DELETE FROM search_history";
            db.execSQL(query);
            query="";
            for (String value : history) {
                query=query+"INSERT INTO search_history(history) VALUES("+"'"+value+"'"+"); ";
            }
            if(history.size()>0){
                db.execSQL(query);
            }

            db.close();
            return true;
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
            return false;
        }
    }

    public static ArrayList<String> getHistory(Context context) {
        ArrayList<String> historyList=new ArrayList<>();
        try {
            SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE,null);
            Cursor cs = db.rawQuery("SELECT * FROM search_history", null);
            while (cs.moveToNext()) {
                historyList.add(cs.getString(1));
            }
            db.close();
        } catch (Exception e) {
            Log.e("SQLite: ",e.getMessage());
        }
        return historyList;
    }

    public static boolean addToFavorite(Context context, BaiHat s) {
        if ( !checkSongIsFavorite(context, s) ) {
            SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
            try {
                String temp = s.getId()+",'"+s.getTitle()+"','"+s.getArtist()+"','"+s.getArtworkUrl()+"',"+s.getDuration()+",'"+s.getStreamUrl()+"',"+s.getPlaybackCount();
                db.execSQL("INSERT INTO favorite(id_song,title,artist,artworkUrl,duration,streamUri,playbackCount) VALUES("+temp+");");
                return true;
            } catch (Exception e) {
                Log.e("SQLite: ", e.getMessage());
            } finally {
                db.close();
            }
            return false;
        } else {
            return false;
        }
    }

    public static boolean deleteTrackInFavorite(Context context, BaiHat s) {
        SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
        try{
            db.execSQL("DELETE FROM favorite WHERE streamUri = '"+s.getStreamUrl()+"'");
            return true;
        } catch (Exception e) {
            Log.e("SQLite: ", e.getMessage());
        } finally {
            db.close();
        }
        return false;
    }

    public static ArrayList<BaiHat> getAllFavorite(Context context) {
        ArrayList<BaiHat> result = new ArrayList<BaiHat>();
        SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
        try {
            Cursor cs = db.rawQuery("SELECT * FROM favorite", null);
            while (cs.moveToNext()) {
                int song_id = cs.getInt(1);
                String song_title = cs.getString(2);
                String artist = cs.getString(3);
                String artworkUrl = cs.getString(4);
                int duration = cs.getInt(5);
                String streamUri = cs.getString(6);
                int playbackCount = cs.getInt(7);
                result.add(new BaiHat(song_id,song_title,artist,artworkUrl,duration,streamUri,playbackCount));
            }
        } catch (Exception e) {
            Log.e("SQLite: ", e.getMessage());
        } finally {
            db.close();
        }
        return result;
    }

    public static boolean checkSongIsFavorite(Context context, BaiHat s) {
        SQLiteDatabase db = context.openOrCreateDatabase(db_name, context.MODE_PRIVATE, null);
        try {
            Cursor cs = db.rawQuery("SELECT * FROM favorite WHERE streamUri = '"+s.getStreamUrl()+"';", null);
            if (cs.moveToNext()) {
                return true;
            }
        } catch (Exception e) {
            Log.e("SQLite: ", e.getMessage());
            return false;
        } finally {
            db.close();
        }
        return false;
    }
}
