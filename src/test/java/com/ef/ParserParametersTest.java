package com.ef;

import com.ef.data.ParserParameters;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alian on 30.10.2017.
 */
public class ParserParametersTest {


    private String[] args;
    private String accessLogPath;
    private String startDateInStr;
    private String duration;

    @Before
    public void initTest() {
        args = new String[3];
        accessLogPath = "C:\\Users\\alian\\Documents\\workspace\\parser\\src\\main\\resources\\access.log";
        args[0] = "--accesslog=" + accessLogPath;
        startDateInStr = "2017-01-01.00:00:00";
        args[1] = "--startDate=" + startDateInStr;
        duration = "daily";
        args[2] = "--duration=" + duration;

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        new ParserParameters(args);
    }

    @Test
    public void shouldParseArguments() {
        String[] arguments = new String[4];
        arguments[0]=args[0];
        arguments[1]=args[1];
        arguments[2]=args[2];
        arguments[3]="--threshold=200";

        ParserParameters parameters = new ParserParameters(arguments);
        assertEquals(accessLogPath,parameters.getAccessLog());
        assertEquals(startDateInStr,parameters.getStartDate());
        assertEquals(duration,parameters.getDuration());
        assertEquals(200,parameters.getThreshold());
    }
}
