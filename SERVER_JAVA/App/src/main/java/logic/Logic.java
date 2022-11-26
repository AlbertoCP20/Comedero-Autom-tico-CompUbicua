package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.ConnectionDDBB;
import db.User;

/**
 *
 * @author mfran
 */
public class Logic {
	
    public static ArrayList<User> getUsersFromDB() {
        
        ArrayList<User> users = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetUsers(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id_user"));
                //user.setName(rs.getString("name"));
                users.add(user);
            }	
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            users = new ArrayList<>();
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            users = new ArrayList<>();
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            users = new ArrayList<>();
                
        } finally {
            conector.closeConnection(con);
        }
        
        return users;
    }
        
}
