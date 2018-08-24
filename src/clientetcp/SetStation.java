/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp;

import java.io.Serializable;

/**
 *
 * @author jose
 */
public class SetStation implements Serializable{
     byte commandType = 1;
     short stationNumber;
     short station;

    public byte getCommandType1() {
        return commandType;
    }

    public void setCommandType1(byte commandType1) {
        this.commandType = commandType1;
    }

    public short getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(short stationNumber) {
        this.stationNumber = stationNumber;
    }

    public short getStation() {
        return station;
    }

    public void setStation(short station) {
        this.station = station;
    }
    
    
    
}
