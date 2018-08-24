/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp;

import java.io.Serializable;
///No projeto TCP
/**
 *
 * @author jose
 */
public class Hello implements Serializable {
    
    
     byte commandType = 0;
     short updPort = 12344;

    public byte getCommandType() {
        return commandType;
    }

    public void setCommandType(byte commandType) {
        this.commandType = commandType;
    }

    public short getUpdPort() {
        return updPort;
    }

    public void setUpdPort(short updPort) {
        this.updPort = updPort;
    }
     
     
    
}
