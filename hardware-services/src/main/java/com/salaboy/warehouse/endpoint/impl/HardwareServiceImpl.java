package com.salaboy.warehouse.endpoint.impl;

import com.salaboy.warehouse.endpoint.exception.BusinessException;
import com.salaboy.warehouse.model.Sensor;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;


import com.salaboy.warehouse.endpoint.api.HardwareService;
import com.salaboy.warehouse.model.Actuator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class HardwareServiceImpl implements HardwareService {
    public enum ServiceStatus{ STOPPED, STARTED, STARTING, FAILED };
    private Map<String, Sensor> sensors = new HashMap<String, Sensor>(10);
    private Map<String, Actuator> actuators = new HashMap<String,Actuator>(10);
    
    

    @Context
    SecurityContext context;

    public HardwareServiceImpl() {
    }

//    @Override
//    public void newItem(Sensor item) throws BusinessException {
//        KeycloakPrincipal principal = (KeycloakPrincipal) context.getUserPrincipal();
//        if (principal != null && principal.getKeycloakSecurityContext() != null) {
//            items.add(item);
//        } else {
//            throw new BusinessException("You don't have the appropriate permession to access this service");
//        }
//    }

    @Override
    public List<Sensor> getSensors() throws BusinessException {
        return new ArrayList<Sensor>(sensors.values());
    }

    @Override
    public List<Actuator> getActuators() throws BusinessException {
        return new ArrayList<Actuator>(actuators.values());
    }

    @Override
    public void start() throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void restart() throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Actuator getActuator(String id) throws BusinessException {
        return actuators.get(id);
    }

    @Override
    public Sensor getSensor(String id) throws BusinessException {
        return sensors.get(id);
    }

    
    

}
