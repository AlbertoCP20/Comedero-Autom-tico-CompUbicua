//Wifi Settings
const char* ssid	= "ssid";
const char* password = "password";  const char* hostname = "ESP8266_1";

//MQTT Settings
const char* MQTT_BROKER_ADRESS = "192.168.1.202";  const uint16_t MQTT_PORT = 1883;
const char* MQTT_CLIENT_NAME = "ESP8266Client_1";

//Server Settings
IPAddress ip(192, 168, 1, 202);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);
