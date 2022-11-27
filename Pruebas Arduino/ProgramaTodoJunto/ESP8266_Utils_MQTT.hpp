void InitMqtt() {
  Serial.print("Connect to MQTT - ");
  Serial.print(MQTT_BROKER_ADRESS);  
  Serial.print(" - ");  
  Serial.println(MQTT_PORT);
  mqttClient.setServer(MQTT_BROKER_ADRESS, MQTT_PORT);  
  mqttClient.setCallback(OnMqttReceived);
}
void ConnectMqtt() {
  while (!mqttClient.connected()) {
      Serial.print("Starting MQTT connection...");
          if (mqttClient.connect(MQTT_CLIENT_NAME,"",""))	{  
            Serial.println("Conexión completada");
            SuscribeMqtt("Comedero1/Hour");
            
      }
      else	{
          Serial.print("Failed MQTT connection, rc=");  Serial.print(mqttClient.state());  Serial.println(" try again in 5 seconds");  delay(5000);
      }
  }
}
void HandleMqtt() {
  if (!mqttClient.connected()) {  
      ConnectMqtt();
  }
  mqttClient.loop();
}
