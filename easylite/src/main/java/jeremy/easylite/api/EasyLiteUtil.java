package jeremy.easylite.api;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import jeremy.easylite.api.config.Config;
import jeremy.easylite.api.utils.EasyCursorFactory;
import jeremy.easylite.api.utils.LogUtils;
import jeremy.easylite.api.utils.Utils;

/**
 * Created by JIANGJIAN650 on 2018/5/22.
 */

public class EasyLiteUtil {
    public static void init(Application application) {
        boolean boo = Config.getDebugEnabled(application);
        LogUtils.setDebug(boo);
        LogUtils.d("getDatabaseName:" + Config.getDatabaseName(application));
        LogUtils.d("getDatabaseVersion:" + Config.getDatabaseVersion(application));
        EasyDatabaseUtil.init(application, boo);
    }
}
