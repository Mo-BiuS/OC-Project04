package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;

@ExtendWith(MockitoExtension.class)
public class TicketTest {
    @Test
    public void variableIdTest(){
    	
    	Ticket ticket = new Ticket();
    	int id = 0;
    	
    	ticket.setId(id);
    	assertEquals(id, ticket.getId());
    }
    
    @Test
    public void variableInTimeTest(){
    	Ticket ticket = new Ticket();
    	Date inTime = new Date(System.currentTimeMillis());
    	
    	ticket.setInTime(inTime);
    	assertEquals(inTime, ticket.getInTime());
    }
    
    @Test
    public void variableOutTimeTest(){
    	Ticket ticket = new Ticket();
    	Date outTime = new Date(System.currentTimeMillis());
    	
    	
    	ticket.setOutTime(outTime);
    	assertEquals(outTime, ticket.getOutTime());
    }
    
    @Test
    public void variableParkingSpotTest(){
    	Ticket ticket = new Ticket();
    	ParkingSpot parkingSpot = new ParkingSpot(0, ParkingType.CAR, true);
    	
    	ticket.setParkingSpot(parkingSpot);
    	assertEquals(parkingSpot, ticket.getParkingSpot());
    }
    
    @Test
    public void variablePriceTest(){
    	Ticket ticket = new Ticket();
    	double price = 0.5;
    	
    	ticket.setPrice(price);
    	assertEquals(price, ticket.getPrice());
    }
    
    @Test
    public void variableRecuringMemberTest(){
    	Ticket ticket = new Ticket();
    	boolean recuringMember = true;
    	
    	ticket.setRecuringMember(recuringMember);
    	assertEquals(recuringMember, ticket.isRecuringMember());
    }
    
    @Test
    public void variableVehiculeRegNumberTest(){
    	Ticket ticket = new Ticket();
    	String vehiculeRegNumber = "test";
    	
    	ticket.setVehicleRegNumber(vehiculeRegNumber);
    	assertEquals(vehiculeRegNumber, ticket.getVehicleRegNumber());
    }
}
