package clienteApp.controlador;

public class ControladorPrincipal {
    private controladorRetiro retiro;
    private controladorLogin login;
    private controladorCambioClave clave;
    private ControladorMenuPrincipal menuPrincipal;

    public ControladorPrincipal() {
        retiro = new controladorRetiro(this);
        login = new controladorLogin(this);
        clave = new controladorCambioClave(this);
        menuPrincipal = new ControladorMenuPrincipal(this);
        iniciar();
    }
    public void iniciar(){
        login.iniciar();
    }

}
