// package org.medlab.reservation;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.medlab.reservation.dao.FlightInstanceDao;
//import org.medlab.reservation.entity.Airline;
//import org.medlab.reservation.entity.Airport;
//import org.medlab.reservation.entity.Flight;
//import org.medlab.reservation.entity.FlightInstance;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import static io.restassured.RestAssured.given;
//
//public class FlightResourceTest {
//
//    private static ClassPathXmlApplicationContext springAppContext;
//
//    private static FlightInstanceDao flightInstanceDao;
//
//    private FlightInstance flight1;
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//        springAppContext =
//                new ClassPathXmlApplicationContext("applicationContext.xml");
//        flightInstanceDao = (FlightInstanceDao) springAppContext.getBean("flightInstanceDaoImpl");
//
//    }
//
//    @Before
//    public void setup() {
//        Airline airline = new Airline();
//        airline.setName("American Airlines");
//
//        Airport airport = new Airport();
//        airport.setIATACode("NYC");
//        airport.setName("New York international Airport");
//
//        Airport airport2 = new Airport();
//        airport2.setIATACode("CHI");
//        airport2.setName("Chicago international Airport");
//
//        Flight flight = new Flight();
//        flight.setFlightNumber("QE1235");
//        flight.setOrigin(airport);
//        flight.setDestination(airport2);
//        flight.setAirline(airline);
//
//        FlightInstance flightInstance = new FlightInstance();
//        flightInstance.setFlightId("00ee4");
//        flightInstance.setAvailableSeats(145);
//        flightInstance.setFlight(flight);
//        flightInstance.setDate(1480118400000L); // 2016-11-26
//
//        flight1 = flightInstanceDao.create(flightInstance);
//    }
//
//    @Test
//    public void testGetAvailableFlightsFromAndSince() {
//        given().when().get("api/flights/NYC/2016-11-26Z/123").then().statusCode(200);
//    }
//
//    @Test
//    public void testGetAvailableFlightsFromToAndSince() {
//        given().when().get("api/flights/NYC/CHI/2016-11-26Z/123").then().statusCode(200);
//    }
//
//    @After
//    public void tearDown() {
//        flightInstanceDao.delete(flight1);
//    }
//
//}
