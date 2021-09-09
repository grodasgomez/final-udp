package py.una.server.udp;

import java.net.*;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import py.una.db.model.RegistroMeteorologico;
import py.una.server.dao.RegistroMeteorologicoDAO;
import py.una.db.request.RequestUDP;

public class UDPServer {

    static RegistroMeteorologicoDAO dao = new RegistroMeteorologicoDAO();

    public static void main(String[] a){
        
        // Variables
        int puertoServidor = 9876;

        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Sistemas Distribuidos - UDP ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

			
            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);


                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
                String datoRecibido = new String(receivePacket.getData());
                datoRecibido = datoRecibido.trim();
                System.out.println("DatoRecibido: " + datoRecibido );
                ObjectMapper objectMapper = new ObjectMapper();
                RequestUDP request = objectMapper.readValue(datoRecibido, RequestUDP.class);

                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();

                System.out.println("De : " + IPAddress + ":" + port);
                System.out.println(request.toString());


                // Enviamos la respuesta inmediatamente a ese mismo cliente
                // Es no bloqueante
                sendData = manageRequest(request);
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);

            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }


    private static byte[] manageRequest(RequestUDP requestUDP) throws JsonProcessingException {
        Integer operation = requestUDP.getOperacion();
        ObjectMapper objectMapper = new ObjectMapper();
        String response = "";
        if(operation.equals(1)){
            RegistroMeteorologico r = objectMapper.convertValue(requestUDP.getData(), RegistroMeteorologico.class);
            response = dao.insertarRegistro(r );
        }else if(operation.equals(2)){
            Integer temperatura = dao.consultarTemperatura(objectMapper.convertValue(requestUDP.getData(), Long.class));
            response = objectMapper.writeValueAsString(temperatura);
        }else if (operation.equals(3)){
            Float prom = dao.temperaturaPromedio(objectMapper.convertValue(requestUDP.getData(), String.class));
            response = objectMapper.writeValueAsString(prom);
        }

        return response.getBytes(StandardCharsets.UTF_8);
    }
}  

