package ACP;

import java.io.*;
import java.util.*;

public class UsuarioDAO implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String ARCHIVO_USUARIOS = "usuarios.dat";

    // Carga la lista de usuarios desde el archivo
    @SuppressWarnings("unchecked")
	public static List<Usuario> cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            List<Usuario> object2 = (List<Usuario>) ois.readObject();
			List<Usuario> object = object2;
			return object;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Guarda la lista de usuarios en el archivo
    public static void guardarUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Busca un usuario por nombre (case insensitive)
    public static Usuario obtenerUsuarioPorNombre(String usuarioInput) {
        for (Usuario u : cargarUsuarios()) {
            if (u.getUsuario() != null && u.getUsuario().equalsIgnoreCase(usuarioInput)) {
                return u;
            }
        }
        return null;
    }

    // Devuelve todos los usuarios (lista)
    public static List<Usuario> obtenerUsuarios() {
        return cargarUsuarios();
    }

    // Valida credenciales (usuario y contraseña sin hash)
    public static boolean validarCredenciales(String usuario, String claveSinHash) {
        Usuario u = obtenerUsuarioPorNombre(usuario);
        if (u == null || !u.isActivo()) return false;

        String hashIngresado = Usuario.hashSHA256(claveSinHash);
        return u.getClaveHash().equalsIgnoreCase(hashIngresado);
    }

    // Guarda o actualiza un usuario en la lista y la persistencia
    public static void guardarUsuario(Usuario nuevo) {
        if (nuevo == null || nuevo.getUsuario() == null || nuevo.getUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario a guardar no puede ser nulo ni vacío.");
        }

        List<Usuario> usuarios = cargarUsuarios();
        boolean actualizado = false;

        for (int i = 0; i < usuarios.size(); i++) {
            String usuarioExistente = usuarios.get(i).getUsuario();
            if (usuarioExistente != null && usuarioExistente.equalsIgnoreCase(nuevo.getUsuario())) {
                usuarios.set(i, nuevo);
                actualizado = true;
                break;
            }
        }

        if (!actualizado) {
            usuarios.add(nuevo);
        }

        guardarUsuarios(usuarios);
    }

}