package controllers;

import org.joda.time.*;
import org.joda.time.format.*;

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

        try {
            if (usuario.rol != ApplicationRole.getByName("organizador"))
                forbidden("No eres un organizador.");
        } catch (NullPointerException e) {
            try {
                Secure.login();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        Organizador organizador = Organizador.find("byLogin", usuario.login).first();

        System.out.println(organizador);
        Concurso concurso = organizador.concurso;
        String nombre = organizador.toString();

        render(organizador, concurso, nombre);
    }

    public static void iniciarConcurso(Long id, String duracion) {
        validation.required(id);
        validation.required(duracion);
        validation.isTrue(duracion);

        Concurso concurso = Concurso.findById(id);

        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm:ss");
        DateTime duracionDateTime = dtf.parseDateTime(duracion);

        concurso.iniciar(duracionDateTime);

        Organizadores.index();
    }

    public static void pararConcurso(Long id) {
        Concurso concurso = Concurso.findById(id);

        concurso.parar();

        Organizadores.index();
    }
}
