/*
  Date.h - Linked library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/
#ifndef Date_h
#define Date_h

#include "Arduino.h"

class Date{
public:
  Date(int, int, int);
  Date(String);
  Date();
//  Date operator++(int);
  void inc();
  Date& operator=(const Date &a);  
  boolean operator>(Date);
  boolean operator>=(Date);
  boolean smallerThan(Date*);  
  boolean operator<=(Date);
  int getYear();
  int getMonth();
  int getDay();
  String toStr();
private:
  int _y, _m, _d;
};

#endif
