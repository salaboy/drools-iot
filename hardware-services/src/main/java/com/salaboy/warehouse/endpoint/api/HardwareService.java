/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salaboy.warehouse.endpoint.api;

import com.salaboy.warehouse.endpoint.exception.BusinessException;
import com.salaboy.warehouse.model.Actuator;
import com.salaboy.warehouse.model.Sensor;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author salaboy
 */
@Path("hardware")
public interface HardwareService {

    @GET
    @Produces("application/json")
    @Path("/sensors/")
    public List<Sensor> getSensors() throws BusinessException;

    @POST
    @Path("/actuators/")
    @Produces("application/json")
    public List<Actuator> getActuators() throws BusinessException;
    
    @POST
    @Path("/start")
    public void start() throws BusinessException;
    
    @POST
    @Path("/restart")
    public void restart() throws BusinessException;
    
    @POST
    @Path("/stop")
    public void stop() throws BusinessException;

    @GET
    @Produces("application/json")
    @Path("/actuators/{id}")
    public Actuator getActuator(@PathParam("id") @NotEmpty @NotNull String id) throws BusinessException;

    @GET
    @Produces("application/json")
    @Path("/sensors/{id}")
    public Sensor getSensor(@PathParam("id") @NotEmpty @NotNull String id) throws BusinessException;
}
