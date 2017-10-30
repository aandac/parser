package com.ef.db;

import com.ef.data.BlockedIp;
import com.ef.data.ParserParameters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author alian
 */
public class ParserMysqlDBConnector {

    private final String INSERT_SQL = "INSERT INTO wallethub.BLOCKED_IP (IP,STARTDATE,ENDDATE,REQUESTCOUNT,REASON) VALUES (?,?,?,?,?)";

    private DateTimeFormatter dtfInputParameter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");

    public void persistBlockedIp(List<BlockedIp> blockedIps, ParserParameters parameter) {
        try (Connection connection = MysqlConnectionFactory.createConnection();
             PreparedStatement stmt = connection.prepareStatement(INSERT_SQL)) {
            int i = 0;
            int batchCount = 1;
            LocalDateTime startDate = LocalDateTime.parse(parameter.getStartDate(), dtfInputParameter);
            LocalDateTime endDate = startDate.plus(1, parameter.getDuration().equals("daily") ? ChronoUnit.DAYS : ChronoUnit.HOURS);

            for (BlockedIp blockedIp : blockedIps) {
                stmt.setString(++i, blockedIp.getIpAddress());
                stmt.setTimestamp(++i, Timestamp.valueOf(startDate));
                stmt.setTimestamp(++i, Timestamp.valueOf(endDate));

                // REQUESTCOUNT
                stmt.setInt(++i, blockedIp.getAccessCount());

                // REASON
                stmt.setString(++i, blockedIp.getReason(parameter.getThreshold()));
                i=0;
                stmt.addBatch();
                batchCount++;
                if (batchCount % 100 == 0) {
                    stmt.executeBatch();
                    stmt.clearBatch();
                }
            }
            stmt.executeBatch();
            stmt.clearBatch();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
