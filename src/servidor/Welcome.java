/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.Serializable;

/**
 *
 * @author jose
 */
public class Welcome implements Serializable {
    private byte replyType ;
    private short numStations;

    public byte getReplyType() {
        return replyType;
    }

    public void setReplyType(byte replyType) {
        this.replyType = replyType;
    }

    public short getNumStations() {
        return numStations;
    }

    public void setNumStations(short numStations) {
        this.numStations = numStations;
    }
    
    
}
