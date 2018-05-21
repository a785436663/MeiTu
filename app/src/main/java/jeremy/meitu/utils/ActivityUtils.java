package jeremy.meitu.utils;

import android.app.Activity;

import java.util.LinkedList;

public class ActivityUtils {
    // 声明一个集合用于记录所有打开的活动  
    private static final LinkedList<Activity> ACTIVITY_LIST = new LinkedList<>();

    // 加入活动对象--------->onCreate  
    public static void add(Activity activity) {
        ACTIVITY_LIST.add(activity);
    }

    // 移除活动对象--------->onDestroy  
    public static void remove(Activity activity) {
        ACTIVITY_LIST.remove(activity);
    }

    // 关闭所有的活动--------->close  
    public static void removeAll() {
        for (Activity activity : ACTIVITY_LIST) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}  