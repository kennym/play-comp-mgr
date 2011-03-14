package controllers;

import java.util.*;

import play.libs.Codec;

import models.*;
/**
 * Class Name
 *
 * Class description - Explain why you need it and what it does.
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