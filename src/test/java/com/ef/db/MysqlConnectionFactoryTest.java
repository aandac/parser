package com.ef.db;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertNotNull;

/**
 * @author alian
 */
public class MysqlConnectionFactoryTest {

    @Test
    public void shouldGetConnection() {
        Connection connection = MysqlConnectionFactory.createConnection();
        assertNotNull(connection);
    }
}
