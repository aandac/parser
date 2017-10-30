package com.ef.data;

import org.apache.commons.cli.*;

/**
 * @author alian
 */
public class ParserParameters {

    private String startDate;

    private String duration;

    private int threshold;

    public ParserParameters(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            Options options = new Options();
            options.addRequiredOption("s", "startDate", true, "Start Date");
            options.addRequiredOption("d", "duration", true, "Duration");
            options.addRequiredOption("t", "threshold", true, "Threshold");
            CommandLine cmd = parser.parse(options, args);
            startDate = cmd.getOptionValue("startDate");
            duration = cmd.getOptionValue("duration");
            threshold = Integer.valueOf(cmd.getOptionValue("threshold"));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ParserParameters(String startDate, String duration, int threshold) {
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDuration() {
        return duration;
    }

    public int getThreshold() {
        return threshold;
    }
}
