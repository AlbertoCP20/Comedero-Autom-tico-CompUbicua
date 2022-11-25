//Wifi Settings
const char* ssid	= "cubicua";
const char* password = "estoesesparta";  const char* hostname = "ESP8266_G8";

//MQTT Settings
const char* MQTT_BROKER_ADRESS = "mqttIp";  const uint16_t MQTT_PORT = 1883;
const char* MQTT_CLIENT_NAME = "ESP8266Client_G8";

//Server Settings
IPAddress ip(192, 168, 1, 119);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);
