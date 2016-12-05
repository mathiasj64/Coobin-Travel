package org.medlab.reservation;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.medlab.reservation.webservices.FlightResource;
import org.medlab.reservation.webservices.ReservationResource;


public class JsonApplication extends ResourceConfig {

    public JsonApplication(){
        super(
                FlightResource.class,
                ReservationResource.class,
                JacksonFeature.class,
                FlightResource_FROM_OTHER_API.class
        );
    }

}
