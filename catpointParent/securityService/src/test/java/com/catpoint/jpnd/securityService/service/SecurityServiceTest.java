package com.catpoint.jpnd.securityService.service;


import com.catpoint.jpnd.imageService.Service.ImageService;
import com.catpoint.jpnd.securityService.application.CatpointGui;
import com.catpoint.jpnd.securityService.data.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito.*;
import org.mockito.stubbing.OngoingStubbing;
import org.mockito.verification.VerificationMode;

import javax.annotation.meta.When;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static com.catpoint.jpnd.securityService.data.ArmingStatus.*;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceTest {

    private SecurityService securityService;

    @Mock
    private ImageService imageservice;

    @Mock
    private SecurityRepository securityRepository;

    @BeforeAll
    static void initOnce(){
        //gui = new CatpointGuiTest();
        //gui.setVisible(true);


    }

    @BeforeEach
    void init(){
        securityService = new SecurityService(securityRepository, imageservice);
        //CatpointGuiTest gui = new CatpointGuiTest(securityService);
        //gui.setVisible(true);
        //securityService.setAlarmStatus(AlarmStatus.NO_ALARM);
        //securityService.setArmingStatus(DISARMED);
        //Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.NO_ALARM);
        //Mockito.when(securityRepository.getArmingStatus()).thenReturn(DISARMED);
    }

    @ParameterizedTest(name = "[{index}] {0} , {1}, {2} , {3}")
    @DisplayName("1. System Alarm set to Alarm_Pending")
    @MethodSource("generator")
    public void alarm_ARMED_sensor_ACTIVATE_alarmStatus_No_ALARM_Set_AlarmStatusTo_Pending_ALARM(Sensor sensor,Boolean bool,Set<Sensor> sensors,ArmingStatus armingStatus){

        Mockito.when(securityRepository.getArmingStatus()).thenReturn(armingStatus);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.NO_ALARM);
        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);


        //securityService.addSensor(sensor);
        securityService.changeSensorActivationStatus(sensor,bool);

        InOrder inOrder = Mockito.inOrder(securityRepository);
        //inOrder.verify(securityRepository).addSensor(sensor);
        inOrder.verify(securityRepository,Mockito.atLeast(1)).updateSensor(sensor);
        inOrder.verify(securityRepository).setAlarmStatus(AlarmStatus.PENDING_ALARM);

        //Assertions.assertEquals(securityService.setAlarmStatus(),AlarmStatus.NO_ALARM);


    }

    @ParameterizedTest(name = "[{index}] {0} , {1}, {2} , {3}")
    @DisplayName("2. System Alarm set to ALARM")
    @MethodSource("generator")
    public void alarm_ARMED_sensor_ACTIVATED_alarmStatus_PENDING_ALARM_Set_alarmStatusTo_ALARM(Sensor sensor,Boolean bool,Set<Sensor> sensors,ArmingStatus armingStatus){

        Mockito.when(securityRepository.getArmingStatus()).thenReturn(armingStatus);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);

        securityService.changeSensorActivationStatus(sensor,bool); // activate sensor

        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
    }

    @ParameterizedTest(name = "[{index}] {0} , {1}, {2} , {3}")
    @DisplayName("3. System Alarm set to NO ALARM")
    @MethodSource("generator01")
    public void all_sensors_INACTIVE_alarmStatus_PENDING_ALARM_Set_alarmStatusTO_NO_ALARM(Sensor sensor,Boolean bool,Set<Sensor> sensors,ArmingStatus armingStatus){

        Mockito.when(securityRepository.getArmingStatus()).thenReturn(armingStatus);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);

        sensor.setActive(Boolean.TRUE);
        securityService.changeSensorActivationStatus(sensor,bool); // make sensor inactive
        securityService.addSensor(sensor);

        Mockito.verify(securityRepository,Mockito.atMost(2)).setAlarmStatus(AlarmStatus.NO_ALARM);

    }
    @ParameterizedTest(name = "[{index}] {0} , {1} , {2} , {3}")
    @DisplayName("4. Alarm active deactivate one sensor doesn't affect the alarm")
    @MethodSource("generator2nd")
    public void alarm_ACTIVE_Change_in_sensor_State_Should_not_affect_Alarm_State(Sensor sensor,Boolean bool,Set<Sensor> sensors,ArmingStatus armingStatus){

        Mockito.when(securityRepository.getArmingStatus()).thenReturn(armingStatus);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.ALARM);
        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
        // All sensors are active
        securityService.changeSensorActivationStatus(sensor,bool); // deactivate 0ne sensor

        securityService.addSensor(sensor);

        Mockito.verify(securityRepository,Mockito.never()).setAlarmStatus(Mockito.any());
    }


    @ParameterizedTest(name = "[{index}] {0} , {1}, {2} , {3}")
    @DisplayName("5. Sensor Activated while already active, set to ALARM")
    @MethodSource("generator")
    public void sensor_ACTIVATE_while_already_ACTIVE_and_alarmStatus_PENDING_ALARM_Set_alarmStatusTO_ALARM(Sensor sensor,Boolean bool,Set<Sensor> sensors,ArmingStatus armingStatus){

        Mockito.when(securityRepository.getArmingStatus()).thenReturn(armingStatus);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);


        sensor.setActive(Boolean.TRUE); // already active
        securityService.changeSensorActivationStatus(sensor,bool); // activate sensor

        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
    }

    @ParameterizedTest(name = "[{index}] {0} , {1}, {2} , {3}")
    @DisplayName("6. Sensor Deactivated while already inactive, do nothing")
    @MethodSource("generator01")
    public void sensor_DEACTIVATE_while_already_INACTIVE_DoNothing(Sensor sensor,Boolean bool,Set<Sensor> sensors,ArmingStatus armingStatus){


        sensor.setActive(Boolean.FALSE);
        //Mockito.when(securityRepository.getArmingStatus()).thenReturn(armingStatus);
        securityService.changeSensorActivationStatus(sensor,bool);

        Mockito.verifyNoInteractions(securityRepository);
        Mockito.verify(securityRepository,Mockito.never()).getAlarmStatus();
        Mockito.verify(securityRepository,Mockito.never()).getArmingStatus();

        //Class<?> klass = securityService.getClass();
        //try {
            //Method method = klass.getDeclaredMethod("handleSensorDeactivated",SecurityService.class);
            //Assertions.assertTrue(method)
            //Mockito.verify(method.invoke("securityService"))
        //} catch (NoSuchMethodException e) {
            //e.printStackTrace();
        //}

    }
    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("7. Cat identified while system is Armed Home set to Alarm ")
    @MethodSource("generator3rd")
    public void cat_image_IDENTIFIED_system_ARMED_HOME_Set_alarmStatusTO_ALARM(BufferedImage bufferedImage,Set<Sensor> sensors){

        Mockito.when(imageservice.imageContainsCat(Mockito.any(),Mockito.anyFloat())).thenReturn(Boolean.TRUE);
        Mockito.when(securityRepository.getArmingStatus()).thenReturn(ARMED_HOME);


        securityService.processImage(bufferedImage);

        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);

    }

    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("8. No cat identified and sensors not active set to NO ALARM")
    @MethodSource("generator3rd")
    public void NO_CAT_IDENTIFIED_sensors_NOT_ACTIVE_SetTO_NO_ALARM(BufferedImage bufferedImage,Set<Sensor> sensors){

        Mockito.when(imageservice.imageContainsCat(Mockito.any(),Mockito.anyFloat())).thenReturn(Boolean.FALSE);
        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
        //Mockito.when(securityRepository.getArmingStatus()).thenReturn(ARMED_HOME);

        securityService.processImage(bufferedImage);

        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);

    }

    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("9. Sensor Disarmed set Alarm status to NO ALARM")
    @MethodSource("generator4th")
    public void system_DISARMED_Set_all_AlarmStatusTO_NO_ALARM(ArmingStatus armingStatus,ArmingStatus armingStatus1){

        securityService.setArmingStatus(armingStatus);


        InOrder inOrder = Mockito.inOrder(securityRepository);
        inOrder.verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
        inOrder.verify(securityRepository).setArmingStatus(DISARMED);
    }

    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("10. System Armed reset all sensors to INACTIVE")
    @MethodSource("generator5th")
    public void system_ARMED_Reset_all_SensorsTo_INACTIVE(Set<Sensor> sensors,ArmingStatus armingStatus1){
        // all sensors are active
        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);
        //for(Sensor sen : sensors){
            //System.out.println("before passing for verifying " + sen.getActive() );
        //}


        securityService.setArmingStatus(armingStatus1);  // system armed


        Assertions.assertTrue(securityService.getSensors().stream()
                .allMatch(e-> e.getActive() == Boolean.FALSE));

        //for(Sensor sen : sensors){
            //System.out.println("after passing and verifying " + sen.getActive() );
        //}


    }
    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("11. Cat identified system Armed Home, set Alarm status to Alarm")
    @MethodSource("generator3rd")
    public void system_ARMED_HOME_cat_IDENTIFIED_Set_AlarmStatusTO_ALARM(BufferedImage bufferedImage,Set<Sensor> sensors){
        Mockito.when(imageservice.imageContainsCat(Mockito.any(),Mockito.anyFloat())).thenReturn(Boolean.TRUE);
        Mockito.when(securityRepository.getArmingStatus()).thenReturn(ARMED_HOME);
        Mockito.when(securityRepository.getSensors()).thenReturn(sensors);

        securityService.processImage(bufferedImage);  // cat detected
        securityService.setArmingStatus(ARMED_HOME); // system armed

        Mockito.verify(securityRepository,Mockito.atLeast(2)).setAlarmStatus(AlarmStatus.ALARM);

    }
















    private static Stream<Arguments> generator() {
        Set<Sensor> sensorSet = new HashSet<Sensor>();
        Set<Sensor> sensorSet1 = new HashSet<Sensor>();
        Sensor sensor = new Sensor("testsensor", SensorType.DOOR);
        Sensor sensor1 = new Sensor("testsensor1", SensorType.DOOR);
        ArmingStatus armingStatus = ARMED_HOME;
        ArmingStatus armingStatus1 = ARMED_AWAY;
        sensorSet.add(sensor);
        sensorSet.add(sensor1);
        sensorSet1.add(sensor1);
        sensorSet1.add(sensor);


        return Stream.of(
                Arguments.of(sensor,Boolean.TRUE,sensorSet,armingStatus),
        Arguments.of(sensor1,Boolean.TRUE,sensorSet1,armingStatus1));
    }
    private static Stream<Arguments> generator01() {
        Set<Sensor> sensorSet = new HashSet<Sensor>();
        Set<Sensor> sensorSet1 = new HashSet<Sensor>();
        Sensor sensor = new Sensor("testsensor2", SensorType.DOOR);
        Sensor sensor1 = new Sensor("testsensor3", SensorType.DOOR);
        ArmingStatus armingStatus = ARMED_HOME;
        ArmingStatus armingStatus1 = ARMED_AWAY;
        sensorSet.add(sensor);
        sensorSet.add(sensor1);
        sensorSet1.add(sensor1);
        sensorSet1.add(sensor);
        return Stream.of(
                Arguments.of(sensor,Boolean.FALSE,sensorSet,armingStatus),
                Arguments.of(sensor1,Boolean.FALSE,sensorSet1,armingStatus1));
    }
    private static Stream<Arguments> generator2nd() {
        Set<Sensor> sensorSet = new HashSet<Sensor>();
        Set<Sensor> sensorSet1 = new HashSet<Sensor>();
        Sensor sensor = new Sensor("testsensor2", SensorType.DOOR);
        Sensor sensor1 = new Sensor("testsensor3", SensorType.DOOR);
        Sensor sensor2 = new Sensor("testsensor4", SensorType.DOOR);
        ArmingStatus armingStatus = ARMED_HOME;
        ArmingStatus armingStatus1 = ARMED_AWAY;
        sensor.setActive(Boolean.TRUE);
        sensor1.setActive(Boolean.TRUE);
        sensor2.setActive(Boolean.TRUE);
        sensorSet.add(sensor);
        sensorSet.add(sensor1);
        sensorSet.add(sensor2);
        sensorSet1.add(sensor1);
        sensorSet1.add(sensor);
        sensorSet1.add(sensor2);
        return Stream.of(
                Arguments.of(sensor,Boolean.FALSE,sensorSet,armingStatus),
                Arguments.of(sensor1,Boolean.FALSE,sensorSet1,armingStatus1));
    }

    private static Stream<Arguments> generator3rd(){
        Set<Sensor> sensorSet = new HashSet<Sensor>();
        Set<Sensor> sensorSet1 = new HashSet<Sensor>();
        Sensor sensor = new Sensor("testsensor2", SensorType.DOOR);
        Sensor sensor1 = new Sensor("testsensor3", SensorType.DOOR);
        sensorSet.add(sensor);
        sensorSet.add(sensor1);
        sensorSet1.add(sensor1);
        sensorSet1.add(sensor);
        BufferedImage bufferedImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_RGB);
        BufferedImage bufferedImage1 = new BufferedImage(30,30,BufferedImage.TYPE_INT_RGB);


        return Stream.of(
                Arguments.of(bufferedImage,sensorSet),
                Arguments.of(bufferedImage1,sensorSet1)
        );
    }
    private static Stream<Arguments> generator4th(){
        ArmingStatus armingStatus = DISARMED;
        ArmingStatus armingStatus1 = DISARMED;
        ArmingStatus armingStatus2 = ARMED_AWAY;
        ArmingStatus armingStatus3 = ARMED_HOME;

        return Stream.of(Arguments.of(armingStatus,armingStatus2),
                Arguments.of(armingStatus1,armingStatus3));
    }

    private static Stream<Arguments> generator5th(){
        Set<Sensor> sensorSet = new HashSet<Sensor>();
        Set<Sensor> sensorSet2 = new HashSet<Sensor>();
        Sensor sensor = new Sensor("testsensor", SensorType.DOOR);
        Sensor sensor1 = new Sensor("testsensor1", SensorType.DOOR);
        Sensor sensor2 = new Sensor("testsensor", SensorType.DOOR);
        Sensor sensor3 = new Sensor("testsensor1", SensorType.DOOR);
        ArmingStatus armingStatus2 = ARMED_AWAY;
        ArmingStatus armingStatus3 = ARMED_HOME;
        sensor.setActive(Boolean.TRUE);
        sensor1.setActive(Boolean.TRUE);
        sensor2.setActive(Boolean.TRUE);
        sensor3.setActive(Boolean.TRUE);

        sensorSet.add(sensor);
        sensorSet.add(sensor1);
        sensorSet2.add(sensor2);
        sensorSet2.add(sensor3);

        return Stream.of(Arguments.of(sensorSet,armingStatus2),
                Arguments.of(sensorSet2,armingStatus3));
    }
}
