package com.carbon_it.space.missioncontrol;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// TODO 3 : add initializing bean hook
// TODO 4 : add destroying bean hook
public class CustomMissionControl implements MissionControl, InitializingBean, DisposableBean {

    // TODO 1 : use jakarta annotations to hook onInit_Annotated
    @Override
    @PostConstruct
    public void onInit_Annotated() {

    }

    // TODO 2 : use jakarta annotations to hook onDestroy_Annotated
    @Override
    @PreDestroy
    public void onDestroy_Annotated() {

    }

    @Override
    public void afterPropertiesSet(){

    }

    @Override
    public void destroy() {

    }


    @Override
    public void onInit_JavaConfig() {

    }

    @Override
    public void onDestroy_JavaConfig() {

    }
}
