/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author jose
 */
public class Snowcast_listener implements Runnable{

    private short porta;
    
    public Snowcast_listener(short porta){
        this.porta=porta;
        Thread t= new Thread(this);
        t.start();
    }

 

    @Override
    public void run() {
         try {
            

            int pacote = 50000;
        
            DatagramSocket receber = new DatagramSocket(porta);

            byte[] myBuffer = new byte[pacote];

            DatagramPacket pkgRecebe = new DatagramPacket(myBuffer, myBuffer.length);
            
            //new Player();
                while(true){
                    receber.receive(pkgRecebe);
                byte audio[] = pkgRecebe.getData();
                    //System.out.write(audio);
                    Player toca= new Player ( new ByteArrayInputStream(audio));
                    toca.play();
                   
                }
            
            
            
            
         
        } catch (SocketException ex) {
            Logger.getLogger(Snowcast_listener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | JavaLayerException ex) {
            Logger.getLogger(Snowcast_listener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
