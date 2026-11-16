package com.example.console.controller;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TelemetryController {

    private final AtomicInteger pingCounter = new AtomicInteger(1);

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "ONLINE",
                "service", "java25-cyber-console",
                "port", 3000,
                "timestamp", Instant.now().toString()
        );
    }

    @GetMapping("/telemetry")
    public Map<String, Object> telemetry() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemMb = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
        long maxMemMb = runtime.maxMemory() / (1024 * 1024);
        long uptimeSec = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;

        return Map.of(
                "javaVersion", System.getProperty("java.version"),
                "vmName", System.getProperty("java.vm.name"),
                "osArch", System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")",
                "cpuCores", runtime.availableProcessors(),
                "memoryUsage", usedMemMb + " MB / " + maxMemMb + " MB",
                "uptimeSeconds", uptimeSec
        );
    }

    @PostMapping("/ping")
    public Map<String, Object> triggerPing(@RequestParam(defaultValue = "JENKINS-NODE-01") String node) {
        int sequence = pingCounter.getAndIncrement();
        return Map.of(
                "sequence", sequence,
                "node", node.toUpperCase(),
                "result", "ACK // PAYLOAD VERIFIED ON JAVA " + Runtime.version().feature() + " [PORT 3000]",
                "timestamp", Instant.now().toString()
        );
    }
}
