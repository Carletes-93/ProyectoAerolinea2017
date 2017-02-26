package Clases;

public class Aeropuerto {
    private int cod_aer;
    private String ciudad;
    private String pais;
    private String iata;

    public Aeropuerto() {
    }

    public Aeropuerto(String ciudad, String pais) {
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public int getCod_aer() {
        return cod_aer;
    }

    public void setCod_aer(int cod_aer) {
        this.cod_aer = cod_aer;
    }

    @Override
    public String toString() {
        return "Aeropuerto{" + "ciudad=" + ciudad + ", pais=" + pais + ", iata=" + iata + '}';
    }
}
