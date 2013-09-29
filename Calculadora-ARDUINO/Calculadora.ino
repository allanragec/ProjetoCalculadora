    
String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete
double valA, valB, resultado;
char operador;
boolean valAComplete = false, valBComplete = false , operadorComplete = false;

void setup() {
  // initialize serial:
  Serial.begin(9600);
  // reserve 200 bytes for the inputString:
  inputString.reserve(200);
}

void loop() {
  // print the string when a newline arrives:

  if (stringComplete) {
    //Serial.println(inputString); 
    if(!valAComplete){
      valA =stringToFloat(inputString); 
      valAComplete = true;
    }else if(!operadorComplete){
      operador = inputString.charAt(0);;
      operadorComplete = true;
    }else if(!valBComplete){
      valB =stringToFloat(inputString); 
      valBComplete = true;
    }
    
    // clear the string:
    inputString = "";
    stringComplete = false;
    
    if(valBComplete){
      valAComplete = false;
      operadorComplete = false;
      valBComplete = false;
     
      if(operador == '+'){
        resultado = valA + valB;
      }else if(operador == '-'){
        resultado = valA - valB;        
      }else if(operador == '*'){
        resultado = valA * valB;        
      }else if(operador == '/'){
        resultado = valA / valB;        
      }else{
        limpaValores();
      }
      Serial.print(resultado); 
      Serial.print('|'); 
    } 

  }
}

double stringToFloat(String minhaString) {
  double numero = 0;
  
  char buf[minhaString.length()];
  minhaString.toCharArray(buf,minhaString.length());
  numero =atof(buf); 
    
  return numero;
}

void limpaValores(){
  valAComplete = false;
  operadorComplete = false;
  valBComplete = false;
  stringComplete = false;
  resultado = 0;
}


/*
  SerialEvent occurs whenever a new data comes in the
 hardware serial RX.  This routine is run between each
 time loop() runs, so using delay inside loop can delay
 response.  Multiple bytes of data may be available.
 */
void serialEvent() {

    if(Serial.available()){
    while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read(); 
    // add it to the inputString:
    if (inChar != '|') { 
      inputString += inChar;
    }else if(inChar == '#'){
      limpaValores();
    }else{
      stringComplete = true;
    }
  }
  }
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
 
  }


