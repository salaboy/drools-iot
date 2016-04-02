/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.pi4j.component.motor.Motor;
import com.pi4j.component.motor.MotorState;
import com.pi4j.component.temperature.TemperatureSensor;
import static org.drools.model.DSL.any;
import org.drools.model.Variable;
import org.drools.retebuilder.CanonicalKieBase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import static org.mockito.Mockito.*;
import static org.drools.model.DSL.*;
import org.drools.model.Rule;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author salaboy
 */
public class HardwareJUnitTest {

    public HardwareJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void WhenTemperatureIsHighTurnOffMotor() {
        CanonicalKieBase kieBase = new CanonicalKieBase();
        Variable<Motor> motorVariable = any(Motor.class);
        Variable<TemperatureSensor> tempSensorVariable = any(TemperatureSensor.class);

        Rule r1 = rule("When Temperature Is High Turn Off Motor")
                .view(
                        input(tempSensorVariable),
                        input(motorVariable),
                        expr(motorVariable, m -> !m.isStopped()),
                        expr(tempSensorVariable, (t) -> t.getTemperature() > 10)
                )
                .then(
                        on(motorVariable, tempSensorVariable)
                        .execute((drools, m, t) -> {
                            System.out.println("Turn off the motor " + m.getName());
                            m.stop();
                            drools.update(m);
                            drools.update(t);
                        })
                );
        
        Rule r2 = rule("When Temperature Is Low Turn On Motor")
                .view(
                        input(tempSensorVariable),
                        input(motorVariable),
                        expr(motorVariable, m -> m.isStopped()),
                        expr(tempSensorVariable, (t) -> t.getTemperature() < 10)
                )
                .then(
                        on(motorVariable, tempSensorVariable)
                        .execute((drools, m, t) -> {
                            System.out.println("Turn on the motor " + m.getName());
                            m.forward();
                            drools.update(m);
                            drools.update(t);
                        })
                        
                        
                );

        kieBase.addRules(r1, r2);

        KieSession ksession = kieBase.newKieSession();

        Motor motor = mock(Motor.class);
        TemperatureSensor tempSensor = mock(TemperatureSensor.class);

        when(motor.getState()).thenReturn(MotorState.FORWARD);

        when(motor.isStopped()).thenReturn(false);

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("Previous State = " + motor.getState());
                when(motor.getState()).thenReturn(MotorState.STOP);
                when(motor.isStopped()).thenReturn(true);
                when(tempSensor.getTemperature()).thenReturn(5.0);
                System.out.println("New State = " + motor.getState());
                return null;
            }
        }).when(motor).stop();
        
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.out.println("Previous State = " + motor.getState());
                when(motor.getState()).thenReturn(MotorState.FORWARD);
                when(motor.isStopped()).thenReturn(false);
                when(tempSensor.getTemperature()).thenReturn(15.0);
                System.out.println("New State = " + motor.getState());
                return null;
            }
        }).when(motor).forward();


        when(motor.getName()).thenReturn("Motor A");

        when(tempSensor.getTemperature()).thenReturn(15.0);

        ksession.insert(motor);

        ksession.insert(tempSensor);

        int fired = ksession.fireAllRules(2);

        System.out.println("Fired: " + fired);

    }
}
