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
public class Announce implements Serializable {
    private byte replayType = 1;
    private byte songNameSize;
    private char songName[] = new char[songNameSize];

    public byte getReplayType() {
        return replayType;
    }

    public void setReplayType(byte replayType) {
        this.replayType = replayType;
    }

    public byte getSongNameSize() {
        return songNameSize;
    }

    public void setSongNameSize(byte songNameSize) {
        this.songNameSize = songNameSize;
    }

    public char[] getSongName() {
        return songName;
    }

    public void setSongName(char[] songName) {
        this.songName = songName;
    }
    
    
    
}
