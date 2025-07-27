package Modelos;

public class Usuario extends Persona {
    private String nombre;

    public Usuario(int id, long dni, String correo, long numero, String direccion,
                   String nombre) {
        super(id, dni, correo, numero, direccion);
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
}
