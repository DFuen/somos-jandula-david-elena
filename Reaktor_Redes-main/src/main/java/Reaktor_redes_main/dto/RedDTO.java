package Reaktor_redes_main.dto;

public class RedDTO {
    private String SSID;
    private String password;
    private String configuration; /* MODIFICAR CUANTO TENGAMOS OBJETO*/


    public RedDTO(String SSID, String password, String configuracion) {
        this.SSID = SSID;
        this.password = password;
        this.configuration = configuracion;
    }

    public RedDTO(){

    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }
}
