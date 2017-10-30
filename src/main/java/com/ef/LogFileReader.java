package com.ef;

import com.ef.data.BlockedIp;
import com.ef.data.BlockedIpStore;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Class for reading access information from the access.log file.
 * Extracts the blocked ip list.
 *
 * @author alian
 */
public class LogFileReader {


    /**
     * Reads the log file with given criteria.
     *
     * @param startDateInStr start date in string
     * @param duration       duration of the date range, can be daily or hourly
     * @param threshold      threshold access count
     * @return list of blocked ip
     */
    public List<BlockedIp> readLogFile(String startDateInStr, String duration, int threshold) {
        BlockedIpStore store = new BlockedIpStore();
        try (FileReader fileReader = new FileReader(new File("./access.log"))) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            DateTimeFormatter dtfLogFile = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
            DateTimeFormatter dtfInputParameter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
            LocalDateTime startDate = LocalDateTime.parse(startDateInStr, dtfInputParameter);
            long daysInSeconds = ChronoUnit.DAYS.getDuration().getSeconds();
            long hourInSeconds = ChronoUnit.HOURS.getDuration().getSeconds();

            while ((line = bufferedReader.readLine()) != null) {
                String[] parsedLine = line.split("\\|");
                String dateInStr = parsedLine[0];
                String ipAddress = parsedLine[1];

                // compare the date
                LocalDateTime logDate = LocalDateTime.parse(dateInStr, dtfLogFile);

                // skip if the log date is lower than start date
                // TODO ali startdate duration u asarsa da gecsin
                if (logDate.isBefore(startDate)) {
                    continue;
                }

                Duration between = Duration.between(logDate, startDate);
                long durationInSecond = Math.abs(between.getSeconds());

                if (duration.equals("hourly") && hourInSeconds > durationInSecond) {
                    store.addIp(ipAddress);
                } else if (duration.equals("daily") && daysInSeconds > durationInSecond) {
                    store.addIp(ipAddress);
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Access.log file is missing.", e);
        } catch (IOException e) {
            throw new RuntimeException("Log file could not read.", e);
        }

        return store.getBlockedIp(threshold);
    }
}
