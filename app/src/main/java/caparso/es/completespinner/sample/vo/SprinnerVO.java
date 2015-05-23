package caparso.es.completespinner.sample.vo;

/**
 * Created by Fernando Galiay on 23/05/2015.
 */
public class SprinnerVO {

    private Integer id;

    private String titulo;

    public SprinnerVO(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return getTitulo();
    }
}
