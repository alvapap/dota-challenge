package gg.bayes.challenge.ingestion;

import gg.bayes.challenge.entity.Item;
import gg.bayes.challenge.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

@Slf4j
@Component
public class ItemIngestionHandler implements IngestionHandler{

    private ItemRepository itemRepository;
    private final static String HERO = "npc_dota_hero_";
    private final static String ITEM = "item_";


    @Autowired
    public ItemIngestionHandler(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void handleIngestion(String ingestionRow, Long matchId) {
        if(ingestionRow.contains("buys item")) {
            String[] splittedRow = ingestionRow.split(" ");
            String hero = splittedRow[1].replace(HERO, "");
            String parsedItem = splittedRow[4].replace(ITEM, "");
            String parsedTime = splittedRow[0].replace("[", "").replace("]", "");
            Long timestamp = 0L;
            try {
                LocalTime localTime = LocalTime.parse(parsedTime);
                timestamp = localTime.getLong(ChronoField.MILLI_OF_DAY);
            } catch (DateTimeParseException dtpe) {
                log.error("Was not able to parse the time in row: {}", ingestionRow, dtpe.getCause());
                // should at least be logged
            }
            Item heroItem = new Item();
            heroItem.setHero(hero);
            heroItem.setItem(parsedItem);
            heroItem.setMatchId(matchId);
            heroItem.setTimestamp(timestamp);
            itemRepository.save(heroItem);
        }
    }
}
