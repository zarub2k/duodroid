package barqsoft.footballscores.changes;

import java.util.Date;

/**
 * @author tham
 *
 * Bean holder for fixure Json information
 */
public class Fixure {
    private String href;
    private Date date;

    private String homeTeamName;
    private String homeTeamHref;
    private int homeTeamGoal;

    private String awayTeamName;
    private String awayTeamHref;
    private int awayTeamGoal;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getHomeTeamHref() {
        return homeTeamHref;
    }

    public void setHomeTeamHref(String homeTeamHref) {
        this.homeTeamHref = homeTeamHref;
    }

    public int getHomeTeamGoal() {
        return homeTeamGoal;
    }

    public void setHomeTeamGoal(int homeTeamGoal) {
        this.homeTeamGoal = homeTeamGoal;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getAwayTeamHref() {
        return awayTeamHref;
    }

    public void setAwayTeamHref(String awayTeamHref) {
        this.awayTeamHref = awayTeamHref;
    }

    public int getAwayTeamGoal() {
        return awayTeamGoal;
    }

    public void setAwayTeamGoal(int awayTeamGoal) {
        this.awayTeamGoal = awayTeamGoal;
    }
}
