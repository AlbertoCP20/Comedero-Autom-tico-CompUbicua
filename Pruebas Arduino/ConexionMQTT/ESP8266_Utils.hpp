void ConnectWiFi_STA() {  
    Serial.println("");  
    WiFi.mode(WIFI_STA);  
    WiFi.begin(ssid, password);
    Serial.print("Conectando a:\t");
    Serial.println(ssid);   
    while (WiFi.status() != WL_CONNECTED) { 
      delay(100);
      Serial.print('.');
    }
    Serial.println("");  Serial.print("Iniciado STA:\t");  Serial.println(ssid);  Serial.print("IP address:\t");  Serial.println(WiFi.localIP());
}
