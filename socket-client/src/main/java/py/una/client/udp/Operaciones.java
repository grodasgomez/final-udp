package py.una.client.udp;

import py.una.db.model.RegistroMeteorologico;
import py.una.db.request.RequestUDP;
import py.una.db.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;

public class Operaciones {

    public static String enviarDatos( BufferedReader inFromUser) throws IOException {
        RegistroMeteorologico r = new RegistroMeteorologico();

        String data;
        System.out.print("Ingrese el id de la estacion\n");
        data = inFromUser.readLine();
        r.setIdEstacion( Long.parseLong(data));

        System.out.print("Ingrese el id de la ciudad\n");
        data = inFromUser.readLine();
        r.setCiudad( Long.parseLong(data));

        System.out.print("Ingrese el porcentaje de humedad\n");
        data = inFromUser.readLine();
        r.setPorcentajeHumedad( Integer.parseInt(data));

        System.out.print("Ingrese la temperatura\n");
        data = inFromUser.readLine();
        r.setTemperatura( Integer.parseInt(data));

        System.out.print("Ingrese la velocidad del viento\n");
        data = inFromUser.readLine();
        r.setVelocidadViento( Float.parseFloat(data));

        r.setFecha(Utils.validFecha(""));
        r.setHora(Utils.validHora(""));

        RequestUDP requestUDP = new RequestUDP();
        requestUDP.setOperacion(1);
        requestUDP.setData(r);
        return Utils.toJson(requestUDP);
    }

    public static String consultarTemperatura( BufferedReader inFromUser) throws IOException {
        RequestUDP requestUDP = new RequestUDP();
        requestUDP.setOperacion(2);

        System.out.print("Ingrese el id de la ciudad a consultar temperatura\n");
        Long ciudadId = Long.parseLong(inFromUser.readLine());
        requestUDP.setData(ciudadId);
        return Utils.toJson(requestUDP);
    }

    public static String consultarPromedio( BufferedReader inFromUser) throws IOException {
        RequestUDP requestUDP = new RequestUDP();
        requestUDP.setOperacion(3);

        System.out.print("Ingrese la fecha a consultar promedio (DD/MM/YYYY)\n");
        String fecha = inFromUser.readLine();
        requestUDP.setData(Utils.validFecha(fecha));
        return Utils.toJson(requestUDP);
    }
}
