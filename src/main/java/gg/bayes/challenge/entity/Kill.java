package gg.bayes.challenge.entity;

import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
public class Kill {

    @EmbeddedId
    private KillCompoundKey id;

    private Integer kills;
}
