WiFiClient espClient;
PubSubClient mqttClient(espClient);  
void SuscribeMqtt(char* topic) {
  mqttClient.subscribe(topic);
}
String payload;
void PublishMqtt(char* topic,unsigned long data) {  
  payload = "";
  payload = String(data);  
  mqttClient.publish(topic, (char*)payload.c_str());
}
String content = "";
String top = "";
void OnMqttReceived(char* topic, byte* payload, unsigned int length) {
  Serial.print("Received on ");  Serial.print(topic);  Serial.print(": ");
  top = topic;
  content = "";
  for (size_t i = 0; i < length; i++) {  
      content.concat((char)payload[i]);
  }
  Serial.print(content);  Serial.println();
}

// Gets the value of the content we receive from a topic
String getContent(){
  return content;
}

// Checks the topic from which the data is received. If topic == Comedero1/Racion then true
bool isTopicRacion(){
  bool flag = false;
  if(top.equals("Comedero1/Racion")){
    flag = true;
  }
  return flag;
}

// Resets topic
void setTopicDefault(){
  top = "";
}
