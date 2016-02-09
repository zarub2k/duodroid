package barqsoft.footballscores.changes;

/**
 * @author tham
 *
 * Keys used to parse Fixures Json response
 */
public enum FixureJsonKeys {
    FIXURES("fixtures"),
    LINKS("_links"),
    SEASON("soccerseason"),
    HOME_TEAM("homeTeam"),
    AWAY_TEAM("awayTeam"),
    HREF("href"),
    DATE("date"),
    HOME_TEAM_NAME("homeTeamName"),
    AWAY_TEAM_NAME("awayTeamName"),
    RESULT("result");

    private String key;

    private FixureJsonKeys(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
