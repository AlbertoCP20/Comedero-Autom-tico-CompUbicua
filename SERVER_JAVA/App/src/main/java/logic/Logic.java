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
import db.Schedule;
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
                user.setBirthday(rs.getDate("birthday"));
                user.setGender(rs.getString("gender").charAt(0));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhoto(rs.getByte("photo"));
                
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
                pet.setBirthday(rs.getDate("birthday"));
                pet.setGender(rs.getString("gender").charAt(0));
                pet.setBreed(rs.getString("breed"));
                pet.setWeight(rs.getFloat("weight"));
                pet.setPhoto(rs.getByte("photo"));
                pet.setIdOwner(rs.getInt("id_user_owner"));
                
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
                feeder.setIdUser(rs.getInt("id_user_landlord"));
                
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
                recordJafe.setDate(rs.getDate("fecha"));
                recordJafe.setTime(rs.getTime("hora"));
                recordJafe.setValue(rs.getFloat("value"));
                recordJafe.setIdSensor(rs.getInt("id_sensor_machine"));
                recordJafe.setIdFeeder(rs.getString("id_feeder_machine"));
                
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
    
    public static ArrayList<Schedule> getSchedulesFromDB() {
        
        ArrayList<Schedule> schedules = new ArrayList<>();

        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConnectionDDBB.GetSchedules(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Schedule schedule = new Schedule();
                schedule.setIdSchedule(rs.getInt("id_schedule"));
                schedule.setFoodTime(rs.getTime("food_time"));
                schedule.setWeight(rs.getFloat("weight"));
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
                user.setBirthday(rs.getDate("birthday"));
                user.setGender(rs.getString("gender").charAt(0));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhoto(rs.getByte("photo"));
                
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
                pet.setBirthday(rs.getDate("birthday"));
                pet.setGender(rs.getString("gender").charAt(0));
                pet.setBreed(rs.getString("breed"));
                pet.setWeight(rs.getFloat("weight"));
                pet.setPhoto(rs.getByte("photo"));
                pet.setIdOwner(rs.getInt("id_user_owner"));
                
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
    
    public static ArrayList<Schedule> getSchedulesUserFromDB(String idFeeder) {
        
        ArrayList<Schedule> schedules = new ArrayList<>();

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
                Schedule schedule = new Schedule();
                schedule.setIdSchedule(rs.getInt("id_schedule"));
                schedule.setFoodTime(rs.getTime("food_time"));
                schedule.setWeight(rs.getFloat("weight"));
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
                recordJafe.setDate(rs.getDate("fecha"));
                recordJafe.setTime(rs.getTime("hora"));
                recordJafe.setValue(rs.getFloat("value"));
                recordJafe.setIdSensor(rs.getInt("id_sensor_machine"));
                recordJafe.setIdFeeder(rs.getString("id_feeder_machine"));
                
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
    
    public static void registerNewFeeder(Feeder newFeeder) {
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        
        try
	{
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = ConnectionDDBB.InsertNewFeeder(con);
            
            ps.setString(1, newFeeder.getIdFeeder());
            ps.setInt(2, newFeeder.getIdUser());

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
        
}
