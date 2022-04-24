package com.catpoint.jpnd.securityService.application;

import com.catpoint.jpnd.securityService.data.AlarmStatus;

import java.awt.*;

/**
 * Identifies a component that should be notified whenever the system status changes
 */
public interface StatusListener {

    void notify(String description, Color color);
    void catDetected(boolean catDetected);
    void sensorStatusChanged();
}




