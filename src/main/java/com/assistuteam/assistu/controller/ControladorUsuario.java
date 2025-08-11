package com.assistuteam.assistu.controller;

import java.util.List;

import com.assistuteam.assistu.model.entity.Alumno;
import com.assistuteam.assistu.model.entity.Usuario;
import com.assistuteam.assistu.model.repository.RepositorioAlumno;
import com.assistuteam.assistu.model.repository.RepositorioUsuario;

/** @author assistu_team **/

@SuppressWarnings("all")
public class ControladorUsuario extends Controlador<RepositorioUsuario, Usuario> {
    public ControladorUsuario() throws Exception {
        repositorio = new RepositorioUsuario();
    }

    @Override
    protected boolean validar(Usuario obj) throws Exception {
        if (obj.getId() < 0) throw new Exception("El ID del usuario es obligatorio");
        if (obj.getMatricula() == null || obj.getMatricula().isEmpty()) throw new Exception("La matrícula del usuario es obligatoria");
        if (obj.getContrasenia() == null || obj.getContrasenia().isEmpty()) throw new Exception("La contraseña del usuario es obligatoria");
        if (obj.getNombre() == null || obj.getNombre().isEmpty()) throw new Exception("El nombre del usuario es obligatorio");
        if (obj.getApellidoPaterno() == null || obj.getApellidoPaterno().isEmpty()) throw new Exception("El apellido paterno del usuario es obligatorio");
        if (obj.getApellidoMaterno() == null || obj.getApellidoMaterno().isEmpty()) throw new Exception("El apellido materno del usuario es obligatorio");
        if (obj.getCorreo() == null || obj.getCorreo().isEmpty()) throw new Exception("El correo del usuario es obligatorio");
        if (obj.getTipoUsuario() == null || obj.getTipoUsuario().isEmpty()) throw new Exception("El tipo de usuario es obligatorio");
        return true;
    }

    public void buscarPorNombre(String nombre) throws Exception {
        List<Usuario> usuarios = repositorio.leerTodos();
        usuarios.forEach(usuario -> {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println(usuario);
            }
        });
    }

    public void buscarPorMatricula(String matricula) throws Exception {
        List<Usuario> usuarios = repositorio.leerTodos();
        usuarios.forEach(usuario -> {
            if (usuario.getMatricula().equalsIgnoreCase(matricula)) {
                System.out.println(usuario);
            }
        });
    }

    public void buscarPorCorreo(String correo) throws Exception {
        List<Usuario> usuarios = repositorio.leerTodos();
        usuarios.forEach(usuario -> {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                System.out.println(usuario);
            }
        });
    }

    public void buscarPorRol(String tipoUsuario) throws Exception {
        List<Usuario> usuarios = repositorio.leerTodos();
        usuarios.forEach(usuario -> {
            if (usuario.getTipoUsuario().equalsIgnoreCase(tipoUsuario)) {
                System.out.println(usuario);
            }
        });
    }

    // lo puse
    public static Usuario login(String matricula, String contrasenia) {
        try {
            System.out.println("Login debug: '" + matricula + "' / '" + contrasenia + "'");
            Usuario u = new RepositorioUsuario().obtenerPorMatriculaYContrasenia(matricula, contrasenia);
            System.out.println("Resultado consulta: " + (u != null ? "Éxito" : "No encontrado"));
            return u;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Registra un usuario con cargo de alumno y su entidad Alumno asociada.
     * @return true si el registro fue exitoso, false en caso contrario
     */
    public boolean registrarAlumno(String matricula, String contrasenia, String nombre, String apellidoP, String apellidoM, String correo, int cuatrimestre, String grupo, String carrera) {
        try {
            Usuario usuario = new Usuario();
            usuario.setMatricula(matricula);
            usuario.setContrasenia(contrasenia);
            usuario.setNombre(nombre);
            usuario.setApellidoPaterno(apellidoP);
            usuario.setApellidoMaterno(apellidoM);
            usuario.setCorreo(correo);
            usuario.setTipoUsuario("alumno");

            boolean usuarioCreado = repositorio.crear(usuario);
            if (!usuarioCreado) return false;
            int idUsuario = usuario.getId();

            Alumno alumno = new Alumno();
            alumno.setCuatrimestre(cuatrimestre);
            alumno.setGrupo(grupo);
            alumno.setCarrera(carrera);
            alumno.setId(idUsuario);

            RepositorioAlumno repoAlumno = new RepositorioAlumno();
            boolean alumnoCreado = repoAlumno.crear(alumno);

            return alumnoCreado;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}