package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;

@ExtendWith(MockitoExtension.class)
public class ParkingSpotTest {
	
    @Test
    public void variableIdTest(){
    	ParkingSpot parkingSpot = new ParkingSpot(0,ParkingType.CAR,true);
    	parkingSpot.setId(1);
    	
    	assertEquals(1, parkingSpot.getId());
    }
    
    @Test
    public void variableParkingTypeTest(){
    	ParkingSpot parkingSpot = new ParkingSpot(0,ParkingType.CAR,true);
    	parkingSpot.setParkingType(ParkingType.BIKE);
    	
    	assertEquals(ParkingType.BIKE, parkingSpot.getParkingType());
    }
    
    @Test
    public void variableAvailableTest(){
    	ParkingSpot parkingSpot = new ParkingSpot(0,ParkingType.CAR,true);
    	parkingSpot.setAvailable(false);
    	
    	assertEquals(false, parkingSpot.isAvailable());
    }
    
    @Test
    public void variableEqualsTest(){
    	ParkingSpot parkingSpotA = new ParkingSpot(0,ParkingType.CAR,true);
    	ParkingSpot parkingSpotB = new ParkingSpot(0,ParkingType.CAR,true);
    	ParkingSpot parkingSpotC = new ParkingSpot(1,ParkingType.CAR,true);
    	
    	assertTrue(parkingSpotA.equals(parkingSpotA));
    	assertTrue(parkingSpotA.equals(parkingSpotB));
    	assertFalse(parkingSpotA.equals(parkingSpotC));
    }
    @Test
    public void variableHashCodeTest(){
    	ParkingSpot parkingSpot = new ParkingSpot(0,ParkingType.CAR,true);
    	
    	assertEquals(0, parkingSpot.hashCode());
    }

}
