package com.ef.data;

/**
 * @author alian
 */
public class BlockedIp {

    private String ipAddress;

    private int accessCount;

    public BlockedIp(String ipAddress, int accessCount) {
        this.ipAddress = ipAddress;
        this.accessCount = accessCount;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getAccessCount() {
        return accessCount;
    }

    public String getReason(int threshold) {
        return getIpAddress() + " is blocked. Because the ip address is accessed " + getAccessCount() + " times.Threshold is " + threshold + ".";
    }
}
