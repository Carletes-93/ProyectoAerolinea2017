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
    private String num_vuelo;
    private String avion;
    private LocalTime duracion;

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

    public int getAsientos_totales() {
        return asientos_totales;
    }

    public void setAsientos_totales(int asientos_totales) {
        this.asientos_totales = asientos_totales;
    }

    public String getNum_vuelo() {
        return num_vuelo;
    }

    public void setNum_vuelo(String num_vuelo) {
        this.num_vuelo = num_vuelo;
    }

    public String getAvion() {
        return avion;
    }

    public void setAvion(String avion) {
        this.avion = avion;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }
    
    @Override
    public String toString() {
        return "Vuelo{" + "fecha=" + fecha + ", precio=" + precio + ", hora_salida=" + hora_salida + ", hora_llegada=" + hora_llegada + ", origen=" + origen + ", destino=" + destino + '}';
    }
}
