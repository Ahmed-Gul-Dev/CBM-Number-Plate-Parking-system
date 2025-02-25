//https://anpr-fyp-default-rtdb.firebaseio.com/
// AIzaSyDFmZc0gH_mUkb7hXrqDZ0Zsdai1AJq420
//https://randomnerdtutorials.com/esp32-firebase-realtime-database/

//HUAWEI-tXK5  --> WXjdMjxS

#include <WiFi.h>
#include <Firebase_ESP_Client.h>

/*Put your SSID & Password*/
const char* ssid = "cbm";  // Enter SSID here -- deltanet254(nadik)
const char* password = "1234567890";  //Enter Password here -- nadik101

// Insert Firebase project API Key
#define API_KEY "AIzaSyDFmZc0gH_mUkb7hXrqDZ0Zsdai1AJq420"
// Insert RTDB URLefine the RTDB URL */
#define DATABASE_URL "https://anpr-fyp-default-rtdb.firebaseio.com/"
//Provide the token generation process info.
#include "addons/TokenHelper.h"
//Provide the RTDB payload printing info and other helper functions.
#include "addons/RTDBHelper.h"
//Define Firebase Data object
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;
unsigned long sendDataPrevMillis = 0, ptime = 0, sendDataMillis = 0;
int count = 0;
bool signupOK = false;

// WaterProof Ultrasonic Sensor
//Mode 0 is default mode with no jumpers or resistors (emulates HC-SR04)
const int trigA = 5;
const int echoA = 4;
const int trigB = 19;
const int echoB = 18;
const int trigC = 21;
const int echoC = 22;

String slot1 = " ", slot2 = " " , np = " ";
bool np_match = false;

void setup() {
  // Set up serial monitor
  Serial.begin(115200);
  Serial.println("*********************************");
  firebase_init();
  //   Set pinmodes for sensor connections
  pinMode(echoA, INPUT);
  pinMode(trigA, OUTPUT);
  pinMode(echoB, INPUT);
  pinMode(trigB, OUTPUT);
  pinMode(echoC, INPUT);
  pinMode(trigC, OUTPUT);
  pinMode(2, OUTPUT);
  digitalWrite(2, HIGH);
  delay(1000);
  digitalWrite(2, LOW);
}

bool readdb = false;
void loop() {
  pslots();
  if (read_ultra(trigC, echoC) < 80 && readdb == false) {
    Firebase.RTDB.setString(&fbdo, "/FromPi/scanning", "start");
    readdb = true;
  }
  else if (millis() - ptime > 3500 && readdb == true)
  {
    //    Serial.print(read_ultra(trigA, echoA));
    //    Serial.print("\t");
    //    Serial.print(read_ultra(trigB, echoB));
    //    Serial.print("\t");
    //    Serial.println(read_ultra(trigC, echoC));
    //    Serial.println("******************");
    Firebase_Data();
    if (np_match) {
      //      Serial.println("************ Success ****************");
      readdb = false;
      //      Firebase.RTDB.setString(&fbdo, "/FromPi/scanning", "stop");
      digitalWrite(2, LOW);
    }
    ptime = millis();
  }

}

// ******************************************************************************

void pslots() {

  if (Firebase.ready() && signupOK && millis() - sendDataMillis > 4000)
  {
    Serial.print(read_ultra(trigA, echoA));
    Serial.print("\t");
    Serial.println(read_ultra(trigB, echoB));
    if (read_ultra(trigA, echoA) > 80) {
      slot1 = "Empty";
    }
    else {
      slot1 = "Full";
    }
    
    if (read_ultra(trigB, echoB) > 80) {
      slot2 = "Empty";
    }
    else {
      slot2 = "Full";
    }
    Firebase.RTDB.setString(&fbdo, "parkingslot1", slot1);
    Firebase.RTDB.setString(&fbdo, "parkingslot2", slot2);
    sendDataMillis = millis();
  }
}

void Firebase_Data()
{
  uint8_t count = 1;
  if (Firebase.ready() && signupOK && (millis() - sendDataPrevMillis > 3000 || sendDataPrevMillis == 0))
  {
    if (Firebase.RTDB.getString(&fbdo, "/FromPi/NumberPlate")) {
      if (fbdo.dataType() == "string") {
        np = fbdo.stringData();
        Serial.print("From Firebase (NumberPlate) : ");
        Serial.println(np);
      }
    } // Getting Number PLate Value from Firebase (sent by pi)

    String st = "Hello";
    while (np_match == false || st == " ")
    {
      pslots();
      if (Firebase.RTDB.getString(&fbdo, "/Students/" + String(count) + "/vehicleNo")) {
        if (fbdo.dataType() == "string") {
          st = fbdo.stringData();
          Serial.print("From Firebase (vehicleNo) : ");
          Serial.println(st);
          if (st == np) {
            np_match = true;
            Serial.println("******** Matched....Success ******* ");
            digitalWrite(2, HIGH);

            //            if (Firebase.RTDB.getString(&fbdo, "/Students/" + String(count) + "/name")) {
            //              if (fbdo.dataType() == "string") {
            //                String n = fbdo.stringData();
            //                Serial.print("Vehicle Owner : ");
            //                Serial.println(n);
            //              }
            //            }
          }
          else {
            Serial.println("Not Match");
          }
        }
      }
      count++;
    } //while loop
    sendDataPrevMillis = millis();
  } // firebase startup
}

float read_ultra(const int TRIGPIN , const int ECHOPIN) {
  float duration, distance;
  // Set the trigger pin LOW for 2uS
  digitalWrite(TRIGPIN, LOW);
  delayMicroseconds(2);

  // Set the trigger pin HIGH for 20us to send pulse
  digitalWrite(TRIGPIN, HIGH);
  delayMicroseconds(20);

  // Return the trigger pin to LOW
  digitalWrite(TRIGPIN, LOW);

  // Measure the width of the incoming pulse
  duration = pulseIn(ECHOPIN, HIGH);

  // Determine distance from duration
  // Use 343 metres per second as speed of sound
  // Divide by 1000 as we want millimeters

  //  distance = (duration / 2) * 0.343;  // give readings in mm
  distance = ((duration / 2) * 0.343) / 10;  // give readings in cm


  // Print result to serial monitor
  //  Serial.print("distance: ");
  //  Serial.print(distance);
  //  Serial.println(" cm");
  return distance;
  // Delay before repeating measurement
  //  delay(250);
}

void firebase_init() {
  //connect to your local wi-fi network
  WiFi.begin(ssid, password);
  //check wi-fi is connected to wi-fi network
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.println("Wi-Fi Connecting..");
    delay(1000);
  }
  Serial.println("Wi-Fi Connected..!");
  Serial.print("Got IP: ");  Serial.println(WiFi.localIP());

  /* Assign the api key (required) */
  config.api_key = API_KEY;
  /* Assign the RTDB URL (required) */
  config.database_url = DATABASE_URL;
  /* Sign up */
  if (Firebase.signUp(&config, &auth, "", "")) {
    Serial.println("ok");
    signupOK = true;
  }
  else {
    Serial.printf("%s\n", config.signer.signupError.message.c_str());
  }

  /* Assign the callback function for the long running token generation task */
  config.token_status_callback = tokenStatusCallback; //see addons/TokenHelper.h

  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
  delay(1000);
}
