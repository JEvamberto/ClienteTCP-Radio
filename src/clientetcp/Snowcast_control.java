/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.Announce;
import servidor.InvalidCommand;
import servidor.Welcome;

/**
 *
 * @author jose
 */
public class Snowcast_control {

    /*1. Hello:
uint8_t commandType = 0;
uint16_t udpPort;
2. SetStation:
uint8_t commandType = 1;
uint16_t stationNumber;
     */
    //Comando Hello
    static Hello hello = new Hello();

    /* static byte commandType = 0;
    static short updPort = 12344;*/
    //SetStation
    static SetStation setStation = new SetStation();

    /* static byte commandType1 = 1;*/
    static short stationNumber;
    static short station;

    public static void conectar() {
        hello.setCommandType((byte) 0);
        hello.setUpdPort((short) 12344);
        Hello hello1 = new Hello();
        hello1.setCommandType((byte) 0);
        hello1.setUpdPort((short) 12344);
        setStation.setCommandType1((byte) 1);
        Serializacao tr = new Serializacao();
        try {

            Socket cliente = new Socket("192.168.0.147", 12333);
            System.out.println("ENVIANDO COMANDO HELLO");
            DataOutputStream enviar = new DataOutputStream(cliente.getOutputStream());
            //System.out.println("CommandType");

            // System.out.println(updPort);
            byte[] dadosHello = tr.serialize(hello1);

            enviar.writeInt(dadosHello.length);
            enviar.write(dadosHello);
            enviar.flush();

            DataInputStream receber = new DataInputStream(cliente.getInputStream());

            //Servidor enviando para o cliente(handShake)
            int tamanho = receber.readInt();

            byte[] dadosWelcome = new byte[tamanho];

            receber.read(dadosWelcome, 0, tamanho);
            Object comando = tr.deserialize(dadosWelcome);
            //  Welcome welcome = (Welcome)tr.deserialize(dadosWelcome);

            if (comando instanceof Welcome) {

                Welcome welcome = (Welcome) comando;
                if (welcome.getReplyType() == 0) {

                    System.out.println("COMANDO WELCOME RECEBIDO COM SUCESSSO");
                    station = (short) welcome.getNumStations();
                    System.out.println("Estação");
                    for (int i = 0; i < station; i++) {
                        System.out.print(i + " ");
                    }
                    System.out.println("");
                } else {

                    System.out.println("FALHA: commandType do Welcome diferente de 0 ");
                    cliente.close();
                    System.exit(1);
                }

            } else if (comando instanceof InvalidCommand) {
                InvalidCommand icommand = (InvalidCommand) comando;

                if (icommand.getInvalidreplyType() == 2) {
                    System.out.println(String.valueOf(icommand.getInvalidreplyString()));
                    System.out.println("Fechando a conexão com o servidor...");
                    cliente.close();
                    System.exit(0);
                }

            }

            char g = '1';
              Scanner teclado = new Scanner(System.in);
              DataOutputStream enviarSetStation ;
              DataInputStream receberAnnounce;
              
              
/*
            System.out.println("Escolha a estação:");
            Scanner teclado = new Scanner(System.in);

            stationNumber = teclado.nextShort();
            setStation.setStationNumber(stationNumber);
            //veririfcar se estação é válida
            //COmandoSetStation

            DataOutputStream enviarSetStation = new DataOutputStream(cliente.getOutputStream());

            byte[] dadosSetStation = tr.serialize(setStation);

            enviarSetStation.writeInt(dadosSetStation.length);
            enviarSetStation.write(dadosSetStation);
            enviarSetStation.flush();

            //Receber Annuncead
            //Anunned
            DataInputStream receberAnnounce = new DataInputStream(cliente.getInputStream());

            int tamanho2 = receberAnnounce.readInt();

            byte[] dadosAnnounce = new byte[tamanho2];

            receberAnnounce.read(dadosAnnounce, 0, tamanho2);

            Object comandoAnnounceInvalid = tr.deserialize(dadosAnnounce);

            if (comandoAnnounceInvalid instanceof InvalidCommand) {

                InvalidCommand comandoInvalidoA = new InvalidCommand();

                System.out.println(comandoInvalidoA.getInvalidreplyString());
                cliente.close();
                System.exit(0);

            } else if (comandoAnnounceInvalid instanceof Announce) {
                Announce announce = (Announce) tr.deserialize(dadosAnnounce);
                if ((byte) announce.getReplayType() == 1) {
                    System.out.println("ANNOUNCE RECEBIDO COM SUCESSO");

                    System.out.println("Nome da Música escolhida:" + String.valueOf(announce.getSongName()));
                }

            }*/

            boolean controlador = true;
            while (controlador) {
                System.out.println("Digite um número da estação:");
                System.out.println("Digite Q para finalizar:");
                
                char p=teclado.nextLine().charAt(0);
                String number=Character.toString(p);
                
                //int d = teclado.nextInt();
                
                
                if (p=='Q'||p == 'q') {
                      System.out.println("Finalizando....");
                       DataOutputStream finalizar= new DataOutputStream(cliente.getOutputStream());
                        Finalizar fecharConexao= new Finalizar();
                       
                       byte dadosFinal []= tr.serialize(fecharConexao);
                       
                       finalizar.writeInt(dadosFinal.length);
                       finalizar.write(dadosFinal);
                       finalizar.flush();
                       
                       
                        
                        cliente.close();
                        System.exit(0);
                    
                }else  {
                        
                    stationNumber= (short)Integer.parseInt(number);
                    
                       //setStation
                        enviarSetStation = null;

                        enviarSetStation = new DataOutputStream(cliente.getOutputStream());

                        setStation.setStationNumber(stationNumber);

                        byte[] dadosESetStation = tr.serialize(setStation);
                        enviarSetStation.writeInt(dadosESetStation.length);
                        enviarSetStation.write(dadosESetStation);
                        enviarSetStation.flush();

                        System.out.println("");
                        //Enviar annunceo

                        receberAnnounce = null;

                        receberAnnounce = new DataInputStream(cliente.getInputStream());

                        int tamanho3 = receberAnnounce.readInt();
                        byte[] dadosRAnnounce = new byte[tamanho3];

                        receberAnnounce.read(dadosRAnnounce, 0, tamanho3);

                        Object comandoAnnInvalid = tr.deserialize(dadosRAnnounce);

                        if (comandoAnnInvalid instanceof InvalidCommand) {
                            InvalidCommand comandoInvalidodd = (InvalidCommand) tr.deserialize(dadosRAnnounce);
                            if (comandoInvalidodd.getInvalidreplyType() == 2) {

                                System.out.println(comandoInvalidodd.getInvalidreplyString());
                                cliente.close();
                                System.exit(0);

                            }

                        } else if (comandoAnnInvalid instanceof Announce) {

                            Announce announceDecodificador = (Announce) tr.deserialize(dadosRAnnounce);

                            if ((byte) announceDecodificador.getReplayType() == 1) {
                                System.out.println("ANNOUNCE RECEBIDO COM SUCESSO");
                                System.out.println("Nome da Música escolhida:" + String.valueOf(announceDecodificador.getSongName()));
                            }
                        }

                    
                    
                    
                    
                    
                }/*

                switch (d) {
                    case 1:
                        System.out.print("Escolha a nova estação:");

                        stationNumber = teclado.nextShort();

                     
                        break;

                    case 2:
                        System.out.println("Finalizando....");
                       DataOutputStream finalizar= new DataOutputStream(cliente.getOutputStream());
                        Finalizar fecharConexao= new Finalizar();
                       
                       byte dadosFinal []= tr.serialize(fecharConexao);
                       
                       finalizar.writeInt(dadosFinal.length);
                       finalizar.write(dadosFinal);
                       finalizar.flush();
                       
                       
                        
                        cliente.close();
                        System.exit(0);

                        break;

                }*/
            }

        } catch (IOException ex) {

            System.out.println("O servidor está fora do ar");
            //   Logger.getLogger(Snowcast_control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Snowcast_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        conectar();
    }

}
