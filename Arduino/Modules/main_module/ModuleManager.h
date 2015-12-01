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
#include "RealTime.h"
#include "SD.h"

class ModuleManager
{
  public:
    void init();
//    String getMessage(String msg);
    void sendToModule(String moduleSN, String msg);
    String getInfo(byte moduleNumber);
    void updateModules();
    byte getModuleNumber();
    static ModuleManager& getInstance();
//    byte getType();
//    byte getAddr();
    void setLogger(Logger *logger);
    void setRTC(RealTime *RTC);
  private:
    //ModuleManager();
//    ModuleManager(Logger* logger, RealTime* RTC);
//    void init();
    Module _modules[MAX_MODULES_NUMBER];
    void loadModules();
    void saveModules();
    void moduleCleanUp();
    byte pingAll();
    byte _activeModules;
    Logger * _logger;
    RealTime * _RTC;
    static ModuleManager *_instance;
};

#endif
