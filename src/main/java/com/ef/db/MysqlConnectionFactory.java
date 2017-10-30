package com.ef.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MysqlConnectionFactory {

    /**
     * Hidden constructor.
     */
    private MysqlConnectionFactory() {
    }

    /**
     * Creates the connection.
     *
     * @return connection instance
     */
    public static Connection createConnection() {
        try {
            Properties params = new Properties();
            params.load(MysqlConnectionFactory.class.getResourceAsStream("db.properties"));

            Properties props = new Properties();
            props.put("user", params.get("USER"));
            props.put("password", params.get("PASSWORD"));
            String jdbcUrl = params.getProperty("JDBC_URL");
            Class.forName(params.getProperty("JDBC_DRIVER"));
            return DriverManager.getConnection(jdbcUrl, props);
        } catch (Exception ex) {
            throw new IllegalStateException("Mysql connection could not created", ex);
        }
    }

}
