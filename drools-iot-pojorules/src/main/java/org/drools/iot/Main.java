/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drools.iot;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.component.temperature.impl.Tmp102;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import java.io.IOException;
import org.drools.retebuilder.CanonicalKieBase;
import org.kie.api.runtime.KieSession;

/**
 *
 * @author salaboy
 */
public class Main {
    public static void main(String[] args) throws IOException {
        CanonicalKieBase kieBase = new CanonicalKieBase();
//        kieBase.addRules(toModule(HardwareModule.class));

        KieSession ksession = kieBase.newKieSession();
        GpioPinDigitalOutput[] pins = null;
        
        ksession.insert(new GpioStepperMotorComponent(pins));
        
        ksession.insert(new Tmp102(0, 0));
        
        int fired = ksession.fireAllRules();
        
        System.out.println("Fired: "+ fired);
        
    }
}
