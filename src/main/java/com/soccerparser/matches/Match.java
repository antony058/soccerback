package com.soccerparser.matches;

public class Match {
    private String homeTeam;
    private String visitorTeam;
    private String matchStat;
    private String formatDate;
    private String formatUserDate;
    private String group;
    private String stadium;
    private String venue;
    private String matchNumber;
    private String homeTeamCode;
    private String matchStage;
    private String referenceToMatch;

    public String getReferenceToMatch() {
        return referenceToMatch;
    }

    public void setReferenceToMatch(String referenceToMatch) {
        this.referenceToMatch = referenceToMatch;
    }

    public String getMatchStage() {
        return matchStage;
    }

    public void setMatchStage(String matchStage) {
        this.matchStage = matchStage;
    }

    public String getHomeTeamCode() {
        return homeTeamCode;
    }

    public void setHomeTeamCode(String homeTeamCode) {
        this.homeTeamCode = homeTeamCode;
    }

    public String getVisitorTeamCode() {
        return visitorTeamCode;
    }

    public void setVisitorTeamCode(String visitorTeamCode) {
        this.visitorTeamCode = visitorTeamCode;
    }

    private String visitorTeamCode;

    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public String getFormatUserDate() {
        return formatUserDate;
    }

    public void setFormatUserDate(String formatUserDate) {
        this.formatUserDate = formatUserDate;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public String getMatchStat() {
        return matchStat;
    }

    public void setMatchStat(String matchStat) {
        this.matchStat = matchStat;
    }
}
