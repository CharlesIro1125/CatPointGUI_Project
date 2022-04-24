package com.catpoint.jpnd.securityService.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import com.catpoint.jpnd.imageService.Service.ImageService;
import com.catpoint.jpnd.securityService.application.StatusListener;
import com.catpoint.jpnd.securityService.data.AlarmStatus;
import com.catpoint.jpnd.securityService.data.ArmingStatus;
import com.catpoint.jpnd.securityService.data.SecurityRepository;
import com.catpoint.jpnd.securityService.data.Sensor;
/**
 * Service that receives information about changes to the security system. Responsible for
 * forwarding updates to the repository and making any decisions about changing the system state.
 *
 * This is the class that should contain most of the business logic for our system, and it is the
 * class you will be writing unit tests for.
 */

public class SecurityService {

    private ImageService imageService;
    private SecurityRepository securityRepository;
    private Set<StatusListener> statusListeners = new HashSet<>();
    private Boolean catDetected = Boolean.FALSE;

    public SecurityService(SecurityRepository securityRepository, ImageService imageService) {
        this.securityRepository = securityRepository;
        this.imageService = imageService;
    }

    /**
     * Sets the current arming status for the system. Changing the arming status
     * may update both the alarm status.
     * @param armingStatus
     */
    public void setArmingStatus(ArmingStatus armingStatus) {
        if(armingStatus == ArmingStatus.DISARMED) {
            setAlarmStatus(AlarmStatus.NO_ALARM);
        }else if(armingStatus == ArmingStatus.ARMED_HOME){
            Set<Sensor> sensors = getSensors();
            for(Sensor sensor: sensors){
                sensor.setActive(Boolean.FALSE);
                if(!catDetected) {
                    setAlarmStatus(AlarmStatus.NO_ALARM);
                } else {setAlarmStatus(AlarmStatus.ALARM);}

            }
        } else if(armingStatus == ArmingStatus.ARMED_AWAY) {
            Set<Sensor> sensors = getSensors();
            for (Sensor sensor : sensors) {
                sensor.setActive(Boolean.FALSE);
                if(!ActiveSensors()) setAlarmStatus(AlarmStatus.NO_ALARM);
        }
    }
        securityRepository.setArmingStatus(armingStatus);
    }
    /**
     * Internal method that handles alarm status changes based on whether
     * the camera currently shows a cat.
     * @param cat True if a cat is detected, otherwise false.
     */
    private void catDetected(Boolean cat) {
        catDetected = cat;
        if(cat && getArmingStatus() == ArmingStatus.ARMED_HOME) {
            setAlarmStatus(AlarmStatus.ALARM);
        } else {
            if(!ActiveSensors()){
                setAlarmStatus(AlarmStatus.NO_ALARM);
            }
        }
        statusListeners.forEach(sl -> sl.catDetected(cat));
    }

    /**
     * Register the StatusListener for alarm system updates from within the SecurityService.
     * @param statusListener
     */
    public void addStatusListener(StatusListener statusListener) {
        statusListeners.add(statusListener);
    }

    public void removeStatusListener(StatusListener statusListener) {
        statusListeners.remove(statusListener);
    }

    /**
     * Change the alarm status of the system and notify all listeners.
     * @param status
     */
    public void setAlarmStatus(AlarmStatus status) {
        securityRepository.setAlarmStatus(status);
        statusListeners.forEach(sl -> sl.notify(status.getDescription(),status.getColor()));
    }
    /**
     * Internal method for updating the alarm status when a sensor has been activated.
     */
    private void handleSensorActivated() {
        if (securityRepository.getArmingStatus() == ArmingStatus.DISARMED) {
            return; //no problem if the system is disarmed
        } else if (securityRepository.getArmingStatus() == ArmingStatus.ARMED_HOME) {
            switch (securityRepository.getAlarmStatus()) {
                case NO_ALARM -> {
                    if (ActiveSensors() && !catDetected) setAlarmStatus(AlarmStatus.PENDING_ALARM);
                }
                case PENDING_ALARM -> {
                    if (ActiveSensors() && !catDetected) setAlarmStatus(AlarmStatus.ALARM);
                }
            }
        }else if(securityRepository.getArmingStatus() == ArmingStatus.ARMED_AWAY){
            switch (securityRepository.getAlarmStatus()) {
                case NO_ALARM -> {
                    if (ActiveSensors()) setAlarmStatus(AlarmStatus.PENDING_ALARM);
                }
                case PENDING_ALARM -> {
                    if (ActiveSensors()) setAlarmStatus(AlarmStatus.ALARM);
                }
            }
        }
    }
    private Boolean ActiveSensors(){
        return getSensors().stream().anyMatch(e -> e.getActive().equals(Boolean.TRUE));
    }
    /**
     * Internal method for updating the alarm status when a sensor has been deactivated
     */
    private void handleSensorDeactivated() {
        if(securityRepository.getArmingStatus() == ArmingStatus.ARMED_HOME) {
            switch (securityRepository.getAlarmStatus()) {
                case PENDING_ALARM -> {
                    if (!ActiveSensors() && !catDetected) setAlarmStatus(AlarmStatus.NO_ALARM);
                }

                case ALARM -> {
                    if (!ActiveSensors() && !catDetected) setAlarmStatus(AlarmStatus.PENDING_ALARM);
                }
            }
            if (!ActiveSensors() && !catDetected) {
                setAlarmStatus(AlarmStatus.NO_ALARM);
            }
        }else {
            switch (securityRepository.getAlarmStatus()) {
                case PENDING_ALARM -> {
                    if (!ActiveSensors()) setAlarmStatus(AlarmStatus.NO_ALARM);
                }

                case ALARM -> {
                    if (!ActiveSensors()) setAlarmStatus(AlarmStatus.PENDING_ALARM);
                }
            }
            if (!ActiveSensors()) {
                setAlarmStatus(AlarmStatus.NO_ALARM);
            }
        }
        }
    /**
     * Change the activation status for the specified sensor and update alarm status if necessary.
     * @param sensor
     * @param active
     */
    public void changeSensorActivationStatus(Sensor sensor, Boolean active) {

        if(!(sensor.getActive()) && !active){
            return;
        }
        if(!(sensor.getActive()) && active) {
            sensor.setActive(active);
            securityRepository.updateSensor(sensor);
            handleSensorActivated();
            //handleSensorActivated();
        } else if (sensor.getActive() && !active) {
            sensor.setActive(active);
            securityRepository.updateSensor(sensor);
            handleSensorDeactivated();
            //handleSensorDeactivated();
        } else if(sensor.getActive() && active){
            sensor.setActive(active);
            securityRepository.updateSensor(sensor);
            handleSensorActivated();
        }
    }

    /**
     * Send an image to the SecurityService for processing. The securityService will use its provided
     * ImageService to analyze the image for cats and update the alarm status accordingly.
     * @param currentCameraImage
     */
    public void processImage(BufferedImage currentCameraImage) {
        catDetected(imageService.imageContainsCat(currentCameraImage, 50.0f));
    }
    public String getAlarmStatusDescription(){
        AlarmStatus alarmStatus = getAlarmStatus();
        return alarmStatus.getDescription();
    }
    public Color getAlarmStatusColor(){
        AlarmStatus alarmStatus = getAlarmStatus();
        return alarmStatus.getColor();
    }
    public AlarmStatus getAlarmStatus() {
        return securityRepository.getAlarmStatus();
    }

    public Set<Sensor> getSensors() {
        return securityRepository.getSensors();
    }

    public void addSensor(Sensor sensor) {
        securityRepository.addSensor(sensor);
    }

    public void removeSensor(Sensor sensor) {
        securityRepository.removeSensor(sensor);
    }

    public ArmingStatus getArmingStatus() {
        return securityRepository.getArmingStatus();
    }
}

