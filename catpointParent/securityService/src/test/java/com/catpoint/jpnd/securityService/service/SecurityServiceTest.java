package com.catpoint.jpnd.securityService.service;


import com.catpoint.jpnd.imageService.Service.ImageService;
import com.catpoint.jpnd.securityService.application.CatpointGui;
import com.catpoint.jpnd.securityService.data.*;
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
import java.lang.reflect.Method;
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

    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("System Alarm set to Alarm_Pending")
    @MethodSource("generator")
    public void alarm_ARMED_sensor_ACTIVATE_alarmStatus_No_ALARM_Set_AlarmStatusTo_Pending_ALARM(Sensor sensor,Boolean bool){

        //Mockito.when(securityRepository.getArmingStatus()).thenReturn(ARMED_HOME);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.NO_ALARM);

        //
        securityService.addSensor(sensor);
        securityService.changeSensorActivationStatus(sensor,bool);

        InOrder inOrder = Mockito.inOrder(securityRepository);
        inOrder.verify(securityRepository).setAlarmStatus(AlarmStatus.PENDING_ALARM);
        inOrder.verify(securityRepository).updateSensor(sensor);
        //Assertions.assertEquals(securityService.setAlarmStatus(),AlarmStatus.NO_ALARM);


    }

    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("System Alarm set to ALARM")
    @MethodSource("generator")
    public void alarm_ARMED_sensor_ACTIVATED_alarmStatus_PENDING_ALARM_Set_alarmStatusTo_ALARM(Sensor sensor,Boolean bool){

        Mockito.when(securityRepository.getArmingStatus()).thenReturn(ARMED_HOME);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);

        securityService.changeSensorActivationStatus(sensor,bool);

        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
    }

    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("System Alarm set to NO ALARM")
    @MethodSource("generator2nd")
    public void alarm_ARMED_sensor_DEACTIVATE_alarmStatus_PENDING_ALARM_Set_alarmStatusTO_NO_ALARM(Sensor sensor,Boolean bool){

        //Mockito.when(securityRepository.getArmingStatus()).thenReturn(ARMED_HOME);
        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);

        securityService.changeSensorActivationStatus(sensor,bool);

        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.NO_ALARM);
    }

    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("Sensor Activated while already active, set to ALARM")
    @MethodSource("generator")
    public void sensor_ACTIVATE_while_already_ACTIVE_and_alarmStatus_PENDING_ALARM_Set_alarmStatusTO_ALARM(Sensor sensor,Boolean bool){

        Mockito.when(securityRepository.getAlarmStatus()).thenReturn(AlarmStatus.PENDING_ALARM);
        sensor.setActive(Boolean.TRUE);
        securityService.changeSensorActivationStatus(sensor,bool);

        Mockito.verify(securityRepository).setAlarmStatus(AlarmStatus.ALARM);
    }

    @ParameterizedTest(name = "[{index}] {0} , {1}")
    @DisplayName("Sensor Deactivated while already inactive, do nothing")
    @MethodSource("generator2nd")
    public void sensor_DEACTIVATE_while_already_INACTIVE_DoNothing(Sensor sensor,Boolean bool){
        sensor.setActive(Boolean.FALSE);
        //Class<?> klass = securityService.getClass();
        //try {
            //Method method = klass.getDeclaredMethod("handleSensorDeactivated",SecurityService.class);
            //Assertions.assertTrue(method)
            //Mockito.verify(method.invoke("securityService"))
        //} catch (NoSuchMethodException e) {
            //e.printStackTrace();
        //}


        Assertions.assertTrue(securityService.changeSensorActivationStatus(sensor,bool) == );
        //Mockito.verify(s)
    }













    private static Stream<Arguments> generator() {

        Sensor sensor = new Sensor("testsensor", SensorType.DOOR);
        Sensor sensor1 = new Sensor("testsensor1", SensorType.DOOR);

        //sensor.setActive(Boolean.FALSE);
        return Stream.of(
                Arguments.of(sensor,Boolean.TRUE),
        Arguments.of(sensor1,Boolean.TRUE));
    }
    private static Stream<Arguments> generator2nd() {

        Sensor sensor = new Sensor("testsensor2", SensorType.DOOR);
        Sensor sensor1 = new Sensor("testsensor3", SensorType.DOOR);
        sensor.setActive(Boolean.TRUE);
        sensor1.setActive(Boolean.TRUE);
        return Stream.of(
                Arguments.of(sensor,Boolean.FALSE),
                Arguments.of(sensor1,Boolean.FALSE));
    }
}
