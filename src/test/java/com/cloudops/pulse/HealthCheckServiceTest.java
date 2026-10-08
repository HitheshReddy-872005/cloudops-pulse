package com.cloudops.pulse;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class HealthCheckServiceTest {

    private final HealthCheckService service = new HealthCheckService();

    @Test
    void testInvalidHostReturnsDown() {
        Map<String, Object> result = service.pingTarget("http://non-existent-domain-xyz-123.com");
        assertEquals("DOWN", result.get("status"));
        assertEquals(503, result.get("statusCode"));
    }
}
