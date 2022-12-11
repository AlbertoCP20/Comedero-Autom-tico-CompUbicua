package logic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import mqtt.MQTTBroker;
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
        MQTTBroker broker = new MQTTBroker();
        MQTTSubscriber suscriber = new MQTTSubscriber();
        suscriber.searchTopicsToSubscribe(broker);

        Log.log.info("-->Running time Thread<--");
        //new ThreadTimeFood();
        
        Log.log.info("-->Arrancado<--");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       
    }
    
}
