package mqtt;

/**
 *
 * @author mfran
 */
public class MQTTBroker {
    private static int qos = 2;
    private static String broker = "tcp://localhost:1883";
    private static String clientId = "jafeServer";
    
    public MQTTBroker() {
        
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
