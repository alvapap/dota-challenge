package gg.bayes.challenge.repository;

import gg.bayes.challenge.entity.Damage;
import gg.bayes.challenge.entity.DamageCompoundKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DamageRepository extends CrudRepository<Damage, DamageCompoundKey> {

    Damage findByIdHeroAndIdMatchIdAndIdTarget(String hero, Long matchId, String target);
    List<Damage> findByIdHeroAndIdMatchId(String hero, Long matchId);
}
