package ru.job4j.jdbc;

import java.io.FileReader;
import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {
    private Connection connection;
    private final Properties properties;

    public TableEditor(Properties properties) {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() {
        var url = properties.getProperty("hibernate.connection.url");
        var username = properties.getProperty("hibernate.connection.username");
        var password = properties.getProperty("hibernate.connection.password");
        try {
            Class.forName(properties.getProperty("hibernate.connection.driver_class"));
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String tableName) {
        var sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s();",
                tableName
        );
        execute(sql);
    }

    public void dropTable(String tableName) {
        var sql = String.format(
                "DROP TABLE IF EXISTS %s;",
                tableName
        );
        execute(sql);
    }

    public void addColumn(String tableName, String columnName, String type) {
        var sql = String.format(
                "ALTER TABLE %s ADD COLUMN IF NOT EXISTS %s %s",
                tableName,
                columnName,
                type
        );
        execute(sql);
    }

    public void dropColumn(String tableName, String columnName) {
        var sql = String.format(
                "ALTER TABLE %s DROP IF EXISTS %s",
                tableName,
                columnName
        );
        execute(sql);
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) {
        var sql = String.format(
                "ALTER TABLE %s RENAME COLUMN %s TO %s",
                tableName,
                columnName,
                newColumnName
        );
        execute(sql);
    }

    public String getTableScheme(String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = this.connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    private void execute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        var tableName = "test_table";
        Properties properties = new Properties();
        try (FileReader fr = new FileReader("app.properties")) {
            properties.load(fr);
            try (TableEditor tableEditor = new TableEditor(properties)) {
                tableEditor.createTable(tableName);
                System.out.println(tableEditor.getTableScheme(tableName));

                tableEditor.addColumn(tableName, "column1", "text");
                System.out.println(tableEditor.getTableScheme(tableName));

                tableEditor.renameColumn(tableName, "column1", "column2");
                System.out.println(tableEditor.getTableScheme(tableName));

                tableEditor.dropColumn(tableName, "column2");
                System.out.println(tableEditor.getTableScheme(tableName));

                tableEditor.dropTable(tableName);
                System.out.println(tableEditor.getTableScheme(tableName));
            }
        }
    }
}
