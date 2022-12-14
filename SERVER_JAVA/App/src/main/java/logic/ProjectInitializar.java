package logic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import mqtt.MQTTBroker;
import mqtt.MQTTPublisher;
import mqtt.MQTTSubscriber;

/**
 *
 * @author mfran
 */
@WebListener
public class ProjectInitializar implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Log.log.info("-->Suscribe Topics<--");
        MQTTBroker broker = new MQTTBroker("n1");
        MQTTPublisher.publish(broker, "TOCK/TOCK", "Prueba");
        MQTTSubscriber suscriber = new MQTTSubscriber();
        suscriber.searchTopicsToSubscribe(broker);
        
        MQTTBroker broker2 = new MQTTBroker("n2");
        MQTTPublisher.publish(broker2, "TOCK/TOCK", "Prueba2");
        Log.log.info("-->Funcionando<--");
        
        Log.log.info("-->Running time Thread<--");
        new ThreadTimeFood(broker2);

        Log.log.info("-->Arrancado<--");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       
    }
    
}
