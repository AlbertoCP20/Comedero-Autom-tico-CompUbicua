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
        return getStatement(con, "SELECT * FROM SCHEDULE;");
    }
    
    public static PreparedStatement GetRecords(Connection con) {
        return getStatement(con, "SELECT * FROM RECORD;");
    }
    
    public static PreparedStatement GetUserID(Connection con) {
        return getStatement(con, "SELECT * FROM USER WHERE ID_USER = ?;");
    }
    
    public static PreparedStatement GetPetUser(Connection con) {
        return getStatement(con, "SELECT * FROM PET WHERE ID_PET = ?;");
    }
    
    public static PreparedStatement GetRecordsFeeder(Connection con) {
        return getStatement(con, "SELECT * FROM RECORD WHERE id_feeder_machine = ?;");
    }
    
    public static PreparedStatement GetScheduleUser(Connection con) {
        return getStatement(con, "SELECT * FROM SCHEDULE WHERE id_feeder = ?;");
    }
    
    public static PreparedStatement InsertNewMeasurement(Connection con) {
        return getStatement(con, "INSERT INTO RECORD (FECHA, HORA, VALUE, ID_SENSOR_MACHINE, ID_FEEDER_MACHINE) VALUES (?, ?, ?, ?, ?);");
    }
    
    public static PreparedStatement InsertNewFeeder(Connection con) {
        return getStatement(con, "INSERT INTO FEEDER (ID_FEEDER, ID_USER_LANDLORD) VALUES (?, ?);");
    }
    
    public static PreparedStatement DeletePet(Connection con) {
        return getStatement(con, "DELETE FROM PET WHERE ID_PET = ?;");
    }
    
    public static PreparedStatement DeleteSchedule(Connection con) {
        return getStatement(con, "DELETE FROM SCHEDULE WHERE ID_SCHEDULE = ? AND ID_FEEDER = ?;");
    }
    
}
