package com.ef;

import com.ef.data.BlockedIp;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by alian on 28.10.2017.
 */
public class ParserTest {

    @Test
    public void shouldParseTestAccessFile() {
        LogFileReader fileReader = new LogFileReader();
        List<BlockedIp> blockedIps = fileReader.readLogFile("2017-01-01.00:00:00", "hourly", 5);

        assertEquals(1, blockedIps.size());
        BlockedIp blockedIp = blockedIps.get(0);
        assertEquals("192.168.234.82", blockedIp.getIpAddress());
        assertEquals(6, blockedIp.getAccessCount());
    }
}
