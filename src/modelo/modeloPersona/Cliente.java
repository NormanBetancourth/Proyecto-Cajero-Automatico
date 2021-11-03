package modelo.modeloPersona;

import java.util.Objects;

public class Cliente{
    private int cedula;
    private String nombre;
    private String usuario;
    private String clave;
    private int saldo;

    public Cliente(int cedula, String nombre, String usuario, String clave, int saldo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.usuario = usuario;
        this.clave = clave;
        this.saldo = saldo;
    }

    public Cliente() {
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "cedula=" + cedula +
                ", nombre='" + nombre + '\'' +
                ", usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
