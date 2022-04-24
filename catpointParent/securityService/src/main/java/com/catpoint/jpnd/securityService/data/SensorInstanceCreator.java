package com.catpoint.jpnd.securityService.data;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

public class SensorInstanceCreator implements InstanceCreator<Sensor> {
    private String name;
    private SensorType sensorType;
    private Set<Sensor> sensors = new TreeSet<>();

    SensorInstanceCreator(String vname,SensorType vsensorType){
        name = vname;
        sensorType = vsensorType;


    }

    @Override
    public Sensor createInstance(Type type) {

        Sensor sensor = new Sensor(name,sensorType);
        sensors.add(sensor);
        return sensor;
    }
}
