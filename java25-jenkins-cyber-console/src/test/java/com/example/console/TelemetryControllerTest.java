package com.example.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.console.controller.TelemetryController;
import org.junit.jupiter.api.Test;

class TelemetryControllerTest {

    private final TelemetryController controller = new TelemetryController();

    @Test
    void healthEndpointReturnsOnline() {
        var response = controller.health();
        assertEquals("ONLINE", response.get("status"));
        assertEquals(3000, response.get("port"));
    }

    @Test
    void pingEndpointIncrementsSequence() {
        var first = controller.triggerPing("NODE-A");
        var second = controller.triggerPing("NODE-B");
        assertEquals(1, first.get("sequence"));
        assertEquals(2, second.get("sequence"));
        assertTrue(second.get("result").toString().contains("ACK"));
    }
}
