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
public class Finalizar implements Serializable {
    
    private short msgFinal=2;
    
    
    
   
    
 
    public short getMsgFinal(){
        return this.msgFinal;
    }
    
}
