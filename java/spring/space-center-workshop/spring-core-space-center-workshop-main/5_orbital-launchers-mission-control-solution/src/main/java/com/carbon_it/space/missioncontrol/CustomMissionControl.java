package com.carbon_it.space.missioncontrol;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class CustomMissionControl implements MissionControl, InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(CustomMissionControl.class);

    @Override
    @PostConstruct
    public void onInit_Annotated() {
        logger.info("@PostConstruct");
    }

    @Override
    @PreDestroy
    public void onDestroy_Annotated() {
        logger.info("@PreDestroy");
    }

    @Override
    public void onInit_JavaConfig() {
        logger.info("Init Javaconfig");
    }

    @Override
    public void onDestroy_JavaConfig() {
        logger.info("Destroy Javaconfig");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("Destroy interface");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Init interface");
    }
}
