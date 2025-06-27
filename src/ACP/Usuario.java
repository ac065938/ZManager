package ACP;

import java.io.Serializable;
import java.security.MessageDigest;
import java.io.File;


public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String usuario;
    private String claveHash;
    private String perfil;
    private boolean activo;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int vendedorNum;
    private String apodo;
    private String email;

    public Usuario() {}

    public Usuario(String usuario, String claveHash, String nombre, String apellidoPaterno, String apellidoMaterno,
                   String perfil, int vendedorNum, String apodo, String email, boolean activo) {
        this.usuario = usuario;
        this.claveHash = claveHash;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.perfil = perfil;
        this.vendedorNum = vendedorNum;
        this.apodo = apodo;
        this.email = email;
        this.activo = activo;
    }

    public static void eliminarArchivoViejo() {
        File archivo = new File("usuarios.dat");
        if (archivo.exists()) {
            archivo.delete();
        }
    }
    
    public static String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // Getters y Setters
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClaveHash() { return claveHash; }
    public void setClaveHash(String claveHash) { this.claveHash = claveHash; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public int getVendedorNum() { return vendedorNum; }
    public void setVendedorNum(int vendedorNum) { this.vendedorNum = vendedorNum; }

    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

}
