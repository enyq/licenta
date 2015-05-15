/*
  ModuleManager.cpp - Library for TODO
  Created by TODO, May 15, 2015.
  TODO: Released into the public domain.
*/

#include "Arduino.h"
#include "ModuleManager.h"
#include "Constants.h"
#include "Wire.h"
#include "Module.h"
#include "Logger.h"
#include "EEPROM.h"

ModuleManager::ModuleManager(){
    _activeModules = 1;
    _modules[0] = Module(255, 255);
}

String ModuleManager::getInfo(byte moduleNumber){
  if ((moduleNumber > 0) && (moduleNumber < _activeModules)){
    return _modules[moduleNumber].getInfo();
  }
  return "";
}

void ModuleManager::saveModules(){
  EEPROM.write(0, _activeModules);
  for (byte i = 0; i < _activeModules; i++){
    EEPROM.write(1 + 2 * i, _modules[i].getAddr());
    EEPROM.write(2 + 2 * i, _modules[i].getType());
  }  
}

void ModuleManager::loadModules(){
  _modules[0] = Module(255, 255);
  _activeModules = EEPROM.read(0);
//  Logger.debug("Active modules number: " + _activeModules);
  if (_activeModules > MAX_MODULES_NUMBER){
    _activeModules = MAX_MODULES_NUMBER;
  }
  for (byte i = 1; i < _activeModules; i++){
    _modules[i] = Module(EEPROM.read(1 + 2 * i),EEPROM.read(2 + 2 * i));
  }
}

// TODO: Implement moduleCleanUp function => Maybe there should be a counter of unsuccessfull retries, will figure out that later
void ModuleManager::moduleCleanUp(){
  // This function will check for removed modules, and after several unsuccessfull retries will remove them from the list
  
  // Will save the new modules to EEPROM
  saveModules();
}

// TODO: implement updateModules method
void ModuleManager::updateModules(){
  moduleCleanUp();
/*  String msg = _modules[0].getInfo();
  byte moduleType = interpretMessage(msg);
  if (moduleType > 0){
    _modules[++_activeModules] = Module(_activeModules, moduleType);
  }*/
  saveModules(); 
}

