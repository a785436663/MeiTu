package jeremy.easylite.complier.model;

import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

/**
 * Created by JIANGJIAN650 on 2018/5/25.
 */
public class ColumnField {
    private Name simpleName;//参数名
    private TypeMirror typeMirror;//参数的类型 String、int...

    private String name;//注解获取的KEY
    private boolean unique;//注解获取的是否唯一
    private boolean notNull;//注解获取的是否可以为空

    public ColumnField(Name simpleName, TypeMirror typeMirror, String name, boolean unique, boolean notNull) {
        this.simpleName = simpleName;
        this.typeMirror = typeMirror;
        this.name = name;
        if (name == null || "".equals(name))
            this.name = simpleName.toString();
        this.unique = unique;
        this.notNull = notNull;
    }

    public String getColumnSQL() {
        if (getTypeToSQL() == null)
            return "";
        String sqlStr = name + " " + getTypeToSQL();
        if (notNull)
            sqlStr = sqlStr + " NOT NULL";
        if (unique)
            sqlStr = sqlStr + " UNIQUE";
        sqlStr = sqlStr + ",";
        return sqlStr;
    }

    public Name getSimpleName() {
        return simpleName;
    }

    public String createGetName() {
        String name = simpleName.toString();
        if ("Boolean".equals(getTypeToCursor()))
            return "is" + name.substring(0, 1).toUpperCase() + name.substring(1);
        return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    public String createSetName() {
        String name = simpleName.toString();
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    public void setSimpleName(Name simpleName) {
        this.simpleName = simpleName;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public void setTypeMirror(TypeMirror typeMirror) {
        this.typeMirror = typeMirror;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getTypeToCursor() {
        if (typeMirror == null || "".equals(typeMirror.toString().toLowerCase()))
            return null;
        String typeMirrorStr = typeMirror.toString().toLowerCase();
        if (typeMirrorStr.contains("long")) {
            return "Long";
        }
        if (typeMirrorStr.contains("float")) {
            return "Float";
        }
        if (typeMirrorStr.contains("double")) {
            return "Double";
        }
        if (typeMirrorStr.contains("string")) {
            return "String";
        }
        if (typeMirrorStr.contains("boolean"))
            return "Boolean";
        if (typeMirrorStr.contains("int"))
            return "Int";
        if (typeMirrorStr.contains("short"))
            return "Short";
        return null;
    }

    public String getTypeToSQL() {
        if (typeMirror == null || "".equals(typeMirror.toString().toLowerCase()))
            return null;
        String typeMirrorStr = typeMirror.toString().toLowerCase();
        if (typeMirrorStr.contains("long")
                || typeMirrorStr.contains("int")
                || typeMirrorStr.contains("byte")
                || typeMirrorStr.contains("short")) {
            return "INTEGER";
        }
        if (typeMirrorStr.contains("float")
                || typeMirrorStr.contains("double")) {
            return "REAL";
        }
        if (typeMirrorStr.contains("string")) {
            return "TEXT";
        }
        if (typeMirrorStr.contains("boolean"))
            return "BOOLEAN";
        return null;
    }

    @Override
    public String toString() {
        return "\nColumnField{" +
                "simpleName=" + simpleName +
                ", typeMirror=" + typeMirror +
                ", typeMirror.getKind()=" + typeMirror.getKind() +
                ", name='" + name + '\'' +
                ", unique=" + unique +
                ", notNull=" + notNull +
                '}';
    }
}
