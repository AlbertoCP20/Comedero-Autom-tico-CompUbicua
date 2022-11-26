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
import db.Topics;
import logic.Log;


/**
 *
 * @author mfran
 */
public class MQTTSubscriber implements MqttCallback{
    
    public void searchTopicsToSubscribe(MQTTBroker broker) {
        ConnectionDDBB conector = new ConnectionDDBB();
        Connection con = null;
        ArrayList<String> topics = new ArrayList<>();
        
        try {
            con = conector.obtainConnection(true);
            Log.logmqtt.debug("Database connected");
            
            //PreparedStatement 
            
        } catch (NullPointerException npe) {
            Log.logmqtt.error("Error: {]", npe);
        } catch (Exception e) {
            Log.logmqtt.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
    }
    
    public void subscribeTopic(MQTTBroker broker, ArrayList<String> topics) throws MqttException {
        Log.logmqtt.debug("Subscribe to topics");
        MemoryPersistence persistence = new MemoryPersistence();
        
        try {
            MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.logmqtt.debug("Mqtt Connecting to broker: " + MQTTBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.debug("Mqtt Connected");
            sampleClient.setCallback(this);
            
            for (int i = 0; i < topics.size(); i++) {
                sampleClient.subscribe(topics.get(i));
                Log.logmqtt.info("Subscribed to {}", topics.get(i));
            }
            
        } catch (MqttException me) {
            Log.logmqtt.error("Error subscribing topic: {}", me);
        } catch (Exception e) {
            Log.logmqtt.error("Error subscribing topic: {}", e);
        }
    }
    
    @Override
    public void connectionLost(Throwable cause) {
        
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.logmqtt.info("{}: {}", topic, message.toString());
        String[] topics = topic.split("/");
        Topics newTopic = new Topics();
        
    }
    
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        
    }
    
}
