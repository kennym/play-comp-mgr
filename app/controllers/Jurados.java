package controllers;

import java.util.List;

import play.mvc.*;

import models.*;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@With(Secure.class)
public class Jurados extends Application {
    public static void index() {
        Usuario usuario = connected();

        try {
            if (usuario.rol != ApplicationRole.getByName("jurado"))
                forbidden("No eres un jurado.");
        } catch (NullPointerException e) {
            try {
                Secure.login();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        Jurado jurado = Jurado.find("byLogin", usuario.login).first();
        Concurso concurso = jurado.concurso;
        List<Concursante> concursantes = Concursante.all().fetch();

        render(jurado, concurso, concursantes);
    }

    /**
     * Evaluar concursante por su trabajo subido.
     *
     * @param id Identificador del concursante
     */
    public static void evaluarConcursante(Long id) {
        Concursante concursante = Concursante.findById(id);

        render(concursante);
    }
}