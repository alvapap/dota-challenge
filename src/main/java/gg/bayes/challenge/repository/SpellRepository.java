package gg.bayes.challenge.repository;

import gg.bayes.challenge.entity.Spell;
import gg.bayes.challenge.entity.SpellCompoundKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SpellRepository extends CrudRepository<Spell, SpellCompoundKey> {

    Spell findByIdHeroAndIdMatchIdAndIdSpell(String hero, Long matchId, String spell);
    List<Spell> findByIdHeroAndIdMatchId(String hero, Long matchId);
}
