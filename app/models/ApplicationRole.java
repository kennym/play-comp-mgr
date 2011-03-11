package models;

import models.deadbolt.Role;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
public class ApplicationRole extends Model implements Role {
    @Required
    public String nombre;

    public ApplicationRole(String nombre) {
        this.nombre = nombre;
    }

    public String getRoleName() {
        return nombre;
    }

    public static ApplicationRole getByName(String name) {
        return ApplicationRole.find("byNombre", name).first();
    }

    @Override
    public String toString() {
        return this.nombre;
    }
}

