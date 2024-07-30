package indigo.hacktohire.app.services;

import indigo.hacktohire.app.entities.Booking;
import indigo.hacktohire.app.entities.Flight;
import indigo.hacktohire.commons.DTOs.EmailObject;
import indigo.hacktohire.commons.DTOs.FlightStatus;
import indigo.hacktohire.commons.DTOs.FlightStatusObject;
import indigo.hacktohire.commons.DTOs.WhatsappObject;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class Utility {

    String toMD5(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    List<EmailObject> convertBookingListToPassengersEmailObjectList(List<Booking> bookingList, FlightStatus flightStatus, String flightStatusUpdatedTo, String customMessage){
        List<EmailObject> emailObjectsList = new ArrayList<>();
        for(Booking booking : bookingList){
            EmailObject emailObject = new EmailObject();
            emailObject.setFlightStatus(flightStatus);

            emailObject.setPnrNumber(booking.getPnr());
            emailObject.setRecipientName(booking.getFirstName() +" "+booking.getLastName());
            emailObject.setRecipientEmail(booking.getEmail());

            emailObject.setFlightDate(Date.from(booking.getFlight().getDepartureDateTime().atZone(ZoneId.systemDefault()).toInstant()).toString());
            emailObject.setFlightStatusUpdatedTo(flightStatusUpdatedTo);
            emailObject.setFlightNumber(booking.getFlight().getFlightNumber());
            emailObject.setFlightRoute("from "+booking.getFlight().getDepartureAirport()+" to "+booking.getFlight().getArrivalAirport());

            emailObject.setCustomMessage(customMessage);
            emailObjectsList.add(emailObject);
        }
        return emailObjectsList;
    }

    List<WhatsappObject> convertBookingListToPassengersWhatsappObjectList(List<Booking> bookingList, FlightStatus flightStatus, String flightStatusUpdatedTo, String customMessage){
        List<WhatsappObject> whatsappObjectList = new ArrayList<>();
        for(Booking booking : bookingList){
            WhatsappObject whatsappObject = new WhatsappObject();
            whatsappObject.setFlightStatus(flightStatus);

            whatsappObject.setPnrNumber(booking.getPnr());
            whatsappObject.setRecipientName(booking.getFirstName() +" "+booking.getLastName());
            whatsappObject.setRecipientNumber(booking.getPhoneNumber());

            whatsappObject.setFlightDate(Date.from(booking.getFlight().getDepartureDateTime().atZone(ZoneId.systemDefault()).toInstant()).toString());
            whatsappObject.setFlightStatusUpdatedTo(flightStatusUpdatedTo);
            whatsappObject.setFlightNumber(booking.getFlight().getFlightNumber());
            whatsappObject.setFlightRoute("from "+booking.getFlight().getDepartureAirport()+" to "+booking.getFlight().getArrivalAirport());

            whatsappObject.setCustomMessage(customMessage);

            whatsappObjectList.add(whatsappObject);
        }
        return whatsappObjectList;
    }

    List<FlightStatusObject> convertFlightListToFlightStatusObjectList(List<Flight> flightList){
        List<FlightStatusObject> flightStatusObjectList = new ArrayList<>();
        for(Flight flight : flightList){
            FlightStatusObject flightStatusObject = new FlightStatusObject();
            flightStatusObject.setTime(Time.from(flight.getDepartureDateTime().atZone(ZoneId.systemDefault()).toInstant()).toString());
            flightStatusObject.setFlightNumber(flight.getFlightNumber());
            flightStatusObject.setAirline(flight.getAirline().getName());
            flightStatusObject.setLogo_url(flight.getAirline().getLogo());
            flightStatusObject.setDestination(flight.getDepartureAirport());
            flightStatusObject.setGate(flight.getDepartureGate() != null ? flight.getDepartureGate() : "-");
            flightStatusObject.setStatus(flight.getFlightStatus().toString());
            flightStatusObjectList.add(flightStatusObject);
        }
        return flightStatusObjectList;
    }

}
