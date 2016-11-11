package Clases;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 *
 * @author Carlos
 */
public class Vuelo {
    private int codigo_vuelo;
    private LocalDate fecha;
    private int precio;
    private LocalTime hora_salida;
    private LocalTime hora_llegada;
    private Aeropuerto origen;
    private Aeropuerto destino;
    private int conexion;
    private int asientos_libres;
    private int asientos_totales = 9;

    public Vuelo() {
    }

    public Vuelo(Aeropuerto origen, Aeropuerto destino, LocalDate fecha, LocalTime hora_salida, LocalTime hora_llegada, int precio) {
        this.fecha = fecha;
        this.precio = precio;
        this.hora_salida = hora_salida;
        this.hora_llegada = hora_llegada;
        this.origen = origen;
        this.destino = destino;
    }
    
    public int getCodigo_vuelo() {
        return codigo_vuelo;
    }

    public void setCodigo_vuelo(int codigo_vuelo) {    
        this.codigo_vuelo = codigo_vuelo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public LocalTime getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(LocalTime hora_salida) {
        this.hora_salida = hora_salida;
    }

    public LocalTime getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(LocalTime hora_llegada) {
        this.hora_llegada = hora_llegada;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public void setOrigen(Aeropuerto origen) {
        this.origen = origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
    }

    public int getConexion() {
        return conexion;
    }

    public void setConexion(int conexion) {
        this.conexion = conexion;
    }

    public int getAsientos_libres() {
        return asientos_libres;
    }

    public void setAsientos_libres(int asientos_libres) {
        this.asientos_libres = asientos_libres;
    }
    
    @Override
    public String toString() {
        return "Vuelo{" + "fecha=" + fecha + ", precio=" + precio + ", hora_salida=" + hora_salida + ", hora_llegada=" + hora_llegada + ", origen=" + origen + ", destino=" + destino + '}';
    }
}
