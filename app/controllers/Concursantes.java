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
            if (usuario.rol != ApplicationRole.getByName("concursante")) {
                renderText("No eres un concursante.");
            }
        } catch (NullPointerException e) {
            renderText("Tienes que inciar sesión primero.");
        }

        Concursante concursante = Concursante.find("byLogin", usuario.login).first();

        Concurso concurso = concursante.equipo.concurso;
        String equipo = concursante.equipo.toString();
        System.out.println(concurso);
        System.out.println(equipo);

        render(concursante, concurso, equipo);
    }

    public static void subir_trabajo(Long id, Blob trabajo) {
        // Obtener concursante de la base de datos
        Concursante concursante = Concursante.findById(id);

        // Actualizar el trabajo
        concursante.trabajo = trabajo;

        // Guardar todos los cambios
        concursante.save();

        // Volver al índice
        Concursantes.index();
    }

    public static void mostrar_trabajo(Long id) {
        // Obtener concursante de la base de datos
        Concursante concursante = Concursante.findById(id);

        // Rendir la URI del trabajo si existe.
        if (concursante.trabajo != null && concursante.trabajo.exists()) {
            response.contentType = concursante.trabajo.type();
            renderBinary(concursante.trabajo.get(), concursante.trabajo.length());
        } else {
            notFound();
        }
    }
}
