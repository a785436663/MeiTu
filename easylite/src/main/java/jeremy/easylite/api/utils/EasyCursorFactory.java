package jeremy.easylite.api.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

public class EasyCursorFactory implements SQLiteDatabase.CursorFactory {

    private boolean debugEnabled;

    public EasyCursorFactory() {
        this.debugEnabled = false;
    }

    public EasyCursorFactory(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
    }

    @SuppressWarnings("deprecation")
    public Cursor newCursor(SQLiteDatabase sqLiteDatabase,
                            SQLiteCursorDriver sqLiteCursorDriver,
                            String editTable,
                            SQLiteQuery sqLiteQuery) {
        if (debugEnabled) {
            Log.d("EasyLiteFactory", sqLiteQuery.toString());
        }
        return new SQLiteCursor(sqLiteDatabase, sqLiteCursorDriver, editTable, sqLiteQuery);
    }
}
