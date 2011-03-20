package controllers;

import models.*;

import play.db.jpa.Blob;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
public class Concursantes extends Application {

    public static void index() {
        Usuario usuario = connected();

        try {
            if (usuario.rol != ApplicationRole.getByName("concursante"))
                forbidden("No eres un concursante.");
        } catch (NullPointerException e) {
            try {
                Secure.login();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        Concursante concursante = Concursante.find("byLogin", usuario.login).first();

        Concurso concurso = concursante.equipo.concurso;
        String equipo = concursante.equipo.toString();
        System.out.println(concurso);
        System.out.println(equipo);

        render(concursante, concurso, equipo);
    }

    public static void subirTrabajo(Long id, Blob trabajo) {
        // Obtener concursante de la base de datos
        Concursante concursante = Concursante.findById(id);

        // Verificar que el usuario puede subir su trabajo y que el concurso
        // está en progreso.
        if (concursante.equipo.concurso.tiempoFinal != null) {
            // TODO: Visualizar un error en la página web.
        }

        if (concursante.puedeSubirTrabajo != true) {
            // TODO
        }

        // Actualizar el trabajo
        concursante.crearTrabajo(trabajo);

        // Guardar todos los cambios
        concursante.save();

        // Volver al índice
        Concursantes.index();
    }

    public static void mostrarTrabajo(Long id) {
        // Obtener concursante de la base de datos
        Concursante concursante = Concursante.findById(id);

        // Rendir la URI del trabajo si existe.
        if (concursante.trabajo != null && concursante.trabajo.exists()) {
            response.contentType = concursante.trabajo.blob.type();
            renderBinary(concursante.trabajo.blob.get(), concursante.trabajo.blob.length());
        } else {
            notFound();
        }
    }
}
