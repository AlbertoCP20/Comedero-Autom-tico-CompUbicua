package mqtt;

import logic.Log;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 *
 * @author mfran
 */
public class MQTTPublisher {
    public static void publish(MQTTBroker broker, String topic, String content) {
        MemoryPersistence persistence = new MemoryPersistence();
        
        try {
            MqttClient sampleClient = new MqttClient(broker.getBroker(), broker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.logmqtt.info("Connecting to broker: " + broker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.info("Connected");
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(broker.getQoS());
            sampleClient.publish(topic, message);
            Log.logmqtt.info("Message published");
            sampleClient.disconnect();
            Log.logmqtt.info("Disconnected");
        
        } catch(MqttException me) {
            Log.logmqtt.error("Error on publishing value: {}", me);
        } catch(Exception e) {
            Log.logmqtt.error("Error on publishing value: {}", e);
        }
    }
}
