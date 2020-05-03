package gg.bayes.challenge.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class DamageCompoundKey implements Serializable {

    private String hero;
    private Long matchId;
    private String target;
}
