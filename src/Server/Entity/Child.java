package Server.Entity;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity

@FilterDef(name = "id", parameters = {
        @ParamDef(name = "id", type = "integer" )
})
@FilterDef(name = "nome", parameters = {
        @ParamDef(name = "nome", type = "string")
})
@FilterDef(name = "cognome", parameters = {
        @ParamDef(name = "cognome", type = "string")
})
@FilterDef(name = "codiceFiscale", parameters = {
        @ParamDef(name = "codiceFiscale", type = "string")
})
@FilterDef(name = "nascita", parameters = {
        @ParamDef(name = "nascita", type = "date")
})
@FilterDef(name = "contatti", parameters = {
        @ParamDef(name = "contatti", type = "string")
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "nome", condition = "nome like :nome"),
        @Filter(name = "cognome", condition = "cognome like :cognome"),
        @Filter(name = "codiceFiscale", condition = "codiceFiscale like :codiceFiscale"),
        @Filter(name = "nascita", condition = "nascita = :nascita"),
        @Filter(name = "contatti", condition = "contatti = :contatti"),
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

    @ManyToMany(cascade = { CascadeType.DETACH }, fetch=FetchType.EAGER)
    @JoinTable(
            name = "Parent",
            joinColumns = { @JoinColumn(name = "child_id") },
            inverseJoinColumns = { @JoinColumn(name = "adult_id") }
    )
    private Set<Adult> parents = new HashSet<>();

    @ManyToMany(mappedBy = "presentChildren")
    private Set<Calendario> presenze = new HashSet<>();

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

    public Set<Adult> getParents() {
        return parents;
    }

    public void setParents(Set<Adult> parents) {
        this.parents = parents;
    }

    public Set<Calendario> getPresenze() {
        return presenze;
    }

    public void setPresenze(Set<Calendario> presenze) {
        this.presenze = presenze;
    }
}
