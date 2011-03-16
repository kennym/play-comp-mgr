package controllers;

import org.joda.time.*;
import org.joda.time.format.*;

import models.*;

/**
 * Concursos
 *
 * Controller for the Concurso model.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
public class Concursos extends Application {

    public static void iniciarConcurso(Long id, String duracion) {
        validation.required(id);
        validation.required(duracion);
        validation.isTrue(duracion);

        System.out.println(duracion);

        Concurso concurso = Concurso.findById(id);

        // Convertir duracion a un objeto Date()
        /// Analizar String
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
        DateTime duracionDateTime = dtf.parseDateTime(duracion);

        System.out.println(duracionDateTime);

        System.out.println(concurso.estado);

        concurso.iniciar(duracionDateTime);
    }

    public static void pararConcurso(Long id) {
        Concurso concurso = Concurso.findById(id);

        concurso.parar();
    }
}