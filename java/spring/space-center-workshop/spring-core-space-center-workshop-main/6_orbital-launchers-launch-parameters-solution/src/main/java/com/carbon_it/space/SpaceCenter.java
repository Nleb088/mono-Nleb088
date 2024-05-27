package com.carbon_it.space;

import com.carbon_it.space.missioncontrol.MissionControl;
import com.carbon_it.space.missioncontrol.ValidatingMissionControl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;

@SpringBootApplication
public class SpaceCenter {

    public static void main(String[] args) {
        SpringApplication.run(SpaceCenter.class, args);
    }

    @Bean
    public MissionControl missionControl(Validator validator) {
        return new ValidatingMissionControl(validator);
    }
}
