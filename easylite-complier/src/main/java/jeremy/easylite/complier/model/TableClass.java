package jeremy.easylite.complier.model;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import jeremy.easylite.annotation.EasyColumn;
import jeremy.easylite.annotation.EasyTable;
import jeremy.easylite.complier.TypeUtil;

/**
 * Created by JIANGJIAN650 on 2018/5/25.
 */
public class TableClass {
    private String KEY_ID = "_easy_id";

    public static TableClass create(TypeElement tableEle) {
        return new TableClass(tableEle);
    }

    private Name qualifiedName;//类全名（包名.名称）
    private Name simpleName;//类简单名
    private TypeMirror typeMirror;

    private String tableName;//注解获取的表名

    private List<ColumnField> columnFields = new ArrayList<>();
    private List<String> methods = new ArrayList<>();

    private String CREATE_TABLE;

    public TableClass(TypeElement tableEle) {
        qualifiedName = tableEle.getQualifiedName();
        simpleName = tableEle.getSimpleName();
        typeMirror = tableEle.asType();

        EasyTable bindView = tableEle.getAnnotation(EasyTable.class);
        tableName = bindView.name();
        if (tableName == null || "".equals(tableName))
            tableName = simpleName.toString();
        initColumn(tableEle);
        CREATE_TABLE = createTableSQL();
        info("simpleName:" + simpleName + "\ntable name:" + tableName + "\ncolumnFields:" + columnFields);
    }

    private void initColumn(TypeElement tableEle) {
        methods.clear();
        columnFields.clear();
        List<? extends Element> enclosedEle = tableEle.getEnclosedElements();
        for (Element enclosed : enclosedEle) {
            if (enclosed.getKind().isField()) {
                VariableElement columnEle = (VariableElement) enclosed;
                EasyColumn easyColumn = columnEle.getAnnotation(EasyColumn.class);
                if (easyColumn != null) {
                    columnFields.add(new ColumnField(columnEle.getSimpleName(), columnEle.asType(), easyColumn.name(), easyColumn.unique(), easyColumn.notNull()));
                }
            } else if (enclosed.getKind().equals(ElementKind.METHOD)) {
                String methodName = enclosed.getSimpleName().toString();
                if (methodName.contains("get"))
                    methods.add(enclosed.getSimpleName().toString());
                System.out.println("method name:" + enclosed.getSimpleName().toString());
            }
        }
        for (ColumnField columnField : columnFields) {
            boolean notHas = methods.indexOf(columnField.createGetName()) == -1;
            System.out.println("notHas " + columnField.getSimpleName() + " getter " + notHas);
            if (notHas) {
                try {
                    throw new Exception(columnField.getSimpleName() + " must has getter and setter!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String createTableSQL() {
        String CREATE_TABLE = "CREATE TABLE " + tableName + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        for (ColumnField columnField : columnFields) {
            CREATE_TABLE = CREATE_TABLE + columnField.getColumnSQL();
        }
        CREATE_TABLE = CREATE_TABLE.substring(0, CREATE_TABLE.length() - 1);
        CREATE_TABLE = CREATE_TABLE + ")";
        return CREATE_TABLE;
    }

    public String getTableSQL() {
        return CREATE_TABLE;
    }

    public JavaFile generateFinder() {
        /**
         * 构建类
         */
        TypeSpec.Builder finderClass = TypeSpec.classBuilder(getEasyDaoName())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.IEasyDao, TypeName.get(typeMirror)))
                .addField(getClassName(), "ins", Modifier.PRIVATE, Modifier.STATIC)
                .addMethod(getStaticMethod())
                .addMethod(getSaveMethod())
                .addMethod(getUpdataMethod())
                .addMethod(getDeleteMethod())
                .addMethod(getFindMethod())
                .addMethod(getCountMethod())
                .addMethod(getContentValuesMethod());

        FieldSpec.Builder keyIdBuilder = FieldSpec.builder(String.class, "KEY" + KEY_ID.toUpperCase(),
                Modifier.STATIC, Modifier.PUBLIC, Modifier.FINAL)
                .initializer("\""+KEY_ID+"\"")
                .addJavadoc(KEY_ID + "'s key!");
        finderClass.addField(keyIdBuilder.build());

        for (ColumnField columnField : columnFields) {
            FieldSpec.Builder fieldBuilder = FieldSpec.builder(String.class, "KEY_" + columnField.getName().toUpperCase(),
                    Modifier.STATIC, Modifier.PUBLIC, Modifier.FINAL)
                    .initializer("\""+columnField.getName()+"\"")
                    .addJavadoc(columnField.getSimpleName() + "'s key!");
            finderClass.addField(fieldBuilder.build());
        }
        return JavaFile.builder(getPackageName(), finderClass.build()).build();
    }

    String getPackageName() {
        String quaName = qualifiedName.toString();
        String packageName = quaName.substring(0, quaName.lastIndexOf("."));
        return packageName;
    }

    MethodSpec getSaveMethod() {
        MethodSpec.Builder saveBuilder = MethodSpec.methodBuilder("save")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(typeMirror), "t")
                .returns(long.class)
                .addStatement("ContentValues v = getContentValues(t)")
                .addStatement("return jeremy.easylite.api.EasyDatabaseUtil.save(\"$N\",$N)", tableName, "v");
        return saveBuilder.build();
    }

    MethodSpec getUpdataMethod() {
        MethodSpec.Builder updataBuilder = MethodSpec.methodBuilder("updata")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(typeMirror), "t")
                .addParameter(String.class, "whereClause")
                .addParameter(String[].class, "whereArgs")
                .varargs(true)
                .returns(long.class)
                .addStatement("ContentValues v = getContentValues(t)")
                .addStatement("return jeremy.easylite.api.EasyDatabaseUtil.update(\"$N\",$N,$N,$N)", tableName, "v", "whereClause", "whereArgs");
        return updataBuilder.build();
    }

    MethodSpec getDeleteMethod() {
        MethodSpec.Builder deleteBuilder = MethodSpec.methodBuilder("delete")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(String.class, "whereClause")
                .addParameter(String[].class, "whereArgs")
                .varargs(true)
                .returns(long.class)
                .addStatement("return jeremy.easylite.api.EasyDatabaseUtil.delete(\"$N\",$N,$N)", tableName, "whereClause", "whereArgs");
        return deleteBuilder.build();
    }

    MethodSpec getCountMethod() {
        MethodSpec.Builder deleteBuilder = MethodSpec.methodBuilder("count")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(String.class, "whereClause")
                .addParameter(String[].class, "whereArgs")
                .varargs(true)
                .returns(long.class)
                .addStatement("return jeremy.easylite.api.EasyDatabaseUtil.count(\"$N\",$N,$N)", tableName, "whereClause", "whereArgs");
        return deleteBuilder.build();
    }

    MethodSpec getFindMethod() {
        MethodSpec.Builder findBuilder = MethodSpec.methodBuilder("find")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(String[].class, "columns")
                .addParameter(String.class, "selection")
                .addParameter(String[].class, "selectionArgs")
                .addParameter(String.class, "groupBy")
                .addParameter(String.class, "having")
                .addParameter(String.class, "orderBy")
                .addParameter(String.class, "limit")
                .returns(List.class)
                .addStatement("List<$N> list = new java.util.ArrayList<>()", simpleName)
                .addStatement("android.database.Cursor c = jeremy.easylite.api.EasyDatabaseUtil.find(\"$N\",$N,$N,$N,$N,$N,$N,$N)",
                        getTableName(), "columns", "selection", "selectionArgs", "groupBy", "having", "orderBy", "limit")
                .beginControlFlow("try")
                .beginControlFlow("if($N.moveToFirst())", "c")
                .beginControlFlow("do")
                .addStatement("$N info = new $N()", simpleName, simpleName);
//        info.setUrl(c.getString(c.getColumnIndex("url")));
        for (ColumnField columnField : columnFields) {
            String type = columnField.getTypeToCursor();
            if (type == null)
                continue;
            if (type.equals("Boolean")) {
                findBuilder.addStatement("info.$N(c.getInt(c.getColumnIndex(\"$N\"))>0)", columnField.createSetName(), columnField.getName());
            } else {
                findBuilder.addStatement("info.$N(c.get$N(c.getColumnIndex(\"$N\")))", columnField.createSetName(), type, columnField.getName());
            }
        }
        findBuilder.addStatement("list.add(info)")
                .endControlFlow("while($N.moveToNext())", "c")
                .endControlFlow()
                .endControlFlow()
                .beginControlFlow("catch(Exception e)")
                .addStatement("e.printStackTrace()")
                .endControlFlow()
                .beginControlFlow("finally")
                .beginControlFlow("if(c != null)")
                .addStatement(" c.close()")
                .endControlFlow()
                .endControlFlow()
                .addStatement("return list");

        return findBuilder.build();
    }

    MethodSpec getContentValuesMethod() {
        MethodSpec.Builder getContentValuesBuilder = MethodSpec.methodBuilder("getContentValues")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(typeMirror), "t")
                .returns(TypeUtil.ContentValues)
                .addStatement("ContentValues v = new ContentValues()");

        for (ColumnField columnField : columnFields) {
            getContentValuesBuilder.addStatement("v.put(\"$N\",t.$N())", columnField.getName(), columnField.createGetName());
        }
        getContentValuesBuilder.addStatement("return v");
        return getContentValuesBuilder.build();
    }

    MethodSpec getStaticMethod() {
        MethodSpec.Builder staticBuilder = MethodSpec.methodBuilder("getIns")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(getClassName())
                .beginControlFlow("if(ins==null)")
                .addStatement("ins = new $N()", getEasyDaoName())
                .endControlFlow()
                .addStatement("return ins");
        return staticBuilder.build();
    }

    private String getEasyDaoName() {
        return simpleName + "EasyDao";
    }

    private ClassName getClassName() {
        return ClassName.bestGuess(getPackageName() + "." + getEasyDaoName());
    }

    private void info(String s) {
        System.out.println(s);
    }

    public Name getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(Name qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public Name getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(Name simpleName) {
        this.simpleName = simpleName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnField> getColumnFields() {
        return columnFields;
    }

    public void setColumnFields(List<ColumnField> columnFields) {
        this.columnFields = columnFields;
    }
}
