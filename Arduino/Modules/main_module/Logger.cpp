/*
  Logger.cpp - Library for TODO
  Created by TODO, May 14, 2015.
  TODO Released into the public domain.
*/

#include "Arduino.h"
#include "Logger.h"
#include "Constants.h"
#include <SD.h>

Logger::Logger(byte level, byte dest, String destFile){
  _level = level;
  _dest = dest;
  _destFile = destFile;
  Serial.begin(9600);
  if ((_dest - LOG_SERIAL_ONLY > 0) && (_destFile != "")){
    //TODO: Initialize SD card
  }
}

// TODO: Include time too (to be done later)
void Logger::log(String msg){
  Serial.println(msg);
  // TODO: Write to SD card too
}

void Logger::info(String msg){
  log("INFO : " + msg);
}

void Logger::warn(String msg){
  if (_level >= LOG_WARN) log("WARNING : " + msg);
}

void Logger::debug(String msg){
  if (_level >= LOG_WARN) log("DEBUG : " + msg);
}

