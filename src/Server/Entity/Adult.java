package Server.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Adult")
public class Adult extends AbstractEntity {
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

    @OneToOne
    @JoinColumn(unique = true, name = "user_id")
    private Users user;

    public Adult() { }

    public Adult(String nome, String cognome, String codiceFiscale, Date nascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.nascita = nascita;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    protected boolean primaryKeysAreValid() {
        return (getId() != null && getId() > 0);
    }
}