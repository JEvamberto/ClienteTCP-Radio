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
public class InvalidCommand implements Serializable{
    
     byte InvalidreplyType = 2;
     byte InvalidreplyStringSize;
     char InvalidreplyString[] = new char[InvalidreplyStringSize];

    public byte getInvalidreplyType() {
        return InvalidreplyType;
    }

    public void setInvalidreplyType(byte InvalidreplyType) {
        this.InvalidreplyType = InvalidreplyType;
    }

    public byte getInvalidreplyStringSize() {
        return InvalidreplyStringSize;
    }

    public void setInvalidreplyStringSize(byte InvalidreplyStringSize) {
        this.InvalidreplyStringSize = InvalidreplyStringSize;
    }

    public char[] getInvalidreplyString() {
        return InvalidreplyString;
    }

    public void setInvalidreplyString(char[] InvalidreplyString) {
        this.InvalidreplyString = InvalidreplyString;
    }
     
     
    
}
