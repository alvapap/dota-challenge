package gg.bayes.challenge.ingestion;

public interface IngestionHandler {

    /**
     * The next qualified handler will handle the ingestion of the row. The ingestion will lead either to the
     * insertion of a new entry in the database, or an existing entry will be updated and saved in the database.
     * @param ingestionRow Each row of the combat log to be ingested.
     * @param matchId   The match id.
     */
    void handleIngestion(String ingestionRow, Long matchId);
}
