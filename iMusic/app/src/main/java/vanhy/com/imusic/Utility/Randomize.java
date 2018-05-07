package vanhy.com.imusic.Utility;

import android.util.Log;

import java.util.Random;
import java.util.RandomAccess;

public class Randomize {
    public static int[] getShuffle(int total) {
        int[] result = new int[total];
        Random rd = new Random();
        for (int i = 0; i < result.length; i++) {
            int value=rd.nextInt(total)+1;
            while (checkUsed(result, value, i)) {
                value=rd.nextInt(total)+1;
            }
            result[i]=value;
        }
        return result;
    }

    private static boolean checkUsed(int[] shuffle, int value, int pos) {
        for (int i = 0; i < pos+1; i++) {
            if (value == shuffle[i]) {
                return true;
            }
        }
        return false;
    }
}
