package models;

import java.util.*;
import javax.persistence.*;

import models.deadbolt.Role;
import models.deadbolt.RoleHolder;
import play.db.jpa.*;
import play.data.validation.*;

@Entity
public class Usuario extends Model implements RoleHolder {

    @Email
    @Required
    public String login;

    @Required
    public String password;

    @Required
    @ManyToOne
    public ApplicationRole rol;

    public Usuario(String login, String password, ApplicationRole rol) {
        this.login = login;
        this.password = password;
        this.rol = rol;
    }

    public static Usuario connect(String login, String password) {
        return find("byLoginAndPassword", login, password).first();
    }

    public String toString() {
        return login;
    }

    public List<? extends Role> getRoles() {
        return Arrays.asList(rol);
    }
}
