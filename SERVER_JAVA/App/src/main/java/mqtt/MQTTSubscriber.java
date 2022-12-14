package mqtt;

import java.sql.Connection;

import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttClient;

import db.ConnectionDDBB;
import db.Topic;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import logic.Log;
import logic.Logic;


/**
 *
 * @author mfran
 */
public class MQTTSubscriber implements MqttCallback{
    
    public void searchTopicsToSubscribe(MQTTBroker broker) {
        Log.logmqtt.info("Search topics to subscribe");
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        ArrayList<String> topics = new ArrayList<>();
        
        try {
            con = conector.obtainConnection(true);
            Log.logmqtt.debug("Database connected");
            
            //Get feeders to search the topic
            PreparedStatement  psFeeders = ConnectionDDBB.GetFeeders(con);
            Log.logmqtt.debug("Query to search feeders => {}", psFeeders.toString());
            ResultSet rsFeeders = psFeeders.executeQuery();
            
            while (rsFeeders.next()) {
                topics.add("Comedero" + rsFeeders.getString("id_feeder") + "/#");
            }
            Log.logmqtt.info("topics " + topics);
            subscribeTopic(broker, topics);
            
        } catch (NullPointerException npe) {
            Log.logmqtt.error("Error: {]", npe);
        } catch (SQLException e) {
            Log.logmqtt.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
    }
    
    public void subscribeTopic(MQTTBroker broker, ArrayList<String> topics) {
        Log.logmqtt.debug("Subscribe to topics");
        MemoryPersistence persistence = new MemoryPersistence();
        
        try {
            MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.logmqtt.info("Mqtt Connecting to broker: " + MQTTBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.info("Mqtt Connected");
            sampleClient.setCallback(this);
            
            for (int i = 0; i < topics.size(); i++) {
                sampleClient.subscribe(topics.get(i));
                Log.logmqtt.info("Subscribed to {}", topics.get(i));
            }
            
        } catch (MqttException me) {
            Log.logmqtt.info("Error subscribing topic: {}", me);
        } catch (Exception e) {
            Log.logmqtt.info("Error subscribing topic: {}", e);
        }
    }
    
    @Override
    public void connectionLost(Throwable cause) {
        
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.logmqtt.info("{}: {}", topic, message.toString());
        String[] topics = topic.split("/");
        Topic newTopic = new Topic();
        
        if (topic.contains("Sensor")) {
            String idFeeder = topics[0].replace("Comedero", "");
            
            if (topic.contains("PreassureF")) {
                String messageText = message.toString();
                String[] listMessage = messageText.split("/");
                newTopic.setIdFeeder(idFeeder); 
                newTopic.setType("Presión");
                newTopic.setIdRation(Integer.parseInt(listMessage[0]));
                newTopic.setValor(Float.parseFloat(listMessage[1]));

                Log.logmqtt.info("Mensaje from feeder{}, sensor{}: {}", 
                                newTopic.getIdFeeder(), newTopic.getIdSensor(), message.toString());

                Logic.storeNewMeasurement(newTopic);
            }
            else if (topic.contains("Infrared")){
                
                newTopic.setIdFeeder(idFeeder);
                newTopic.setType("Infrarrojo");
                newTopic.setValor(Float.parseFloat(message.toString()));

                Log.logmqtt.info("Mensaje from feeder{}, sensor{}: {}", 
                                newTopic.getIdFeeder(), newTopic.getIdSensor(), message.toString());

                Logic.storeNewMeasurement(newTopic);

            }
        }
        
    }
    
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        
    }
    
}
