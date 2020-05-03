package gg.bayes.challenge.repository;

import gg.bayes.challenge.entity.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findByHeroAndMatchId(String hero, Long matchId);
}
