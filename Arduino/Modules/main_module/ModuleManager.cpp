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
  init();
}

ModuleManager::ModuleManager(Logger* logger){
  init();
  _logger = logger;
}

void ModuleManager::init(){
    _activeModules = 1;
    _modules[0] = Module(NEW_MODULE_ID, 255); 
}

String ModuleManager::getInfo(byte moduleNumber){
  _logger->info("Getting info from module number: ");
// _logger->info(moduleNumber);
  if ((moduleNumber > 0) && (moduleNumber < _activeModules)){
    return _modules[moduleNumber].getInfo();
  }
  return "";
}

void ModuleManager::saveModules(){
  _logger->info("Saving modules to EEPROM");
  EEPROM.write(0, _activeModules);
  for (byte i = 0; i < _activeModules; i++){
    EEPROM.write(1 + 2 * i, _modules[i].getAddr());
    EEPROM.write(2 + 2 * i, _modules[i].getType());
  }  
}

void ModuleManager::loadModules(){
  _logger->info("Loading modules from EEPROM");
  _modules[0] = Module(NEW_MODULE_ID, 255);
  _activeModules = EEPROM.read(0);
  _logger->debug("Active modules number: " + _activeModules);
  if (_activeModules > MAX_MODULES_NUMBER){
    _activeModules = MAX_MODULES_NUMBER;
  }
  for (byte i = 1; i < _activeModules; i++){
    _modules[i] = Module(EEPROM.read(1 + 2 * i),EEPROM.read(2 + 2 * i));
//    _logger.debug("Module " + i + " initialized with the following values: ");// + _modules[i].getType() + " " + _modules[i].getAddr());
  }
}


void ModuleManager::moduleCleanUp(){
  // This function will check for removed modules, and after several unsuccessfull retries will remove them from the list
  for (byte i = 1; i < _activeModules; i++){
    if (_modules[i].getRetries() > 10){
      for (byte j = i; j < _activeModules - 1; j++) _modules[j] = _modules[j + 1];
      _activeModules--;
    }
  }
  // Will save the new modules to EEPROM
  saveModules();
}

// Will return 1 if there is a newly installed module
byte ModuleManager::pingAll(){
  for (byte i = 1; i < _activeModules; i++) _modules[i].ping();
  return _modules[0].ping();
}

// TODO: implement updateModules method
void ModuleManager::updateModules(){
  _logger->info("UPDATE modules");
  _modules[0].sendMessage("SET ADDR 25");
  pingAll();
  _modules[++_activeModules] = Module(_activeModules, _modules[0].getType());
  _modules[0].setAddr(_activeModules);
  moduleCleanUp();
  _modules[0].getInfo();
//  _logger->info(msg);
//  if (ping == 1) _logger->info("Ping from client");
//  else _logger->info("No ping from client");
//  _logger->debug(msg);
//  Serial.print("PING result: ");
//  Serial.println(ping);
/*  String msg = _modules[0].getInfo();
  byte moduleType = interpretMessage(msg);
  if (moduleType > 0){
    _modules[++_activeModules] = Module(_activeModules, moduleType);
  }*/
  saveModules(); 
}

