package py.una.db.model;

/**
 * Clase que guarda los datos de un registro meteorologico
 */
public class RegistroMeteorologico {
    private Long idEstacion;
    private Long ciudad;
    private Integer porcentajeHumedad;
    private Integer temperatura;
    private Float velocidadViento;
    private String fecha;
    private String hora;

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Long getCiudad() {
        return ciudad;
    }

    public void setCiudad(Long ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getPorcentajeHumedad() {
        return porcentajeHumedad;
    }

    public void setPorcentajeHumedad(Integer porcentajeHumedad) {
        this.porcentajeHumedad = porcentajeHumedad;
    }

    public Integer getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Integer temperatura) {
        this.temperatura = temperatura;
    }

    public Float getVelocidadViento() {
        return velocidadViento;
    }

    public void setVelocidadViento(Float velocidadViento) {
        this.velocidadViento = velocidadViento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Registro recibido: {" +
                "idEstacion=" + idEstacion +
                ", ciudad=" + ciudad +
                ", porcentajeHumedad=" + porcentajeHumedad +
                ", temperatura=" + temperatura +
                ", velocidadViento=" + velocidadViento +
                ", fecha=" + fecha +
                ", hora=" + hora +
                '}';
    }
}
