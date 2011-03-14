package controllers;

import play.mvc.With;

import models.Organizador;
import models.Concurso;
import models.Usuario;
import models.Concursante;
import models.ApplicationRole;

/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@CRUD.For(Organizador.class)
public class Organizadores extends Application {

    public static void index() {
        Usuario usuario = connected();

        System.out.println(usuario.rol);
        if (usuario.rol != ApplicationRole.getByName("organizador"))
            // FIXME !
            renderText("No eres un organizador.");

        Organizador organizador = Organizador.find("byLogin", usuario.login).first();

        String concurso = organizador.concurso.toString();
        String nombre = organizador.toString();;

        System.out.println(concurso);
        System.out.println(nombre);

        render(organizador, concurso, nombre);
    }

    public static void iniciarConcurso(Long id) {
        Concurso concurso = Concurso.findById(id);

        concurso.iniciar();
    }

    public static void terminarConcurso(Long id) {
        Concurso concurso = Concurso.findById(id);

        concurso.terminar();
    }

    public static void pausarConcurso(Long id) {
        Concurso concurso = Concurso.findById(id);

        concurso.pausar();
    }
}
