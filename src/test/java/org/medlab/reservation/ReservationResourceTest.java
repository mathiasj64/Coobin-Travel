/*package org.medlab.reservation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.medlab.reservation.dao.FlightInstanceDao;
import org.medlab.reservation.dao.ReservationDao;
import org.medlab.reservation.entity.FlightInstance;
import org.medlab.reservation.entity.Reservation;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class ReservationResourceTest {

    private static ClassPathXmlApplicationContext springAppContext;

    ReservationDao reservationDao;
    FlightInstanceDao flightInstanceDao;

    FlightInstance flightInstance;
    String generatedId = "";

    @Before
    public void setup() throws Exception {
        springAppContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        reservationDao = (ReservationDao) springAppContext.getBean("reservationDaoImpl");
        flightInstanceDao = (FlightInstanceDao) springAppContext.getBean("flightInstanceDaoImpl");

        generatedId = UUID.randomUUID().toString();
        flightInstance = new FlightInstance();
        flightInstance.setFlightId(generatedId);
        flightInstance.setAvailableSeats(145);

        flightInstanceDao.create(flightInstance);

    }

    @Test
    public void testSaveReservation() {
        String requestJson = "{\"flightID\":\"" + generatedId + "\",\"numberOfSeats\":2,\"reserveeName\":\"Peter Hansen\",\"reserveePhone\":\"12345678\",\"reserveeEmail\":\"peter@peter.dk\",\"passengers\":[{\"firstName\":\"Peter\",\"lastName\":\"Peterson\"},{\"firstName\":\"Jane\",\"lastName\":\"Peterson\"}]}";

        given().contentType("application/json").
                body(requestJson).
                when().
                post("api/reservation/13").then().statusCode(200);
    }
    
    @After
    public void tearDown() {
        List reservations = reservationDao.findByFlightID(generatedId);
        if (!CollectionUtils.isEmpty(reservations)) {
            reservationDao.delete((Reservation) reservations.get(0));
        }
        flightInstanceDao.delete(flightInstance);
    }

}
*/
