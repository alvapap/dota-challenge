package gg.bayes.challenge.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class Spell {

    @EmbeddedId
    private SpellCompoundKey id;

    private Integer casts;
}
