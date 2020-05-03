package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.entity.Kill;
import gg.bayes.challenge.entity.KillCompoundKey;
import gg.bayes.challenge.ingestion.IngestionHandler;
import gg.bayes.challenge.repository.DamageRepository;
import gg.bayes.challenge.repository.ItemRepository;
import gg.bayes.challenge.repository.KillRepository;
import gg.bayes.challenge.repository.SpellRepository;
import gg.bayes.challenge.rest.model.HeroKills;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceImplTest {

    @Mock
    private List<IngestionHandler> ingestionHandlers;
    @Mock
    private KillRepository killRepository;
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private SpellRepository spellRepository;
    @Mock
    private DamageRepository damageRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private MatchServiceImpl matchService;

    @Test
    void shouldReturnHeroKills() {
        Long matchId = 1L;
        String hero = "bloodseeker";
        Integer kills = 5;
        List<Kill> killList = new ArrayList<>();
        Kill heroKill = new Kill();
        KillCompoundKey primaryKey = new KillCompoundKey();
        primaryKey.setMatchId(matchId);
        primaryKey.setHero(hero);
        heroKill.setId(primaryKey);
        heroKill.setKills(kills);
        killList.add(heroKill);
        when(killRepository.findByIdMatchId(matchId)).thenReturn(killList);
        List<HeroKills> heroKills = matchService.getHeroKills(matchId);
        assertEquals(1, heroKills.size());
        assertEquals(hero, heroKills.get(0).getHero());
        assertEquals(5, heroKills.get(0).getKills());
    }
}
