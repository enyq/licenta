/*
  RealTime.h - Library for TODO
  Created by TODO, July 7, 2015, by refactoring version available on arduino.cc by user "Krodal".
  TODO: Released into the public domain.
  
  This real-time class is meant to read time from the RTC module, and to set it by the user.
*/


#include "RealTime.h"


int bcd2bin(int h, int l){
  return ((h)*10) + (l);
}

int bin2bcd_h(int x){
  return (x)/10;
}

//#define bin2bcd_h(x)   ((x)/10)

int bin2bcd_l(int x){
  return (x)%10;
}

RealTime::RealTime(byte CLK, byte DAT, byte RST){
  _CLK = CLK;
  _DAT = DAT;
  _RST = RST;
}

void RealTime::init(){
  DS1302_write (0x8E, 0);

  // Disable Trickle Charger.
  DS1302_write (0x90, 0x00);
}

int RealTime::getSeconds(){
  DS1302_clock_burst_read( (uint8_t *) &rtc);
  return bcd2bin(rtc.Seconds10, rtc.Seconds);
}

int RealTime::getMinutes(){
  DS1302_clock_burst_read( (uint8_t *) &rtc);
  return bcd2bin(rtc.Minutes10, rtc.Minutes);
}

int RealTime::getHours(){
  DS1302_clock_burst_read( (uint8_t *) &rtc);
  return bcd2bin( rtc.h24.Hour10, rtc.h24.Hour);
}

int RealTime::getDays(){
  DS1302_clock_burst_read( (uint8_t *) &rtc);
  return bcd2bin( rtc.Date10, rtc.Date);
}


int RealTime::getDoW(){
  DS1302_clock_burst_read( (uint8_t *) &rtc);
  return rtc.Day;
}

int RealTime::getMonths(){
  DS1302_clock_burst_read( (uint8_t *) &rtc);
  return bcd2bin( rtc.Month10, rtc.Month);
}
 
int RealTime::getYears(){
  return 2000 + bcd2bin( rtc.Year10, rtc.Year);
}

String RealTime::getDateTime(){
  String buffer = "";
  
  int months = getMonths();
  int days = getDays();
  int hours = getHours();
  int minutes = getMinutes();
  int seconds = getSeconds();

  buffer += (String)getYears() + "/" + (months < 10 ? "0" : "") +  (String)months + "/" + (days < 10 ? "0" : "") + (String)days + " " + (hours < 10 ? "0" : "") + (String)hours +
            (minutes < 10 ? "0" : "") + ":" + (String)minutes + ":" + (seconds < 10 ? "0" : "") + (String)seconds;
  
  return buffer;
}

String RealTime::getDate(){
  String buffer = "";
  
  int months = getMonths();
  int days = getDays();

  buffer += (String)getYears() + "/" + (months < 10 ? "0" : "") +  (String)months + "/" + (days < 10 ? "0" : "") + (String)days;
  
  return buffer;
}


void RealTime::setTime(int seconds, int minutes, int hours, int dayOfWeek, int dayOfMonth, int month, int year){
  memset ((char *) &rtc, 0, sizeof(rtc));

  rtc.Seconds    = bin2bcd_l( seconds);
  rtc.Seconds10  = bin2bcd_h( seconds);
  rtc.CH         = 0;      // 1 for Clock Halt, 0 to run;
  rtc.Minutes    = bin2bcd_l( minutes);
  rtc.Minutes10  = bin2bcd_h( minutes);
  rtc.h24.Hour   = bin2bcd_l( hours);
  rtc.h24.Hour10 = bin2bcd_h( hours);
  rtc.h24.hour_12_24 = 0; // 0 for 24 hour format
  rtc.Date       = bin2bcd_l( dayOfMonth);
  rtc.Date10     = bin2bcd_h( dayOfMonth);
  rtc.Month      = bin2bcd_l( month);
  rtc.Month10    = bin2bcd_h( month);
  rtc.Day        = dayOfWeek;
  rtc.Year       = bin2bcd_l( year - 2000);
  rtc.Year10     = bin2bcd_h( year - 2000);
  rtc.WP = 0;  

  DS1302_clock_burst_write( (uint8_t *) &rtc);
}

void RealTime::DS1302_clock_burst_read( uint8_t *p)
{
  int i;

  _DS1302_start();

  _DS1302_togglewrite( 0xBF, true);  

  for( i=0; i<8; i++)
  {
    *p++ = _DS1302_toggleread();
  }
  _DS1302_stop();
}


void RealTime::DS1302_clock_burst_write( uint8_t *p)
{
  int i;

  _DS1302_start();

  _DS1302_togglewrite( 0xBE, false);  

  for( i=0; i<8; i++)
  {
    _DS1302_togglewrite( *p++, false);  
  }
  _DS1302_stop();
}


uint8_t RealTime::DS1302_read(int address)
{
  uint8_t data;

  bitSet( address, 0);  

  _DS1302_start();
  _DS1302_togglewrite( address, true);  
  data = _DS1302_toggleread();
  _DS1302_stop();

  return (data);
}

void RealTime::DS1302_write( int address, uint8_t data)
{
  // clear lowest bit (read bit) in address
  bitClear( address, 0);   

  _DS1302_start();
  _DS1302_togglewrite( address, false); 
  _DS1302_togglewrite( data, false); 
  _DS1302_stop();  
}


void RealTime::_DS1302_start( void)
{
  digitalWrite( _RST, LOW); // default, not enabled
  pinMode( _RST, OUTPUT);  

  digitalWrite( _CLK, LOW); // default, clock low
  pinMode( _CLK, OUTPUT);

  pinMode( _DAT, OUTPUT);

  digitalWrite( _RST, HIGH); // start the session
  delayMicroseconds( 4);           // tCC = 4us
}

void RealTime::_DS1302_stop(void)
{
  // Set CE low
  digitalWrite( _RST, LOW);

  delayMicroseconds( 4);           // tCWH = 4us
}

uint8_t RealTime::_DS1302_toggleread( void)
{
  uint8_t i, data;

  data = 0;
  for( i = 0; i <= 7; i++)
  {
    digitalWrite( _CLK, HIGH);
    delayMicroseconds( 1);

    digitalWrite( _CLK, LOW);
    delayMicroseconds( 1);        // tCL=1000ns, tCDD=800ns

    bitWrite( data, i, digitalRead( _DAT)); 
  }
  return( data);
}

void RealTime::_DS1302_togglewrite( uint8_t data, uint8_t release)
{
  int i;

  for( i = 0; i <= 7; i++)
  { 
    digitalWrite( _DAT, bitRead(data, i));  
    delayMicroseconds( 1);     // tDC = 200ns
    digitalWrite( _CLK, HIGH);     
    delayMicroseconds( 1);     // tCH = 1000ns, tCDH = 800ns

    if( release && i == 7)
    {
      pinMode( _DAT, INPUT);
    }
    else
    {
      digitalWrite( _CLK, LOW);
      delayMicroseconds( 1);       // tCL=1000ns, tCDD=800ns
    }
  }
}
