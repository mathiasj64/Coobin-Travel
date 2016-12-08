package org.medlab.reservation.webservices;

import org.apache.commons.lang.StringUtils;
import org.medlab.reservation.dao.FlightDao;
import org.medlab.reservation.dao.FlightInstanceDao;
import org.medlab.reservation.entity.Airline;
import org.medlab.reservation.entity.Airport;
import org.medlab.reservation.entity.Flight;
import org.medlab.reservation.entity.FlightInstance;
import org.medlab.reservation.exceptions.IllegalInputException;
import org.medlab.reservation.exceptions.NoFlightsException;
import org.medlab.reservation.exceptions.NoTicketsException;
import org.medlab.reservation.exceptions.UnknownErrorException;
import org.medlab.reservation.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/flights")
public class FlightResource {

    @Autowired
    FlightDao flightDao;
    @Autowired
    FlightInstanceDao flightInstanceDao;

    @GET
    @Path("{from}/{date}/{tickets}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAvailableFlightsFromAndSince(@PathParam("from") String from,
                                                    @PathParam("date") String date,
                                                    @PathParam("tickets") Integer tickets) {

        if (StringUtils.isEmpty(from) || StringUtils.isEmpty(date) || tickets == null) {
            throw new IllegalInputException();
        }
        Long time;
        try {
            time = DateUtils.dateStringToLong(date);
        } catch (Exception e) {
            throw new IllegalInputException();
        }
        List<FlightInstance> flights;
        try {
            flights = flightInstanceDao.getAllFlightsByAirportSince(from, time);
        } catch (Exception e) {
            throw new UnknownErrorException();
        }
        if (CollectionUtils.isEmpty(flights)) {
            throw new NoFlightsException();
        }
        ArrayList toRemove = new ArrayList();
        for (FlightInstance flight : flights) {
            if (flight.getAvailableSeats() < tickets) {
                toRemove.add(flight);
            }
        }
        flights.removeAll(toRemove);
        if (CollectionUtils.isEmpty(flights)) {
            throw new NoTicketsException();
        }

        List flightsResponse = FlightsResponseBuilder.build(flights);
        System.out.println("list2" + flightsResponse);
        return Response.status(200).entity(flightsResponse).build();
    }

    @GET
    @Path("{from}/{to}/{date}/{tickets}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAvailableFlightsFromToAndSince(@PathParam("from") String from,
                                                      @PathParam("to") String to,
                                                      @PathParam("date") String date,
                                                      @PathParam("tickets") Integer tickets) {

        if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to) ||
                StringUtils.isEmpty(date) || tickets == null) {
            throw new IllegalInputException();
        }
        Long time;
        try {
            time = DateUtils.dateStringToLong(date);
        } catch (Exception e) {
            throw new IllegalInputException();
        }
        List<FlightInstance> flights;
        try {
            flights = flightInstanceDao.getAllFlightsByAirportsSince(from, to, time);
        } catch (Exception e) {
            throw new UnknownErrorException();
        }
        if (CollectionUtils.isEmpty(flights)) {
            throw new NoFlightsException();
        }
        ArrayList toRemove = new ArrayList();
        for (FlightInstance flight : flights) {
            if (flight.getAvailableSeats() < tickets) {
                toRemove.add(flight);
            }
        }
        flights.removeAll(toRemove);
        if (CollectionUtils.isEmpty(flights)) {
            throw new NoTicketsException();
        }

        List flightsResponse = FlightsResponseBuilder.build(flights);
        
        String a = flightsResponse.get(0).toString();
                System.out.println("list2" + flightsResponse);
                

        return Response.status(200).entity(flightsResponse).build();
    }

    @GET
    @Path("create")
    @Produces({MediaType.APPLICATION_JSON})
    @Transactional
    public Response save() {

        Airline airline = new Airline();
        airline.setName("Coobin Airline");

        Airport airport = new Airport();
        airport.setIATACode("CPH");
        airport.setName("Copenhagen");

        Airport airport2 = new Airport();
        airport2.setIATACode("SXF");
        airport2.setName("Berlin");

        Flight flight = new Flight();
        flight.setFlightNumber("Cyka1337");
        flight.setOrigin(airport);
        flight.setDestination(airport2);
        flight.setAirline(airline);

        FlightInstance flightInstance = new FlightInstance();
        flightInstance.setFlightId("00FZ5");
        flightInstance.setAvailableSeats(145);
        flightInstance.setFlight(flight);
        flightInstance.setDate(1514678400000L);

        FlightInstance flight1 = flightInstanceDao.create(flightInstance);

        return Response.status(200).entity(flight1).build();
    }

}
