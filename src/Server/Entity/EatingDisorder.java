package Server.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity

@FilterDefs({
        @FilterDef(name = "id", parameters = @ParamDef(name = "id", type = "integer")),
        @FilterDef(name = "type", parameters = @ParamDef(name = "type", type = "string")),
})
@Filters({
        @Filter(name = "id", condition = "id = :id"),
        @Filter(name = "type", condition = "type like :type"),
})

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Table(name = "EatingDisorder")
public abstract class EatingDisorder extends AbstractEntity {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "affectedChild")
    private Child affectedChild;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "affectedAliment")
    private Aliment affectedAliment;

    public EatingDisorder() {
    }

    public EatingDisorder(Child affectedChild, Aliment affectedAliment) {
        this.affectedChild = affectedChild;
        this.affectedAliment = affectedAliment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAffectedChildId() {
        return affectedChild.getId();
    }

    public Child getAffectedChild() {
        return affectedChild;
    }

    public void setAffectedChild(Child affectedChild) {
        this.affectedChild = affectedChild;
    }

    public Integer getAffectedAlimentId() {
        return affectedAliment.getId();
    }

    public Aliment getAffectedAliment() {
        return affectedAliment;
    }

    public void setAffectedAliment(Aliment affectedAliment) {
        this.affectedAliment = affectedAliment;
    }

}