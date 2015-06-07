/*
  ModuleManager.h - Library for TODO
  Created by TODO, May 15, 2015.
  TODO: Released into the public domain.
*/
#ifndef ModuleManager_h
#define ModuleManager_h

#include "Arduino.h"
#include "Constants.h"
#include "Module.h"
#include "Logger.h"

class ModuleManager
{
  public:
    ModuleManager();
    ModuleManager(Logger* logger);
    void init();
//    String getMessage(String msg);
    String getInfo(byte moduleNumber);
    void updateModules();
    byte getModuleNumber();
//    byte getType();
//    byte getAddr();
  private:
//    void init();
    Module _modules[MAX_MODULES_NUMBER];
    void loadModules();
    void saveModules();
    void moduleCleanUp();
    byte pingAll();
    byte _activeModules;
    Logger * _logger;
};

#endif
