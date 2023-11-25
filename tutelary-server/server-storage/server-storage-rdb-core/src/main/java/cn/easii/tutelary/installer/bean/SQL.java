package cn.easii.tutelary.installer.bean;

public class SQL {

    private static String LINE_END = System.lineSeparator();

    private static String TAB = "\t";

    private final StringBuilder sql;

    public SQL() {
        this.sql = new StringBuilder();
    }

    public SQL append(String fragment) {
        sql.append(fragment);
        return this;
    }

    public SQL append(boolean condition, String fragment) {
        if (condition) {
            return append(fragment);
        }
        return this;
    }

    public SQL appendTab(String fragment) {
        sql.append(fragment).append(TAB);
        return this;
    }

    public SQL appendTab(boolean condition, String fragment) {
        if (condition) {
            return appendTab(fragment);
        }
        return this;
    }

    public SQL appendLine(String line) {
        sql.append(line).append(LINE_END);
        return this;
    }

    public SQL appendLine(boolean condition, String line) {
        if (condition) {
            return appendLine(line);
        }
        return this;
    }

    @Override
    public String toString() {
        return sql.toString();
    }

}
