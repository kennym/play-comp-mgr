package controllers;

import java.util.List;

import play.db.jpa.Blob;

import models.*;
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

        Concurso concurso = concursante.concurso;
        List<Concursante> concursantes = Concursante.find("byConcursoLike", concurso).fetch();
        String equipo = concursante.equipo.toString();
        System.out.println(concurso);
        System.out.println(equipo);

        render(concursante, concursantes, concurso, equipo);
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
