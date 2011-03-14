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

        System.out.println(usuario.rol);
        if (usuario.rol != ApplicationRole.getByName("concursante"))
            // FIXME !
            renderText("No eres un concursante.");

        Concursante concursante = Concursante.find("byLogin", usuario.login).first();

        String concurso = concursante.equipo.concurso.toString();
        String equipo = concursante.equipo.toString();
        String nombre = concursante.toString();
        System.out.println(concurso);
        System.out.println(equipo);
        System.out.println(nombre);

        render(concursante, concurso, equipo, nombre);
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
