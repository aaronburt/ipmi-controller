package uk.co.aaronburt.ipmi.controller;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SystemUtils;
import org.hibernate.mapping.Any;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.aaronburt.ipmi.service.IpmiService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final IpmiService service;

    @PostMapping("/power/status")
    public Map<String, Object> hey() {
        Map<String, Object> map = new HashMap<>();
        map.put("action", "/power/status");
        map.put("state", service.getPowerStatus());
        return map;
    }

    @PostMapping("/power/{state}")
    public Map<String, Object> powerOn(@PathVariable String state) {
        Map<String, Object> map = new HashMap<>();
        map.put("action", "/power/" + state);
        map.put("state", service.setPowerStatus(state));
        return map;
    }

    @PostMapping("/fanspeed/{fanSpeed}")
    public Map<String, Object> setFanSpeed(@PathVariable Integer fanSpeed) throws IOException {

        Map<String, Object> map = new HashMap<>();
        if (fanSpeed >= 0 && fanSpeed <= 100) {
            map.put("action", "/fanspeed/" + fanSpeed);
            map.put("state", service.setFanSpeed(fanSpeed));
        } else {
            map.put("state", "failed");
        }

        return map;
    }
}
