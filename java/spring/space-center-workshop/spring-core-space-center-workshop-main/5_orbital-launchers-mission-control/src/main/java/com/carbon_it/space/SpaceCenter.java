package com.carbon_it.space;

import com.carbon_it.space.missioncontrol.MissionControl;
import com.carbon_it.space.missioncontrol.CustomMissionControl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpaceCenter {

    public static void main(String[] args) {
        SpringApplication.run(SpaceCenter.class, args);
    }

    // TODO 5 : hook onInit_JavaConfig and onDestroy_JavaConfig properly.
    @Bean
    public MissionControl missionControl() {
        return new CustomMissionControl();
    }
}
