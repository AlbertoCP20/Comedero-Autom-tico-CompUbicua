package logic;

import mqtt.MQTTBroker;
import mqtt.MQTTSubscriber;

/**
 *
 * @author mfran
 */
public class ThreadSubscriber extends Thread{
    
    
    public ThreadSubscriber() {
        start();
    }
    
    
    @Override
    public void run() {
        MQTTBroker broker = new MQTTBroker();
        MQTTSubscriber suscriber = new MQTTSubscriber();
        suscriber.searchTopicsToSubscribe(broker);
    }
}
