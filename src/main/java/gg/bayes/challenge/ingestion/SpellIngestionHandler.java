package gg.bayes.challenge.ingestion;

import gg.bayes.challenge.entity.Spell;
import gg.bayes.challenge.entity.SpellCompoundKey;
import gg.bayes.challenge.repository.SpellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpellIngestionHandler implements IngestionHandler{
    private SpellRepository spellRepository;
    private final static String HERO = "npc_dota_hero_";

    @Autowired
    public SpellIngestionHandler(SpellRepository spellRepository) {
        this.spellRepository = spellRepository;
    }

    @Override
    public void handleIngestion(String ingestionRow, Long matchId) {
        if(ingestionRow.contains("casts ability")) {
            String[] splittedRow = ingestionRow.split(" ");
            String hero = splittedRow[1].replace(HERO, "");
            String spell = splittedRow[4].replace(hero + "_", "");
            Spell heroSpell = spellRepository.findByIdHeroAndIdMatchIdAndIdSpell(hero, matchId, spell);
            if(heroSpell != null) {
                heroSpell.setCasts(heroSpell.getCasts() + 1);
                spellRepository.save(heroSpell);
            } else {
                Spell newSpell = new Spell();
                SpellCompoundKey primaryKey = new SpellCompoundKey();
                primaryKey.setHero(hero);
                primaryKey.setMatchId(matchId);
                primaryKey.setSpell(spell);
                newSpell.setId(primaryKey);
                newSpell.setCasts(1);
                spellRepository.save(newSpell);
            }
        }
    }
}
