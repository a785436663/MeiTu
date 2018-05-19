package jeremy.meitu.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by JIANGJIAN650 on 2018/5/19.
 */

public class Utils {
    public static int getLastPosition(StaggeredGridLayoutManager mStaggeredGridLayoutManager) {
        int[] lastPositions = new int[mStaggeredGridLayoutManager.getSpanCount()];
        mStaggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static int getLastPosition(GridLayoutManager layoutManager) {
        return layoutManager.findLastVisibleItemPosition();
    }
}
