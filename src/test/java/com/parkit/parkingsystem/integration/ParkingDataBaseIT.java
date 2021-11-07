package com.parkit.parkingsystem.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

	private static final Logger logger = LogManager.getLogger("ParkingDataBaseIT");
	
    private static DataBaseConfig dataBaseConfig = new DataBaseConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    

    private static String vehiculeRegNumber = "ABCDEF";
    
    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseConfig;
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn(vehiculeRegNumber);
        Connection connection = null;
        try{
            connection = dataBaseConfig.getConnection();

            //set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();

            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
        	dataBaseConfig.closeConnection(connection);
        }
    }

    @AfterAll
    private static void tearDown(){

    }
    
    @Test
    public void testParkingACar(){
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            ResultSet ticketResult = con.prepareStatement("select * from ticket where VEHICLE_REG_NUMBER = \""+vehiculeRegNumber+"\"").executeQuery();
            
            boolean isTicketRegistered = false;
            boolean isVehiculeRegistered = false;
            
            if(ticketResult.next()){
            	isTicketRegistered = true;
            	ResultSet parkingResult = con.prepareStatement("select * from parking where PARKING_NUMBER = " + ticketResult.getInt(2)).executeQuery();
	               	
            	if(parkingResult.next() && !isVehiculeRegistered) {
	               		isVehiculeRegistered = (parkingResult.getInt(2) == 0); //making sure that it's considered used
	            }
            	dataBaseConfig.closeResultSet(parkingResult);
            }
            dataBaseConfig.closeResultSet(ticketResult);
            
            assertTrue(isTicketRegistered && isVehiculeRegistered);
            
        }catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
            assertTrue(false);
        }finally {
        	dataBaseConfig.closeConnection(con);
        }
    }
    
    @Test
    public void testParkingLotExit(){
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        
        Date inTime = new Date();
        inTime.setTime( System.currentTimeMillis() - (  60 * 60 * 1000) );
        Ticket ticket = ticketDAO.getTicket(vehiculeRegNumber.toString());
        ticket.setInTime(inTime);
        
        parkingService.processExitingVehicle();
        
        Connection con = null;
        try {
            con = dataBaseConfig.getConnection();
            ResultSet ticketResult = con.prepareStatement("select * from ticket where VEHICLE_REG_NUMBER = \""+vehiculeRegNumber+"\"").executeQuery();
            
            boolean isPriceRight = false;
            
            if(ticketResult.next()){
            	isPriceRight = ticketResult.getDouble(4) == ticket.getPrice();
            }

            assertTrue(isPriceRight);
            
        }catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
            assertTrue(false);
        }finally {
        	dataBaseConfig.closeConnection(con);
        }
    }
    @Test
    public void discountForRecurringUsers() throws Exception{
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        parkingService.processExitingVehicle();
        parkingService.processIncomingVehicle();
        assertTrue(ticketDAO.getTicket(vehiculeRegNumber.toString()).isRecuringMember());
    }
}
