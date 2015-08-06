/*
  Date.cpp - Library for TODO
  Created by TODO, Aug 5, 2015.
  TODO: Released into the public domain.
*/

#include "Date.h"

Date::Date(int y, int m, int d){
  _y = y;
  _m = m;
  _d = d;
}

Date::Date(String date){
  // Date should be like: YYYY/MM/DD
  _y = (date[0] - '0') * 1000 + (date[1] - '0') * 100 + (date[2] - '0') * 10 + (date[3] - '0');
  _m = (date[5] - '0') * 10 + (date[6] - '0');
  _d = (date[8] - '0') * 10 + (date[9] - '0');
}

Date Date::operator++(int){
  Date x(_y, _m, _d);
  _d++;
  
  if (((_d > 31) && ((_m == 1) || (_m == 3) || (_m == 5) || (_m == 7) || (_m == 8) || (_m == 10) || (_m ==12))) || ((_d > 30) && ((_m == 4) || (_m == 6) || (_m == 9) || (_m == 11)))){
    _m++;
    _d = 1;
  } else if (_m ==2) {
    if ((((_y % 4 == 0) || (_y % 100 == 0)) && (_y % 400 != 0)) && (_d > 29)){
      _m++;
      _d = 1;
    } else if (_d > 28) {
      _m++;
      _d = 1;
    }
  }
  if (_m > 12) {
    _y++;
    _m = 1;
  }
  return x;
}

boolean Date::operator<(const Date a){
  return (_y < a._y) || 
         ((_y == a._y) && ((_m < a._m) || 
         ((_m == a._m) && (_d < a._d))));
}

boolean Date::operator<=(const Date a){
  return (_y < a._y) || 
         ((_y == a._y) && ((_m < a._m) || 
         ((_m == a._m) && (_d <= a._d))));
}

boolean Date::operator>(const Date a){
  return (_y > a._y) || 
         ((_y == a._y) && ((_m > a._m) || 
         ((_m == a._m) && (_d > a._d))));
}

boolean Date::operator>=(const Date a){
  return (_y > a._y) || 
         ((_y == a._y) && ((_m > a._m) || 
         ((_m == a._m) && (_d >= a._d))));
}

Date& Date::operator=(const Date &a){
  _y = a._y;
  _m = a._m;
  _d = a._d;
}  

String Date::toStr(){
  return (String)_y + "/" + (_m<10?"0":"") + (String)_m + "/" + (_d<10?"0":"") + (String)_d;
}

