#include "Module.h"
#include <Wire.h>

String info;

Module mod[110];

void setup() {
  Wire.begin();
}

void loop() {
  info=mod[0].get_info();
  info=mod[1].get_info();
}
