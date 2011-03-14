package controllers;

import java.util.*;

import play.libs.Codec;

import models.*;

/**
 * Concursos
 *
 * Controller for the Concurso model.
 *
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
public class Concursos extends Application {

    public static void index() {
        List<Concurso> concursos = Concurso.findAll();
        render(concursos);
    }

    public static void show(Long id) {
        Concurso concurso = Concurso.findById(id);
        String randomID = Codec.UUID();
        render(concurso, randomID);
    }
}