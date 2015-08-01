package edu.licenta.eniko.sqlite.model;

import java.util.Date;

/**
 * Created by Eniko on 6/11/2015.
 */
public class Module {

    private String id;
   // private String value;
 //   private Date received_at;
  //  private Room room;
   // private ModuleType type;

    public Module() {
    }

//    public Module(String value, Date received_at, Room room, ModuleType type) {
//        this.value = value;
//        this.received_at = received_at;
//        this.room = room;
//        this.type = type;
//    }
//
    public Module(String id){
        //, String value, Date received_at, Room room, ModuleType type) {
        this.id = id;
//        this.value = value;
//        this.received_at = received_at;
//        this.room = room;
//        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public Date getReceived_at() {
//        return received_at;
//    }
//
//    public void setReceived_at(Date received_at) {
//        this.received_at = received_at;
//    }
//
//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }
//
//    public ModuleType getType() {
//        return type;
//    }
//
//    public void setType(ModuleType type) {
//        this.type = type;
//    }
}
