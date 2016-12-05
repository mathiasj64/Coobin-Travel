package org.medlab.reservation.webservices;

import org.medlab.reservation.entity.FlightInstance;
import org.medlab.reservation.webservices.response.FlightResponse;
import org.medlab.reservation.webservices.response.FlightsResponse;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class FlightsResponseBuilder {

    static Set<String> airlines = new LinkedHashSet<>();

    public static List<FlightsResponse> build(List<FlightInstance> flights) {

        List<FlightsResponse> flightsResponseList = new LinkedList<>();
        for (FlightInstance flight : flights) {
            if (flight.getFlight() != null && flight.getFlight().getAirline() != null &&
                    flight.getFlight().getAirline().getName() != null &&
                    !airlines.contains(flight.getFlight().getAirline().getName())) {
                airlines.add(flight.getFlight().getAirline().getName());
            }
        }
        if (!CollectionUtils.isEmpty(airlines)) {
            for (String airline : airlines) {
                List<FlightResponse> flightResponseList = new ArrayList<>();
                for (FlightInstance flight : flights) {
                    if (flight.getFlight() != null && flight.getFlight().getAirline() != null &&
                            flight.getFlight().getAirline().getName() != null &&
                            airline.equals(flight.getFlight().getAirline().getName())) {
                        flightResponseList.add(new FlightResponse(flight));
                    }
                }
                flightsResponseList.add(new FlightsResponse(airline, flightResponseList));
            }
        }

        return flightsResponseList;
    }
}
