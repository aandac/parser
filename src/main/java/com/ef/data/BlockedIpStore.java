package com.ef.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author alian
 */
public class BlockedIpStore {

    private Map<String, Integer> blockCountMap = new HashMap<>();

    public void addIp(String ip) {
        blockCountMap.merge(ip, 1, (a, b) -> a + b);
    }

    public List<BlockedIp> getBlockedIp(int threshold) {
        return blockCountMap.entrySet()
                .parallelStream()
                .filter(e -> e.getValue() > threshold)
                .sorted(Map.Entry.comparingByValue())
                .map(entry -> new BlockedIp(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
