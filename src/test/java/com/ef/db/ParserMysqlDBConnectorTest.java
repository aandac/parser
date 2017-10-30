package com.ef.db;

import com.ef.data.BlockedIp;
import com.ef.data.ParserParameters;
import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author alian
 */
public class ParserMysqlDBConnectorTest {

    @After
    public void cleanUpTest() {
        try (Connection connection = MysqlConnectionFactory.createConnection();
             PreparedStatement stmt = connection.prepareStatement("delete from wallethub.BLOCKED_IP")) {
            stmt.execute();
            System.out.println("Database cleared.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldGetConnection() {
        Connection connection = MysqlConnectionFactory.createConnection();
        assertNotNull(connection);
    }

    @Test
    public void shouldInsertBlockedIpList() {
        BlockedIp ip1 = new BlockedIp("192.168.1.1", 300);
        BlockedIp ip2 = new BlockedIp("192.168.1.2", 100);
        BlockedIp ip3 = new BlockedIp("192.168.1.3", 250);
        List<BlockedIp> blockedIps = new ArrayList<>(3);
        blockedIps.add(ip1);
        blockedIps.add(ip2);
        blockedIps.add(ip3);
        ParserMysqlDBConnector connector = new ParserMysqlDBConnector();

        connector.persistBlockedIp(blockedIps, new ParserParameters("2017-01-01.15:00:00", "hourly", 100));

        try (Connection connection = MysqlConnectionFactory.createConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * from wallethub.BLOCKED_IP where IP='192.168.1.1'")) {
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                int requestcount = resultSet.getInt("REQUESTCOUNT");
                Timestamp timestamp = resultSet.getTimestamp("ENDDATE");


                assertEquals(300, requestcount);
                assertEquals("2017-01-01T16:00", timestamp.toLocalDateTime().toString());

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
