package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Date;

import javax.sql.DataSource;

import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.naming.Context;

import java.util.Calendar;

import logic.Log;

/**
 *
 * @author mfran
 */
public class ConnectionDDBB {
    
    public ConnectionDDBB() {
        
    }
    
    public Connection obtainConnection(boolean autoCommit) throws NullPointerException {
        Connection con = null;
        int intentos = 5;
        
        for (int i = 0; i < intentos; i++) {
            Log.logdb.info("Attempt {} to connect to the database", i);
            
            try {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/jafe");     //Get the connection factory configured in Tomcat

                con = ds.getConnection();                                               //Obtiene una conexion
                Calendar calendar = Calendar.getInstance();
                Date date = new Date(calendar.getTime().getTime());

                Log.logdb.debug("Connection creation. BD connection identifier: {} obtained in {}", con.toString(), date.toString());
                con.setAutoCommit(autoCommit);
                Log.logdb.info("Connection obtained in the attempt: " + i);
                i = intentos;
                
            } catch(NamingException nx) {
                Log.logdb.error("Error getting connection while trying: {} = {}", i, nx);
            } catch (SQLException sqle) {
                Log.logdb.error("Error sql getting connection while trying: {} = {}", i, sqle);
                throw (new NullPointerException("SQL connection is null"));
            }
        }
        return con;
    }
    
    public void closeTransaction(Connection con)
    {
        try {
            con.commit();
            Log.logdb.debug("Transaction closed");
            
        } catch (SQLException sqle) {
            Log.logdb.error("Error closing the transaction: {}", sqle);
        }
    }
    
    public void cancelTransaction(Connection con) {
        try {
            con.rollback();
            Log.logdb.debug("Transaction canceled");
            
        } catch (SQLException sqle) {
            Log.logdb.error("Error sql when canceling the transaction: {}", sqle);
        }
    }
    
    public void closeConnection(Connection con) {
        try {
            Log.logdb.info("Closing the connection");
            
            if (con != null) {
                Calendar calendar = Calendar.getInstance();
                Date date = new Date(calendar.getTime().getTime());
                Log.logdb.debug("Connection closed. BD connection identifier: {} obtained in {}", con.toString(), date.toString());
                con.close();
            }
            
            Log.logdb.info("The connection has been closed");
            
        } catch (SQLException sqle) {
            Log.logdb.error("Error sql closing the connection: {}", sqle);
        }
    }
    
    public static PreparedStatement getStatement(Connection con, String sql) {
        PreparedStatement ps = null;
        
        try {
            if (con != null) {
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            }
            
        } catch (SQLException sqle) {
            Log.logdb.warn("Error sql creating PreparedStatement: {}", sqle);
        }
        
        return ps;
    }
    
    
    //-------------------- CALLS TO THE DATABASE -----------------------------
    
    public static PreparedStatement GetUsers(Connection con) {
        return getStatement(con, "SELECT * FROM USER;");
    }
    
    public static PreparedStatement GetPets(Connection con) {
        return getStatement(con, "SELECT * FROM PET;");
    }
    
    public static PreparedStatement GetFeeders(Connection con) {
        return getStatement(con, "SELECT * FROM FEEDER;");
    }
    
    public static PreparedStatement GetSchedules(Connection con) {
        return getStatement(con, "SELECT * FROM RATION;");
    }
    
    public static PreparedStatement GetRecords(Connection con) {
        return getStatement(con, "SELECT * FROM RECORD;");
    }
    
    public static PreparedStatement GetUserID(Connection con) {
        return getStatement(con, "SELECT * FROM USER WHERE ID_USER = ?;");
    }
    
    public static PreparedStatement GetUserPassword(Connection con) {
        return getStatement(con, "SELECT ID_USER, PASSWORD FROM USER WHERE EMAIL = ?;");
    }
    
    public static PreparedStatement GetFeederByID(Connection con) {
        return getStatement(con, "SELECT * FROM FEEDER WHERE ID_FEEDER = ?;");
    }
    
    public static PreparedStatement GetPetUser(Connection con) {
        return getStatement(con, "SELECT * FROM PET WHERE ID_USER = ?;");
    }
    
    public static PreparedStatement GetUserFeeder(Connection con) {
        return getStatement(con, "SELECT * FROM FEEDER WHERE ID_USER = ?;");
    }
    
    public static PreparedStatement GetRecordsFeeder(Connection con) {
        return getStatement(con, "SELECT * FROM RECORD WHERE ID_FEEDER = ?;");
    }
    
    public static PreparedStatement GetRecordsPortion(Connection con) {
        return getStatement(con, "SELECT ID_RECORD, DATER, TIMER, VALUE, ID_RATION, ID_SENSOR, RECORD.ID_FEEDER FROM RECORD INNER JOIN FEEDER\n" +
            "ON RECORD.ID_FEEDER = FEEDER.ID_FEEDER INNER JOIN USER ON FEEDER.ID_USER = USER.ID_USER\n" +
            "WHERE USER.ID_USER = ? AND RECORD.ID_RATION = ? AND RECORD.DATER = ? AND RECORD.ID_SENSOR = ?;");
    }
    
    public static PreparedStatement GetStatusPetByUser(Connection con) {
        return getStatement(con, "SELECT STATUS FROM PET INNER JOIN USER ON PET.ID_USER = USER.ID_USER WHERE USER.ID_USER = ?;");
    }
    
    public static PreparedStatement GetPercentageFood(Connection con) {
        return getStatement(con, "SELECT PERCENTAGE_FOOD FROM FEEDER INNER JOIN USER ON FEEDER.ID_USER = USER.ID_USER WHERE USER.ID_USER = ?;");
    }
    
    public static PreparedStatement GetScheduleUser(Connection con) {
        return getStatement(con, "SELECT * FROM RATION WHERE id_feeder = ?;");
    }
    
    public static PreparedStatement GetRationsByIdUser(Connection con) {
        return getStatement(con, "SELECT ID_RATION, TIMEP, WEIGHTG, RATION.ID_FEEDER FROM RATION INNER JOIN FEEDER "
            + "ON RATION.ID_FEEDER = FEEDER.ID_FEEDER INNER JOIN USER ON FEEDER.ID_USER = USER.ID_USER WHERE USER.ID_USER = ?;");
    }
    
    public static PreparedStatement InsertNewUser(Connection con) {
        return getStatement(con, "INSERT INTO USER (ID_USER, NAME, FIRST_SURNAME, SEC_SURNAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?, ?, ?);");
    }
    
    public static PreparedStatement InsertNewRation(Connection con) {
        return getStatement(con, "INSERT INTO RATION (ID_RATION, TIMEP, WEIGHTG, ID_FEEDER) VALUES (?, ?, ?, ?);");
    }
    
    public static PreparedStatement InsertNewPet(Connection con) {
        return getStatement(con, "INSERT INTO PET (ID_PET, NAME, GENDER, WEIGHTKG, TYPE, STATUS, ID_USER) VALUES (?, ?, ?, ?, ?, ?, ?);");
    }
    
    public static PreparedStatement InsertNewMeasurement(Connection con) {
        return getStatement(con, "INSERT INTO RECORD (ID_RECORD, DATER, TIMER, VALUE, ID_RATION, ID_SENSOR, ID_FEEDER) VALUES (?, ?, ?, ?, ?, ?, ?);");
    }
    
    public static PreparedStatement DeleteUser(Connection con) {
        return getStatement(con, "DELETE FROM USER WHERE ID_USER = ?;");
    }
    
    public static PreparedStatement DeletePet(Connection con) {
        return getStatement(con, "DELETE FROM PET WHERE ID_PET = ?;");
    }
    
    public static PreparedStatement DeletePetWithUser(Connection con) {
        return getStatement(con, "DELETE FROM PET WHERE ID_USER = ?;");
    }
    
    public static PreparedStatement DeleteRation(Connection con) {
        return getStatement(con, "DELETE FROM RATION WHERE ID_FEEDER = ? AND ID_RATION = ?;");
    }
    
    public static PreparedStatement UpdateFeederUser(Connection con) {
        return getStatement(con, "UPDATE FEEDER SET ID_USER = ? WHERE ID_FEEDER = ?;");
    }
    
    public static PreparedStatement GetLastIdUserDB(Connection con) {
        return getStatement(con, "SELECT ID_USER FROM USER ORDER BY ID_USER DESC LIMIT 1;");
    }
    
    public static PreparedStatement GetLastIdPetDB(Connection con) {
        return getStatement(con, "SELECT ID_PET FROM PET ORDER BY ID_PET DESC LIMIT 1;");
    }
    
    public static PreparedStatement GetLastIdRationDB(Connection con) {
        return getStatement(con, "SELECT ID_RATION FROM RATION WHERE ID_FEEDER = ? ORDER BY ID_RATION DESC LIMIT 1;");
    }
    
    public static PreparedStatement GetIdFeeder(Connection con) {
        return getStatement(con, "SELECT ID_FEEDER FROM FEEDER WHERE ID_USER = ?;");
    }
    
    public static PreparedStatement DisableFK(Connection con) {
        return getStatement(con, "SET FOREIGN_KEY_CHECKS=0;");
    }
    
    public static PreparedStatement EnableFK(Connection con) {
        return getStatement(con, "SET FOREIGN_KEY_CHECKS=1;");
    }
    
}
