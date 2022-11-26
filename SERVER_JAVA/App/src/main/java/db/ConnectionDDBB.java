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
        return getStatement(con, "SELECT * FROM USERS;");
    }
    
    public static PreparedStatement GetIdFeeders(Connection con) {
        return getStatement(con, "SELECT ID_FEEDER FROM FEEDER");
    }
    
}