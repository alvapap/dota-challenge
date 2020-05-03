package gg.bayes.challenge.ingestion;

import gg.bayes.challenge.entity.Kill;
import gg.bayes.challenge.entity.KillCompoundKey;
import gg.bayes.challenge.repository.KillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KillIngestionHandler implements IngestionHandler{
    private KillRepository killRepository;
    private final static String HERO = "npc_dota_hero_";

    @Autowired
    public KillIngestionHandler(KillRepository killRepository) {
        this.killRepository = killRepository;
    }

    @Override
    public void handleIngestion(String ingestionRow, Long matchId) {
        if(ingestionRow.contains("killed by")) {
            String[] splittedRow = ingestionRow.split(" ");
            if(splittedRow[1].startsWith(HERO) && splittedRow[5].startsWith(HERO)) {
                String hero = splittedRow[5].replace(HERO, "");
                Kill heroKill = killRepository.findByIdHeroAndIdMatchId(hero, matchId);
                if(heroKill != null) {
                    heroKill.setKills(heroKill.getKills() + 1);
                    killRepository.save(heroKill);
                } else {
                    Kill newHeroKill = new Kill();
                    KillCompoundKey primaryKey = new KillCompoundKey();
                    primaryKey.setHero(hero);
                    primaryKey.setMatchId(matchId);
                    newHeroKill.setId(primaryKey);
                    newHeroKill.setKills(1);
                    killRepository.save(newHeroKill);
                }
            }
        }
    }
}
