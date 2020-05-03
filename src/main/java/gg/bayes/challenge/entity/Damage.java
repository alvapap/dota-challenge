package gg.bayes.challenge.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class Damage {

    @EmbeddedId
    private DamageCompoundKey id;

    private Integer damageInstances;

    private Integer totalDamage;
}
