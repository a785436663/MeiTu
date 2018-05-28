package jeremy.easylite.complier;

import com.squareup.javapoet.ClassName;

/**
 * Created by mingwei on 12/15/16.
 * CSDN:    http://blog.csdn.net/u013045971
 * Github:  https://github.com/gumingwei
 */
public class TypeUtil {
    public static final ClassName IEasyDao = ClassName.bestGuess("jeremy.easylite.api.IEasyDao");
    public static final ClassName SQLiteOpenHelper = ClassName.bestGuess("android.database.sqlite.SQLiteOpenHelper");
    public static final ClassName Context = ClassName.bestGuess("android.content.Context");
    public static final ClassName CursorFactory = ClassName.bestGuess("android.database.sqlite.SQLiteDatabase.CursorFactory");
    public static final ClassName SQLiteDatabase = ClassName.bestGuess("android.database.sqlite.SQLiteDatabase");
    public static final ClassName ContentValues = ClassName.bestGuess("android.content.ContentValues");
    public static final ClassName EasyDatabaseUtil = ClassName.bestGuess("jeremy.easylite.api.EasyDatabaseUtil");
}
