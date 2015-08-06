/*
  WipeCommand.cpp - Library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/

#include "WipeCommand.h"

void WipeCommand::parse(String command){

  firstTime = true;
};

void WipeCommand::sendData() {};

boolean WipeCommand::available() {
  boolean ret = firstTime;
  firstTime = false;
  return ret;
};
char WipeCommand::read() {
   File root;
   root = SD.open("/");
   rm(root, "/");
   root.close();
   return '/0';
};

void WipeCommand::rm(File dir, String tempPath) {
  while(true) {
    File entry =  dir.openNextFile();
    String localPath;

    if (entry) {
      if (entry.isDirectory()) {
        localPath = tempPath + entry.name() + "/" + '\0';
        char folderBuf[localPath.length()];
        localPath.toCharArray(folderBuf, localPath.length());
        rm(entry, folderBuf);
        SD.rmdir(folderBuf);
      }
      else {
        localPath = tempPath + entry.name() + '\0';
        char charBuf[localPath.length()];
        localPath.toCharArray(charBuf, localPath.length() );
        SD.remove(charBuf);
      }
    } 
    else {
      break; // break out of recursion when finished
    }
  }
}
