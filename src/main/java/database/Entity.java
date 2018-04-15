package database;

/**
 * <p>Abstract entity class.</p>
 *
 * @author mrfarinq
 * @version $Id: $Id
 */
public abstract class Entity {
    private Integer id;

    /**
     * <p>Constructor for entity.</p>
     */
    public Entity() {
    }

    /**
     * <p>Constructor for entity.</p>
     *
     * @param id a {@link java.lang.Integer} object.
     */
    public Entity(Integer id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return a int.
     */
    public int getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id a {@link java.lang.Integer} object.
     */
    public void setId(Integer id) {
        this.id = id;
    }
}


