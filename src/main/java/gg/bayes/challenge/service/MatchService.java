package gg.bayes.challenge.service;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

import java.util.List;

public interface MatchService {
    Long ingestMatch(String payload);

    List<HeroKills> getHeroKills(Long matchId);

    List<HeroItems> getItems(String heroName, Long matchId);

    List<HeroSpells> getHeroSpells(String heroName, Long matchId);

    List<HeroDamage> getHeroDamage(String heroName, Long matchId);
}
