package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping(consumes = "text/plain")
    public ResponseEntity<Long> ingestMatch(@RequestBody @NotNull @NotBlank String payload) {
        final Long matchId = matchService.ingestMatch(payload);
        return ResponseEntity.ok(matchId);
    }

    @GetMapping("{matchId}")
    public ResponseEntity<List<HeroKills>> getMatch(@PathVariable("matchId") Long matchId) {
        List<HeroKills> heroKills = matchService.getHeroKills(matchId);
        return ResponseEntity.ok(heroKills);
    }

    @GetMapping("{matchId}/{heroName}/items")
    public ResponseEntity<List<HeroItems>> getItems(@PathVariable("matchId") Long matchId,
                                                    @PathVariable("heroName") String heroName) {
        List<HeroItems> heroItems = matchService.getItems(heroName, matchId);
        return ResponseEntity.ok(heroItems);
    }

    @GetMapping("{matchId}/{heroName}/spells")
    public ResponseEntity<List<HeroSpells>> getSpells(@PathVariable("matchId") Long matchId,
                                                      @PathVariable("heroName") String heroName) {
        List<HeroSpells> heroSpells = matchService.getHeroSpells(heroName, matchId);
        return ResponseEntity.ok(heroSpells);
    }

    @GetMapping("{matchId}/{heroName}/damage")
    public ResponseEntity<List<HeroDamage>> getDamage(@PathVariable("matchId") Long matchId,
                                                      @PathVariable("heroName") String heroName) {
        List<HeroDamage> heroDamageList = matchService.getHeroDamage(heroName, matchId);
        return ResponseEntity.ok(heroDamageList);
    }
}
