package logic;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import java.util.ArrayList;

import db.ConnectionDDBB;
import db.Feeder;
import db.Pet;
import db.RecordJafe;
import db.Ration;
import db.Topic;
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
                user.setIdUser(rs.getInt("id_user"));
                user.setName(rs.getString("name"));
                user.setFirstSurname(rs.getString("first_surname"));
                user.setSecondSurname(rs.getString("sec_surname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                
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
    
    public static ArrayList<Pet> getPetsFromDB() {
        ArrayList<Pet> pets = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetPets(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id_pet"));
                pet.setName(rs.getString("name"));
                pet.setGender(rs.getString("gender").charAt(0));
                pet.setWeight(rs.getFloat("weightKg"));
                pet.setType(rs.getString("type"));
                pet.setStatus(rs.getBoolean("status"));
                pet.setIdOwner(rs.getInt("id_user"));
                
                pets.add(pet);
            }	
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            pets = new ArrayList<>();
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            pets = new ArrayList<>();
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            pets = new ArrayList<>();
                
        } finally {
            conector.closeConnection(con);
        }
        
        return pets;
    }
    
    public static ArrayList<Feeder> getFeedersFromDB() {
        
        ArrayList<Feeder> feeders = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetFeeders(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Feeder feeder = new Feeder();
                feeder.setIdFeeder(rs.getString("id_feeder"));
                feeder.setFood(rs.getFloat("percentage_food"));
                feeder.setWater(rs.getFloat("percentage_water"));
                feeder.setIdUser(rs.getInt("id_user"));
                
                feeders.add(feeder);
            }	
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            feeders = new ArrayList<>();
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            feeders = new ArrayList<>();
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            feeders = new ArrayList<>();
                
        } finally {
            conector.closeConnection(con);
        }
        
        return feeders;
    }
    
    public static ArrayList<RecordJafe> getRecordsFromDB() {
        ArrayList<RecordJafe> records = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetRecords(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                RecordJafe recordJafe = new RecordJafe();
                recordJafe.setIdRecord(rs.getInt("id_record"));
                recordJafe.setDate(rs.getDate("dateR"));
                recordJafe.setTime(rs.getTime("timeR"));
                recordJafe.setValue(rs.getFloat("value"));
                recordJafe.setIdRation(rs.getInt("id_ration"));
                recordJafe.setIdSensor(rs.getInt("id_sensor"));
                recordJafe.setIdFeeder(rs.getString("id_feeder"));
                
                records.add(recordJafe);
            }
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            records = new ArrayList<>();
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            records = new ArrayList<>();
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            records = new ArrayList<>();
                
        } finally {
            conector.closeConnection(con);
        }
        
        return records;
    }
    
    public static ArrayList<Ration> getSchedulesFromDB() {
        
        ArrayList<Ration> schedules = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetSchedules(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Ration schedule = new Ration();
                schedule.setIdRation(rs.getInt("id_ration"));
                schedule.setFoodTime(rs.getTime("timeP"));
                schedule.setWeight(rs.getFloat("weightG"));
                schedule.setIdFeeder(rs.getString("id_feeder"));
                
                schedules.add(schedule);
            }	
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            schedules = new ArrayList<>();
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            schedules = new ArrayList<>();
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            schedules = new ArrayList<>();
                
        } finally {
            conector.closeConnection(con);
        }
        
        return schedules;
    }
    
    public static ArrayList<User> getUserIDFromDB(String idUser) {
        ArrayList<User> users = new ArrayList<>();
        
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetUserID(con);
            Log.log.info("Query=> {}", ps.toString());
            
            ps.setInt(1, Integer.parseInt(idUser));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setName(rs.getString("name"));
                user.setFirstSurname(rs.getString("first_surname"));
                user.setSecondSurname(rs.getString("sec_surname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                
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
    
    public static int getUserValidation(String email, String password) {
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        String passwordBD = "";
        int idUser = -3;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetUserPassword(con);
            Log.log.info("Query=> {}", ps.toString());
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                passwordBD = rs.getString("password");
                idUser = rs.getInt("id_user");
            }	
            else {
                return 0;
            }
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            passwordBD = "";
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            passwordBD = "";
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            passwordBD = "";
                
        } finally {
            conector.closeConnection(con);
        }
        
        if (password.equals(passwordBD)) {
            return idUser;
        }
        else {
            return -2;
        }
    }
    
    public static int getFeederValidation(String idFeeder) {
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        int idUser = 0;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetFeederByID(con);
            Log.log.info("Query=> {}", ps.toString());
            
            ps.setString(1, idFeeder);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                idUser = rs.getInt("id_user");
            }	
            else {
                return 0;
            }
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
                
        } finally {
            conector.closeConnection(con);
        }
        
        if (idUser == 0) {
            return 1;
        }
        else {
            return -2;
        }
    }
    
    public static String getUserPassword(String email) {
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        String passwordBD = "";
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetUserPassword(con);
            Log.log.info("Query=> {}", ps.toString());
            
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                passwordBD = rs.getString("password");
            }	
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            passwordBD = "0";
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            passwordBD = "0";
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            passwordBD = "0";
                
        } finally {
            conector.closeConnection(con);
        }
        
        if (passwordBD.isEmpty()) {
            return "0";
        }
        else {
            return passwordBD;
        }
    }
    
    public static ArrayList<Pet> getPetUserFromDB(String idUser) {
        
        ArrayList<Pet> pets = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetPetUser(con);
            Log.log.info("Query=> {}", ps.toString());
            
            ps.setInt(1, Integer.parseInt(idUser));
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id_pet"));
                pet.setName(rs.getString("name"));
                pet.setGender(rs.getString("gender").charAt(0));
                pet.setWeight(rs.getFloat("weightKg"));
                pet.setType(rs.getString("type"));
                pet.setStatus(rs.getBoolean("status"));
                pet.setIdOwner(rs.getInt("id_user"));
                
                pets.add(pet);
            }	
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            pets = new ArrayList<>();
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            pets = new ArrayList<>();
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            pets = new ArrayList<>();
                
        } finally {
            conector.closeConnection(con);
        }
        
        return pets;
    }
    
    public static ArrayList<Ration> getSchedulesUserFromDB(String idFeeder) {
        
        ArrayList<Ration> schedules = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetScheduleUser(con);
            Log.log.info("Query=> {}", ps.toString());
            
            ps.setString(1, idFeeder);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Ration schedule = new Ration();
                schedule.setIdRation(rs.getInt("id_ration"));
                schedule.setFoodTime(rs.getTime("timeP"));
                schedule.setWeight(rs.getFloat("weightG"));
                schedule.setIdFeeder(rs.getString("id_feeder"));
                
                schedules.add(schedule);
            }	
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            schedules = new ArrayList<>();
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            schedules = new ArrayList<>();
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            schedules = new ArrayList<>();
                
        } finally {
            conector.closeConnection(con);
        }
        
        return schedules;
        
    }
    
    public static ArrayList<RecordJafe> getRecordsFeederFromDB(String idFeeder) {
        
        ArrayList<RecordJafe> records = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetRecordsFeeder(con);
            Log.log.info("Query=> {}", ps.toString());
            
            ps.setString(1, idFeeder);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                RecordJafe recordJafe = new RecordJafe();
                recordJafe.setIdRecord(rs.getInt("id_record"));
                recordJafe.setDate(rs.getDate("dateR"));
                recordJafe.setTime(rs.getTime("timeR"));
                recordJafe.setValue(rs.getFloat("value"));
                recordJafe.setIdRation(rs.getInt("id_ration"));
                recordJafe.setIdSensor(rs.getInt("id_sensor"));
                recordJafe.setIdFeeder(rs.getString("id_feeder"));
                
                records.add(recordJafe);
            }	
                
        } catch (SQLException sqle) {
            Log.log.error("Error: {}", sqle);
            records = new ArrayList<>();
                
        } catch (NullPointerException npe) {
            Log.log.error("Error: {}", npe);
            records = new ArrayList<>();
                
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            records = new ArrayList<>();
                
        } finally {
            conector.closeConnection(con);
        }
        
        return records;
        
    }
    
    public static int addNewUser(User user, String idFeeder) {
        
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        int newId;
        
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.InsertNewUser(con);
            
            newId = getLastIdUser() + 1;
            ps.setInt(1, newId);
            ps.setString(2, user.getName());
            ps.setString(3, user.getFirstSurname());
            ps.setString(4, user.getSecondSurname());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getPassword());
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();
            
            if (addUserToFeeder(newId, idFeeder) != 1) {
                return 0;
            }
            
	} catch (SQLException e) {
            Log.log.error("Error: {}", e);
            return 0;
            
	} catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            return 0;
            
	} catch (Exception e) {
            Log.log.error("Error:{}", e);
            return 0;
            
        } finally {
            conector.closeConnection(con);
	}
        return 1;
    }
    
    public static int addUserToFeeder(int idUser, String idFeeder) {

	ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConnectionDDBB.UpdateFeederUser(con);
            ps.setInt(1, idUser);
            ps.setString(2, idFeeder);
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            return -1; 
            
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            return -1;
            
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            return -1; 
            
        } finally {
            conector.closeConnection(con);
	}
        return 1;
    }
    
    public static int addNewPet(Pet pet) {
        
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        int newId;
        
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.InsertNewPet(con);
            
            newId = getLastIdPet() + 1;
            ps.setInt(1, newId);
            ps.setString(2, pet.getName());
            ps.setString(3, String.valueOf(pet.getGender()));
            ps.setFloat(4, pet.getWeight());
            ps.setString(5, pet.getType());
            ps.setBoolean(6, pet.isStatus());
            ps.setInt(7, pet.getIdOwner());
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();
            
	} catch (SQLException e) {
            Log.log.error("Error: {}", e);
            return 0;
            
	} catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            return 0;
            
	} catch (Exception e) {
            Log.log.error("Error:{}", e);
            return 0;
            
        } finally {
            conector.closeConnection(con);
	}
        return 1;
    }
    
    public static void storeNewMeasurement(Topic newTopic) {
        
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = ConnectionDDBB.InsertNewMeasurement(con);
            
            ps.setDate(1, Date.valueOf(LocalDate.now(ZoneId.of("Europe/Madrid"))));
            ps.setTime(2, Time.valueOf(LocalTime.now(ZoneId.of("Europe/Madrid"))));
            ps.setFloat(3, newTopic.getValor());
            ps.setInt(4, newTopic.getIdSensor());
            ps.setString(5, newTopic.getIdFeeder());
            
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            
        } finally {
            conector.closeConnection(con);
        }
    }
    
    public static void deleteScheduleFromDB(String idSchedule, String idFeeder) {
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = ConnectionDDBB.DeleteSchedule(con);
            
            ps.setInt(1, Integer.parseInt(idSchedule));
            ps.setString(2, idFeeder);
            
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();  
            
	} catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
	} catch (NullPointerException e)
	{
            Log.log.error("Error: {}", e);
	} catch (Exception e)
	{
            Log.log.error("Error:{}", e);
        } finally
        {
            conector.closeConnection(con);
	}
    }
    
    public static void deletePetFromDB(String idPet) {
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = ConnectionDDBB.DeletePet(con);
            
            ps.setInt(1, Integer.parseInt(idPet));
            
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();  
            
	} catch (SQLException e)
	{
            Log.log.error("Error: {}", e);
	} catch (NullPointerException e)
	{
            Log.log.error("Error: {}", e);
	} catch (Exception e)
	{
            Log.log.error("Error:{}", e);
        } finally
        {
            conector.closeConnection(con);
	}
    }
    
    private static int getLastIdUser(){
        int ultimoCod=-1;
	ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConnectionDDBB.GetLastIdUserDB(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                ultimoCod=rs.getInt("id_user");
            }
             
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            return -1; 
            
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            return -1;
            
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            return -1; 
            
        } finally {
            conector.closeConnection(con);
	}
        return ultimoCod;
    }
    
    private static int getLastIdPet(){
        int ultimoCod=-1;
	ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
		
            PreparedStatement ps = ConnectionDDBB.GetLastIdPetDB(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                ultimoCod=rs.getInt("id_pet");
            }
             
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            return -1; 
            
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            return -1;
            
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            return -1; 
            
        } finally {
            conector.closeConnection(con);
	}
        return ultimoCod;
    }
        
}
