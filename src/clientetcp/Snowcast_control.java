/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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

    static Hello hello = new Hello();

    static SetStation setStation = new SetStation();

    static short stationNumber;
    static short station;
    static Socket cliente;
    static Serializacao tr;

    public static void conectar(String ipServidor,String portaServidor, String portaUDP) {

        hello.setCommandType((byte) 0);
        hello.setUpdPort((short) Integer.parseInt(portaUDP));

        setStation.setCommandType1((byte) 1);

        tr = new Serializacao();

        try {

            cliente = new Socket(ipServidor, Integer.parseInt(portaServidor));

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (!cliente.isClosed()) {
                        finalizar();
                    }

                }
            });
            System.out.println("ENVIANDO COMANDO HELLO");
            DataOutputStream enviar = new DataOutputStream(cliente.getOutputStream());
            byte[] dadosHello = tr.serialize(hello);

            enviar.writeInt(dadosHello.length);
            enviar.write(dadosHello);
            enviar.flush();

            DataInputStream receber = new DataInputStream(cliente.getInputStream());

            int tamanho = receber.readInt();

            byte[] dadosWelcome = new byte[tamanho];

            receber.read(dadosWelcome, 0, tamanho);
            Object comando = tr.deserialize(dadosWelcome);

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
                    finalizar();
                }

            }

           
            Scanner teclado = new Scanner(System.in);
            DataOutputStream enviarSetStation;
            DataInputStream receberAnnounce;

            boolean controlador = true;
            while (controlador) {
                System.out.println("Digite um número da estação:");
                System.out.println("Digite Q para finalizar:");

                String number = teclado.nextLine();

                if (number.equals("Q") || number.equals("q")) {
                    System.exit(0);

                } else {

                    stationNumber = (short) Integer.parseInt(number);

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
                           System.exit(0);

                        }

                    } else if (comandoAnnInvalid instanceof Announce) {

                        Announce announceDecodificador = (Announce) tr.deserialize(dadosRAnnounce);

                        if ((byte) announceDecodificador.getReplayType() == 1) {
                            System.out.println("ANNOUNCE RECEBIDO COM SUCESSO");
                            System.out.println("Nome da Música escolhida:" + String.valueOf(announceDecodificador.getSongName()));
                        }
                    }

                }
            }

        } catch (IOException ex) {

            System.out.println("O servidor está fora do ar");

        } catch (ClassNotFoundException ex) {
           
         Logger.getLogger(Snowcast_control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void finalizar() {
        System.out.println("Finalizando....");
        DataOutputStream finalizar;
        try {
            finalizar = new DataOutputStream(cliente.getOutputStream());

            Finalizar fecharConexao = new Finalizar();

            byte dadosFinal[] = tr.serialize(fecharConexao);

            finalizar.writeInt(dadosFinal.length);
            finalizar.write(dadosFinal);
            finalizar.flush();

            cliente.close();
            
        } catch (IOException ex) {
            System.out.println("Não foi possível enviar o finalizar Servidor está fora do ar");
            //Logger.getLogger(Snowcast_control.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

        String ipServidor = null, portaServidor = null, portaUDP = null;

        if (args.length == 0) {
            System.out.println("Digite os parâmetros válidos");

        } else {

            ipServidor = args[0];
            portaServidor = args[1];
            portaUDP = args[2];

        }

        conectar(ipServidor,portaServidor,portaUDP);
    }

}
