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
  _logger = logger;
//  init();
}

void ModuleManager::init(){
    loadModules();
}

String ModuleManager::getInfo(byte moduleNumber){
  //_logger->info("Getting info from module number: ");
  //_logger->info((String)moduleNumber);
  if ((moduleNumber > 0) && (moduleNumber <= _activeModules)){
    return _modules[moduleNumber].getInfo();
  }
  return "";
}

void ModuleManager::saveModules(){
  //_logger->info("Saving modules to EEPROM");
  EEPROM.write(0, _activeModules);
  for (byte i = 1; i <= _activeModules; i++){
    EEPROM.write(1 + 2 * i, _modules[i].getAddr());
    EEPROM.write(2 + 2 * i, _modules[i].getType());
  }  
}

void ModuleManager::loadModules(){
  //_logger->info("Loading modules from EEPROM");
  _modules[0] = Module(NEW_MODULE_ID, 255);
  _activeModules = EEPROM.read(0);
  //_logger->info("Active modules number: " + (String)_activeModules); //TODO: setup logging correctly
  if (_activeModules > MAX_MODULES_NUMBER){
    _activeModules = MAX_MODULES_NUMBER;
  }
  for (byte i = 1; i <= _activeModules; i++){
    _modules[i] = Module(EEPROM.read(1 + 2 * i),EEPROM.read(2 + 2 * i));
    //_logger->info("Module " + (String)i + " initialized with the following values: " + (String)_modules[i].getAddr() + " " + (String)_modules[i].getType()); //TODO: setup logging correctly
  }
}


void ModuleManager::moduleCleanUp(){
  // This function will check for removed modules, and after several unsuccessfull retries will remove them from the list
  pingAll();
  for (byte i = 1; i <= _activeModules; i++){
    if (_modules[i].getRetries() > MAX_PING_RETRIES){
      //_logger->info("Deleting module number " + (String)i + " with address " + (String)_modules[i].getAddr() + " because it did not responded more than 10 times");
      for (byte j = i; j <= _activeModules - 1; j++){
        _modules[j + 1].setAddr(j + I2C_MIN_ADDR); // This will assure that the address is set in the module too      
        _modules[j] = Module(j + I2C_MIN_ADDR, _modules[j + 1].getType());
      }
      _activeModules--;
    }
  }
  // Will save the new modules to EEPROM
  saveModules();
}

// Will return 1 if there is a newly installed module
byte ModuleManager::pingAll(){
  for (byte i = 1; i <= _activeModules; i++) _modules[i].ping();
  return _modules[0].ping();
}

// TODO: implement updateModules method
void ModuleManager::updateModules(){
  //_logger->info("UPDATE modules");
  moduleCleanUp();
  _modules[0] = Module(110, 255);
  if (_modules[0].ping() == 1){ // We have a new module, will have to give it an address
    byte addr = ++_activeModules + I2C_MIN_ADDR;
    //_logger->info("Adding new module to address: " + (String)addr);
    _modules[0].sendMessage("SET ADDR " + (String)addr);
    _modules[_activeModules] = Module(addr, _modules[0].getType());
  }
  _logger->info("Act modules: " + String(_activeModules));
  for (byte i = 1; i <= _activeModules; i++){
    _logger->info(_modules[i].getInfo());
//    _logger->info("Getting from " + (String)i + " addr " +  (String)_modules[i].getAddr() + " : " + (String)_modules[i].getInfo());
  }


/*  _modules[0].getInfo();
  pingAll();
  _modules[++_activeModules] = Module(_activeModules, _modules[0].getType());
  _modules[0].setAddr(_activeModules);
  moduleCleanUp();
  _modules[0].getInfo();*/
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

byte ModuleManager::getModuleNumber(){
  return _activeModules;
}

