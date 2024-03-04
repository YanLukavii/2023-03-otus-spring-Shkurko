package ru.otus.hw.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DinnerHealthIndicator implements HealthIndicator {
    
    private final LocalTime time = LocalTime.now();
    
    @Override
    public Health health() {
        
        if (time.getHour() == 13) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "У нас ОБЕД!")
                    .build();
        } else {
            return Health.up().withDetail("message", "Работаем, обед c 13:00!").build();
        }
    }
}