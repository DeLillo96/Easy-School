package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity

@FilterDef(name = "id", parameters = {
        @ParamDef(name = "id", type = "integer")
})
@FilterDef(name = "nome", parameters = {
        @ParamDef(name = "nome", type = "string")
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "nome", condition = "nome =:nome"),
})

@Table(name = "Alimento")
public class Alimento extends AbstractEntity {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @Column(nullable = false, length = 32)
    private String nome;

    public Alimento() { }

    public Alimento(String nome) {
        this.nome=nome;
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
}
