package modelo.modeloTransacciones;

import modelo.modeloPersona.Cliente;

public class ModeloTransacciones {
    private int idTransaccion;
    private Cliente cliente;
    private double monto;
    private String tipoID;

    public ModeloTransacciones(int idTransaccion, Cliente cliente, double monto, String tipoID) {
        this.idTransaccion = idTransaccion;
        this.cliente = cliente;
        this.monto = monto;
        this.tipoID = tipoID;
    }

    public ModeloTransacciones() {
    }

    public int getIdTransaccion() {
        return idTransaccion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getMonto() {
        return monto;
    }

    public String getTipoID() {
        return tipoID;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setTipoID(String tipoID) {
        this.tipoID = tipoID;
    }
}
