package com.parkit.parkingsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;

public class DataBaseConfigTest {

	/*
	 * Le but de DataBaseConfig est :
	 * -de contenir l'adresse de la BDD (actuellement constant)
	 * -de capturer les erreurs de :
	 * 		-*Connection->close()
	 *		-*PreparedStatement->close()
	 *		-*ResultSet->close()
	 *
	 * Aussi, en cas de dysfonctionnement, une erreur sera émise de 
	 * la classe DataBaseConfigTest elle même.
	 */
	@Test
	public void normalOperationTest() {
		DataBaseConfig dataBaseConfig = new DataBaseConfig();
		
		Connection con = null;
		try {
            con = dataBaseConfig.getConnection();
            
            PreparedStatement ps = con.prepareStatement("select * from parking;");
            ps.execute();
            ResultSet rs = ps.executeQuery();
            
            dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeResultSet(rs);
            
        }catch (Exception ex){assertTrue(false);}
		finally {
            dataBaseConfig.closeConnection(con);
        }
	}
}
