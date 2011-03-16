package controllers;

import play.mvc.With;

import models.*;

/**
 * Organizadores
 *
 * El controller para Organizador()
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@CRUD.For(Organizador.class)
public class Organizadores extends Application {

    public static void index() {
        Usuario usuario = connected();

        Organizador organizador = Organizador.find("byLogin", usuario.login).first();

        System.out.println(organizador);
        Concurso concurso = organizador.concurso;
        String nombre = organizador.toString();

        render(organizador, concurso, nombre);
    }
}
