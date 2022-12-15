package mqtt;

/**
 *
 * @author mfran
 */
public class MQTTBroker {
    private static int qos = 2;
    private static String broker = "tcp://192.168.1.47:1883";
    private static String clientId;
    
    public MQTTBroker(String clientId) {
        this.clientId = clientId;
    }
    
    public static int getQoS() {
        return qos;
    }
    
    public static String getBroker() {
        return broker;
    }
    
    public static String getClientId() {
        return clientId;
    }
}
