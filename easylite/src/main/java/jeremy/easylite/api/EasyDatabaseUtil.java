package jeremy.easylite.api;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jeremy.easylite.api.config.Config;
import jeremy.easylite.api.utils.EasyCursorFactory;
import jeremy.easylite.api.utils.LogUtils;
import jeremy.easylite.api.utils.Utils;

public class EasyDatabaseUtil {
    private static SQLiteOpenHelper sql;

    public static void init(Application application, boolean debug) {
        sql = Utils.getEasySQLOpenHelper(Config.CLASSNAME_EasyDatabaseHelper,
                application, Config.getDatabaseName(application),
                new EasyCursorFactory(debug),
                Config.getDatabaseVersion(application));
        getDB();
    }

    public static SQLiteDatabase getDB() {
        return sql.getWritableDatabase();
    }

    public static String[] getColumnNames(String tableName) {
        String[] columns = new String[0];
        SQLiteDatabase db = getDB();
        String query = "SELECT * FROM " + tableName;
        Cursor cr = db.rawQuery(query, null);
        try {
            int count = cr.getColumnCount();
            if (count == 0)
                return null;
            columns = new String[count];
            for (int i = 0; i < cr.getColumnCount(); i++) {
                // 获得所有列的数目及实际列数
                int columnCount = cr.getColumnCount();
                // 获得指定列的列名
                String columnName = cr.getColumnName(i);
                columns[i] = columnName;
                LogUtils.i("SQLiteDatabase getColumnNames:" + columnCount + "," + columnName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cr.close();
            return columns;
        }
    }

    public static String[] getAllTable() {
        SQLiteDatabase db = getDB();
        String query = "select name from sqlite_master where type='table' order by name";
        Cursor cr = db.rawQuery(query, null);
        int count = cr.getCount();
        if (count == 0)
            return null;
        String[] tables = new String[count];
        int i = 0;
        if (cr.moveToFirst()) {
            do {
                tables[i] = cr.getString(0);
                LogUtils.i("SQLiteDatabase table:" + tables[i]);
                i++;
            } while (cr.moveToNext());
        }
        return tables;
    }

    public static long save(String table, ContentValues values) {
        SQLiteDatabase db = getDB();
        long i = db.insert(table, null, values);
        LogUtils.i("SQLiteDatabase insert:" + i);
        return i;
    }

    public static long delete(String table, String whereClause, String... whereArgs) {
        SQLiteDatabase db = getDB();
        return db.delete(table, whereClause, whereArgs);
    }

    public static long update(String table, ContentValues values, String whereClause, String... whereArgs) {
        SQLiteDatabase db = getDB();
        return db.update(table, values, whereClause, whereArgs);
    }

    public static Cursor find(String table, String[] columns, String selection,
                                       String[] selectionArgs, String groupBy, String having,
                                       String orderBy) {
        SQLiteDatabase sqLiteDatabase = getDB();
        Cursor c = sqLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }

//    public static <T> void deleteAll(Class<T> type) {
//        Utils.getDaoByName(type.getSimpleName());
//        SQLiteDatabase db = EasyLiteUtil.getDB();
//        db.delete(NamingHelper.toSQLName(type), null, null);
//    }
//
//    public static <T> void deleteAll(Class<T> type, String whereClause, String... whereArgs) {
//        SugarDb db = getSugarContext().getSugarDb();
//        SQLiteDatabase sqLiteDatabase = db.getDB();
//        sqLiteDatabase.delete(NamingHelper.toSQLName(type), whereClause, whereArgs);
//    }
//
//    @SuppressWarnings("deprecation")
//    public static <T> void saveInTx(T... objects) {
//        saveInTx(Arrays.asList(objects));
//    }
//
//    @SuppressWarnings("deprecation")
//    public static <T> void saveInTx(Collection<T> objects) {
//        SQLiteDatabase sqLiteDatabase = getSugarContext().getSugarDb().getDB();
//        try {
//            sqLiteDatabase.beginTransaction();
//            sqLiteDatabase.setLockingEnabled(false);
//            for (T object : objects) {
//                SugarRecord.save(object);
//            }
//            sqLiteDatabase.setTransactionSuccessful();
//        } catch (Exception e) {
//            LogUtils.i("Sugar", "Error in saving in transaction " + e.getMessage());
//        } finally {
//            sqLiteDatabase.endTransaction();
//            sqLiteDatabase.setLockingEnabled(true);
//        }
//    }
//
//    public static <T> List<T> listAll(Class<T> type) {
//        return find(type, null, null, null, null, null);
//    }
//
//    public static <T> T findById(Class<T> type, Long id) {
//        List<T> list = find(type, "id=?", new String[]{String.valueOf(id)}, null, null, "1");
//        if (list.isEmpty()) return null;
//        return list.get(0);
//    }
//
//    public static <T> T findById(Class<T> type, Integer id) {
//        return findById(type, Long.valueOf(id));
//    }
//
//    public static <T> Iterator<T> findAll(Class<T> type) {
//        return findAsIterator(type, null, null, null, null, null);
//    }
//
//    public static <T> Iterator<T> findAsIterator(Class<T> type, String whereClause, String... whereArgs) {
//        return findAsIterator(type, whereClause, whereArgs, null, null, null);
//    }
//
//    public static <T> Iterator<T> findWithQueryAsIterator(Class<T> type, String query, String... arguments) {
//        SugarDb db = getSugarContext().getSugarDb();
//        SQLiteDatabase sqLiteDatabase = db.getDB();
//        Cursor c = sqLiteDatabase.rawQuery(query, arguments);
//        return new CursorIterator<>(type, c);
//    }
//
//    public static <T> Iterator<T> findAsIterator(Class<T> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {
//        SugarDb db = getSugarContext().getSugarDb();
//        SQLiteDatabase sqLiteDatabase = db.getDB();
//        Cursor c = sqLiteDatabase.query(NamingHelper.toSQLName(type), null, whereClause, whereArgs,
//                groupBy, null, orderBy, limit);
//        return new CursorIterator<>(type, c);
//    }
//
//    public static <T> List<T> find(Class<T> type, String whereClause, String... whereArgs) {
//        return find(type, whereClause, whereArgs, null, null, null);
//    }
//
//    public static <T> List<T> findWithQuery(Class<T> type, String query, String... arguments) {
//        SugarDb db = getSugarContext().getSugarDb();
//        SQLiteDatabase sqLiteDatabase = db.getDB();
//        T entity;
//        List<T> toRet = new ArrayList<>();
//        Cursor c = sqLiteDatabase.rawQuery(query, arguments);
//
//        try {
//            while (c.moveToNext()) {
//                entity = type.getDeclaredConstructor().newInstance();
//                SugarRecord.inflate(c, entity);
//                toRet.add(entity);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            c.close();
//        }
//
//        return toRet;
//    }
//
//    public static void executeQuery(String query, String... arguments) {
//        getSugarContext().getSugarDb().getDB().execSQL(query, arguments);
//    }
//
//    public static <T> List<T> find(Class<T> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {
//        SugarDb db = getSugarContext().getSugarDb();
//        SQLiteDatabase sqLiteDatabase = db.getDB();
//        T entity;
//        List<T> toRet = new ArrayList<>();
//        Cursor c = sqLiteDatabase.query(NamingHelper.toSQLName(type), null, whereClause, whereArgs,
//                groupBy, null, orderBy, limit);
//        try {
//            while (c.moveToNext()) {
//                entity = type.getDeclaredConstructor().newInstance();
//                SugarRecord.inflate(c, entity);
//                toRet.add(entity);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            c.close();
//        }
//        return toRet;
//    }
//
//    public static <T> long count(Class<?> type) {
//        return count(type, null, null, null, null, null);
//    }
//
//    public static <T> long count(Class<?> type, String whereClause, String[] whereArgs) {
//        return count(type, whereClause, whereArgs, null, null, null);
//    }
//
//    public static <T> long count(Class<?> type, String whereClause, String[] whereArgs, String groupBy, String orderBy, String limit) {
//        SQLiteDatabase sqLiteDatabase = EasyLiteUtil.getDB();
//
//        long toRet = -1;
//        String filter = (!TextUtils.isEmpty(whereClause)) ? " where " + whereClause : "";
//        SQLiteStatement sqLiteStatament = sqLiteDatabase.compileStatement("SELECT count(*) FROM " + NamingHelper.toSQLName(type) + filter);
//        if (whereArgs != null) {
//            for (int i = whereArgs.length; i != 0; i--) {
//                sqLiteStatament.bindString(i, whereArgs[i - 1]);
//            }
//        }
//        try {
//            toRet = sqLiteStatament.simpleQueryForLong();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            sqLiteStatament.close();
//        }
//        return toRet;
//    }
}
