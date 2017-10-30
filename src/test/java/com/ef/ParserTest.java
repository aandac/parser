package com.ef;

import com.ef.db.MysqlConnectionFactory;
import org.junit.Test;

import java.sql.Connection;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;


/**
 * Created by alian on 28.10.2017.
 */
public class ParserTest {

    @Test
    public void test(){
        Duration duration = Duration.of(1, ChronoUnit.DAYS);

        assertEquals(60*60*24,duration.getSeconds());
    }
}
