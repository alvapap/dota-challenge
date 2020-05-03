package gg.bayes.challenge.repository;

import gg.bayes.challenge.entity.Kill;
import gg.bayes.challenge.entity.KillCompoundKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KillRepository extends CrudRepository<Kill, KillCompoundKey> {

    Kill findByIdHeroAndIdMatchId(String hero, Long matchId);
    List<Kill> findByIdMatchId(Long matchId);
}
