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

    public static void duracion(Long id) {
        Concurso concurso = Concurso.findById(id);

        DateTime tiempoInicial = new DateTime(concurso.tiempoInicial);
        DateTime tiempoFinal = new DateTime(concurso.tiempoFinal);
        DateTime duracion = concurso.duracion;

        String inputDateTime = "HH:mm:ss";
        String reprDuracion = duracion.toString(inputDateTime);
        String reprTiempoInicial = tiempoInicial.toString(inputDateTime);

        renderText(reprTiempoInicial);
    }
}