package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.entity.Damage;
import gg.bayes.challenge.entity.Item;
import gg.bayes.challenge.entity.Kill;
import gg.bayes.challenge.entity.Spell;
import gg.bayes.challenge.ingestion.DamageIngestionHandler;
import gg.bayes.challenge.ingestion.IngestionHandler;
import gg.bayes.challenge.repository.DamageRepository;
import gg.bayes.challenge.repository.ItemRepository;
import gg.bayes.challenge.repository.KillRepository;
import gg.bayes.challenge.repository.SpellRepository;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Service
public class MatchServiceImpl implements MatchService {

    private List<IngestionHandler> ingestionHandlers;
    private static Long matchId = 0L;
    private KillRepository killRepository;
    private ItemRepository itemRepository;
    private SpellRepository spellRepository;
    private DamageRepository damageRepository;
    private ModelMapper modelMapper;

    @Autowired
    public MatchServiceImpl(final List<IngestionHandler> ingestionHandlers, final KillRepository killRepository,
                            final ItemRepository itemRepository, final SpellRepository spellRepository,
                            final DamageRepository damageRepository, final ModelMapper modelMapper) {
        this.ingestionHandlers = ingestionHandlers;
        this.killRepository = killRepository;
        this.itemRepository = itemRepository;
        this.spellRepository = spellRepository;
        this.damageRepository = damageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Long ingestMatch(String payload) {
        matchId++;
        String[] ingestionRows = payload.split("(?=\\[)");
        DamageIngestionHandler damageIngestionHandler = new DamageIngestionHandler(damageRepository);
        for(String ingestionRow : ingestionRows) {
            ingestionHandlers.stream().forEach(next -> next.handleIngestion(ingestionRow, matchId));
        }
        return matchId;
    }

    @Override
    public List<HeroKills> getHeroKills(Long matchId) {
        List<Kill> killList = killRepository.findByIdMatchId(matchId);
        Type listType = new TypeToken<List<HeroKills>>(){}.getType();
        modelMapper.typeMap(Kill.class, HeroKills.class).addMappings(mapper -> {
            mapper.map(src -> src.getId().getHero(),
                    HeroKills::setHero);
        });
        List<HeroKills> heroKills = modelMapper.map(killList, listType);
        return heroKills;
    }

    @Override
    public List<HeroItems> getItems(String heroName, Long matchId) {
        List<Item> itemList = itemRepository.findByHeroAndMatchId(heroName, matchId);
        Type listType = new TypeToken<List<HeroItems>>(){}.getType();
        List<HeroItems> heroItems = modelMapper.map(itemList, listType);
        return heroItems;
    }

    @Override
    public List<HeroSpells> getHeroSpells(String heroName, Long matchId) {
        List<Spell> spellList = spellRepository.findByIdHeroAndIdMatchId(heroName, matchId);
        Type listType = new TypeToken<List<HeroSpells>>(){}.getType();
        modelMapper.typeMap(Spell.class, HeroSpells.class).addMappings(mapper -> {
            mapper.map(src -> src.getId().getSpell(),
                    HeroSpells::setSpell);
        });
        List<HeroSpells> heroSpells = modelMapper.map(spellList, listType);
        return heroSpells;
    }

    @Override
    public List<HeroDamage> getHeroDamage(String heroName, Long matchId) {
        List<Damage> damageList = damageRepository.findByIdHeroAndIdMatchId(heroName, matchId);
        Type listType = new TypeToken<List<HeroDamage>>(){}.getType();
        modelMapper.typeMap(Damage.class, HeroDamage.class).addMappings(mapper -> {
            mapper.map(src -> src.getId().getTarget(),
                    HeroDamage::setTarget);
        });
        List<HeroDamage> heroDamageList = modelMapper.map(damageList, listType);
        return heroDamageList;
    }
}
