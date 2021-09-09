package py.una.client.udp;



import py.una.db.model.RegistroMeteorologico;
import py.una.db.request.RequestUDP;
import py.una.db.utils.Utils;

import java.io.*;
import java.net.*;


class UDPClient {
    private static String direccionServidor = "127.0.0.1";
    private static final int puertoServidor = 9876;
    private static  InetAddress IPAddress;

    public static void main(String[] a) {
        if (a.length > 0) {
            direccionServidor = a[0];
        }

        try {

            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            IPAddress = InetAddress.getByName(direccionServidor);
            System.out.println("Intentando conectar a = " + IPAddress + ":" + puertoServidor +  " via UDP...");

            Integer operation;
            do{
                clear();
                System.out.println("Ingrese la operacion a realizar (0 para terminar)");
                System.out.println("1.Enviar datos de sensores meteorológicos");
                System.out.println("2.Consultar temperatura");
                System.out.println("3.Consultar promedio de temperatura");
                operation = Integer.parseInt(inFromUser.readLine());
                if(operation > 0 && operation < 4 ){
                    String message = manageOperation(operation, inFromUser);
                    sendData(clientSocket, message);
                    inFromUser.readLine();
                }
            }while (!operation.equals(0));


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String manageOperation(Integer operation,  BufferedReader inFromUser) throws IOException {
        if(operation.equals(1)){
            return Operaciones.enviarDatos(inFromUser);
        }else if (operation.equals(2)){
            return Operaciones.consultarTemperatura(inFromUser);
        }else{
            return Operaciones.consultarPromedio(inFromUser);
        }
    }

    public static void sendData( DatagramSocket clientSocket, String data) throws IOException {
        byte [] dataBytes = data.getBytes();
        byte[] receiveData = new byte[1024];

        System.out.println("Enviar " + data + " al servidor. ("+ dataBytes.length + " bytes)");
        DatagramPacket sendPacket =
                new DatagramPacket(dataBytes, dataBytes.length, IPAddress, puertoServidor);

        clientSocket.send(sendPacket);

        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);

        System.out.println("Esperamos si viene la respuesta.");

        //Vamos a hacer una llamada BLOQUEANTE entonces establecemos un timeout maximo de espera
        clientSocket.setSoTimeout(10000);

        try {
            // ESPERAMOS LA RESPUESTA, BLOQUENTE
            clientSocket.receive(receivePacket);

            String respuesta = new String(receivePacket.getData());

            InetAddress returnIPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            System.out.println("Respuesta desde =  " + returnIPAddress + ":" + port);
            System.out.println(respuesta);


        } catch (SocketTimeoutException ste) {

            System.out.println("TimeOut: El paquete udp se asume perdido.");
        }
    }

    public static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
} 

