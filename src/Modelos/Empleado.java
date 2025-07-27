package Modelos;

public class Empleado extends Persona {
    private String username;
    private String password;

    public Empleado(int id, long dni, String correo, long numero, String direccion,
                    String username, String password) {
        super(0, dni, correo, numero, direccion);
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDni(long dni) {
    }
}
