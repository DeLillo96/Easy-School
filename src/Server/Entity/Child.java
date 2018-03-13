package Server.Entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.Date;

@Entity

@FilterDef(name = "byId", parameters = {
        @ParamDef(name = "id", type = "integer" )
})
@FilterDef(name = "byCodiceFiscale", parameters = {
        @ParamDef(name = "codiceFiscale", type = "string")
})
@Filters({
        @Filter(name = "byCodiceFiscale", condition = "codiceFiscale like :codiceFiscale"),
        @Filter(name = "byId", condition = "id = :id")
})
@Table(name = "child")
public class Child extends AbstractEntity{
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(nullable = false, length = 32)
    private String nome;

    @Column(nullable = false, length = 32)
    private String cognome;

    @Column(unique = true, length = 16)
    private String codiceFiscale;

    @Column(nullable = false)
    private Date nascita;

    @Column(nullable = false, length = 64)
    private String contatti;

    public Child() { }

    public Child(String nome, String cognome, Date nascita, String contatti) {
        this(nome, cognome, null, nascita, contatti);
    }

    public Child(String nome, String cognome, String codiceFiscale, Date nascita, String contatti) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.nascita = nascita;
        this.contatti = contatti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Date getNascita() {
        return nascita;
    }

    public void setNascita(Date nascita) {
        this.nascita = nascita;
    }

    public String getContatti() {
        return contatti;
    }

    public void setContatti(String contatti) {
        this.contatti = contatti;
    }

    @Override
    protected boolean primaryKeysAreValid() {
        return (null != getId() && getId() > 0);
    }
}
