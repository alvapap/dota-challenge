package gg.bayes.challenge.ingestion;

import gg.bayes.challenge.entity.Damage;
import gg.bayes.challenge.entity.DamageCompoundKey;
import gg.bayes.challenge.repository.DamageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DamageIngestionHandler implements IngestionHandler{

    private DamageRepository damageRepository;
    private final static String HERO = "npc_dota_hero_";

    @Autowired
    public DamageIngestionHandler(DamageRepository damageRepository) {
        this.damageRepository = damageRepository;
    }

    /**
     * The responsible handler to ingest the row. The result will be either a new entry which will be saved in the database or
     * an existing entry which will be updated in the database.
     * @param ingestionRow Each row of the combat log to be ingested.
     * @param matchId   The match id.
     */
    @Override
    public void handleIngestion(String ingestionRow, Long matchId) {
        if(ingestionRow.contains("hits")) {
            String[] splittedRow = ingestionRow.split(" ");
            String hero = splittedRow[1].replace(HERO, "");
            String target = splittedRow[3].replace(HERO, "");
            Integer parsedDamage = 0;
            try {
                parsedDamage = Integer.parseInt(splittedRow[7]);
            } catch (NumberFormatException numberFormatException) {
                log.error("Was not able to parse the damage in row: {}", ingestionRow, numberFormatException.getCause());
                // should at least be logged
            }
            Damage damage = damageRepository.findByIdHeroAndIdMatchIdAndIdTarget(hero, matchId, target);
            if(damage != null) {
                damage.setTotalDamage(damage.getTotalDamage() + parsedDamage);
                damage.setDamageInstances(damage.getDamageInstances() + 1);
                damageRepository.save(damage);
            } else {
                Damage newDamageEntry = new Damage();
                DamageCompoundKey primaryKey = new DamageCompoundKey();
                primaryKey.setHero(hero);
                primaryKey.setMatchId(matchId);
                primaryKey.setTarget(target);
                newDamageEntry.setId(primaryKey);
                newDamageEntry.setDamageInstances(1);
                newDamageEntry.setTotalDamage(parsedDamage);
                damageRepository.save(newDamageEntry);
            }
        }
    }
}
