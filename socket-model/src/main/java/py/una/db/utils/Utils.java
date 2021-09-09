package py.una.db.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;

public class Utils {

    public static Date getFecha(String str) throws ParseException {
        if(str.equals(""))
            return new Date();
        return new SimpleDateFormat("dd/MM/yyyy").parse(str);
    }

    public static String validFecha(String str) {
        if(str.equals(""))
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        return str;
    }

    public static String validHora(String str)  {
        if(str.equals(""))
            return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return str;
    }

    public static LocalTime getHora(String str) {
        if(str.equals(""))
            return LocalTime.now();
        return LocalTime.parse(str);
    }

    public static String toJson(Object data) throws JsonProcessingException {
        return  new ObjectMapper().writeValueAsString(data);
    }
}
