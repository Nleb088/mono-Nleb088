package com.carbon_it.space;

import com.carbon_it.space.missioncontrol.MissionControl;
import com.carbon_it.space.missioncontrol.ValidatingMissionControl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpaceCenter {

    public static void main(String[] args) {
        SpringApplication.run(SpaceCenter.class, args);
    }

    @Bean
    // TODO 4 : as seen in the beginning of this workshop, configure this bean definition method in order to ask for
    //  an instance of org.springframework.validation.Validator, and pass it to the constructor of the ValidatingMissionControl class.
    public MissionControl missionControl() {
        return new ValidatingMissionControl(null);
    }
}
