package clienteApp.controlador;

import clienteApp.modeloCliente.ModeloCliente;

public class ControladorPrincipal {
    private ModeloCliente modeloCliente;
    private String identificacionCliente;
    private ControladorRetiro retiro;
    private ControladorLogin login;
    private ControladorCambioClave clave;
    private ControladorMenuPrincipal menuPrincipal;
    public final String SALIR = "salir";

    public ControladorPrincipal() {
        modeloCliente = new ModeloCliente();
        login = new ControladorLogin(this, modeloCliente);
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public void controladorMenuPrincipal(){
        menuPrincipal = new ControladorMenuPrincipal(this, modeloCliente);
    }

    public void controladorCambioClave(){
        clave = new ControladorCambioClave(this, modeloCliente);
    }

    public void controladorRetiro(){
        retiro = new ControladorRetiro(this, modeloCliente);
    }
}
