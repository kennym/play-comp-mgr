package models;

import javax.persistence.*;

import play.db.jpa.*;

import play.data.validation.*;

/**
 * Problem
 *
 * Represents a problem which has to be resolved.
 * 
 * @author Kenny Meyer <knny.myer@gmail.com>
 */
@Entity
public class Problem extends Model {
    @Required
    public String title;

    @Required
    @Lob
    public String statement;
}