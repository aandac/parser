package com.ef;

import com.ef.data.BlockedIp;
import com.ef.data.ParserParameters;
import com.ef.db.ParserMysqlDBConnector;

import java.util.List;

/**
 * The tool takes "startDate", "duration" and "threshold" as command line arguments.
 * "startDate" is of "yyyy-MM-dd.HH:mm:ss" format,
 * "duration" can take only "hourly", "daily" as inputs and
 * "threshold" can be an integer.
 *
 * @author alian
 */
public class Parser {

    public static void main(String[] args) {
        // get the options
        ParserParameters parameters = new ParserParameters(args);

        // parse the log file and find the blocked ip list with the given criteria
        LogFileReader fileReader = new LogFileReader();
        int threshold = parameters.getThreshold();
        List<BlockedIp> blockedIps = fileReader.readLogFile(parameters.getStartDate(), parameters.getDuration(), threshold);

        // Print the blocked ip list
        blockedIps.forEach(ip -> System.out.println(ip.getReason(threshold)));

        // persist the blocked ip list to the MYSQL database
        ParserMysqlDBConnector connector = new ParserMysqlDBConnector();
        connector.persistBlockedIp(blockedIps, parameters);
    }
}
