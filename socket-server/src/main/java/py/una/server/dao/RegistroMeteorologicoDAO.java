package py.una.server.dao;

import py.una.db.model.RegistroMeteorologico;
import py.una.db.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegistroMeteorologicoDAO {

    List<RegistroMeteorologico> registros = new ArrayList<>();

    public String insertarRegistro(RegistroMeteorologico registro){
        registros.add(registro);
        return "Registrado ingresado exitosamente";
    }

    /**
     * Retorna la temperatura del registro mas actual
     * @param ciudad Id de la ciudad
     * @return Temperatura actual de la ciudad, 0 si no hay ningún registro asociado
     */
    public int consultarTemperatura(Long ciudad){
        List<Integer> list =  registros.stream()
                .filter((registro -> registro.getCiudad().equals(ciudad) ))
                .sorted((this::compareTwoRegistros))
                .map(RegistroMeteorologico::getTemperatura).collect(Collectors.toList());
        return  list.isEmpty() ? 0 : list.get(list.size()-1);
    }

    /**
     * Calcula la temperatura promedio de un dia especifico
     * @param fecha Fecha asociada a los registros a calcular el promedio
     * @return Promedio del dia especificado, 0 si no existe ningun registro asociado
     */
    public float temperaturaPromedio(String fecha){
        float sum =  registros.stream()
                .filter((registro -> registro.getFecha().equals(fecha)))
                .map(RegistroMeteorologico::getTemperatura)
                .reduce(Integer::sum).orElse(0);
        return  sum ==0 ? 0 : (sum / registros.size());
    }


    /**
     * Compara dos registros meteorologicos
     * @param r1 Registro 1
     * @param r2 Registro 2
     * @return Retorna el resultado de comparar las fechas de cada registro
     */
    private int compareTwoRegistros(RegistroMeteorologico r1, RegistroMeteorologico r2)  {
        try {
            int result = Utils.getFecha(r1.getFecha()).compareTo(Utils.getFecha(r2.getFecha()));
            if(result == 0){
                return Utils.getHora(r1.getHora()).compareTo(Utils.getHora(r2.getHora()));
            }
            return result;
        }catch ( Exception e) {
            System.exit(1);
        }
        return 0;
    }

}
