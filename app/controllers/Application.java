package controllers;

import play.*;
import play.mvc.*;
import play.libs.Codec;

import java.util.*;

import models.*;

public class Application extends Controller {

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