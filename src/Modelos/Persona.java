package Modelos;

public abstract class Persona {
    protected int id;
    protected long dni;
    protected String correo;
    protected long numero;
    protected String direccion;

    public Persona(int id, long dni, String correo, long numero, String direccion) {
        this.id = id;
        this.dni = dni;
        this.correo = correo;
        this.numero = numero;
        this.direccion = direccion;
    }

    public int getId() { return id; }
    public long getDni() { return dni; }
    public String getCorreo() { return correo; }
    public long getNumero() { return numero; }
    public String getDireccion() { return direccion; }

    public void setCorreo(String correo) { this.correo = correo; }
    public void setNumero(long numero) { this.numero = numero; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
