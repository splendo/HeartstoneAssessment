package com.lakhmotkin.heartstonecards.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor Lakhmotkin on 25.02.2018, for HeartstoneAssessment.
 */

public class CardSets {
    @SerializedName("Basic")
    List<Card> basic;

    @SerializedName("Blackrock Mountain")
    List<Card> blackRockMountain;

    @SerializedName("Classic")
    List<Card> classic;

    @SerializedName("Credits")
    List<Card> credits;

    @SerializedName("Debug")
    List<Card> debug;

    @SerializedName("Goblins vs Gnomes")
    List<Card> goblinsVsGnomes;

    @SerializedName("Hall of Fame")
    List<Card> hallOfFame;

    @SerializedName("Hero Skins")
    List<Card> heroSkin;

    @SerializedName("Journey to Un'Goro")
    List<Card> journeyToUnGoro;

    @SerializedName("Mean Streets of Gadgetzan")
    List<Card> meanStreetsofGadgetzan;

    @SerializedName("Missions")
    List<Card> missions;

    @SerializedName("Naxxramas")
    List<Card> naxxramas;

    @SerializedName("One Night in Karazhan")
    List<Card> oneNightInKarazhan;

    @SerializedName("Promo")
    List<Card> promo;

    @SerializedName("System")
    List<Card> system;

    @SerializedName("Tavern Brawl")
    List<Card> tavernBrawl;

    @SerializedName("The Grand Tournament")
    List<Card> theGrandTournament;

    @SerializedName("The League of Explorers")
    List<Card> theLeagueofExplorers;

    @SerializedName("Whispers of the Old Gods")
    List<Card> whispersOfTheOldGods;

    public CardSets() {
    }

    public List<Card> getAll() {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(blackRockMountain);
        allCards.addAll(basic);
        allCards.addAll(classic);
        allCards.addAll(credits);
        allCards.addAll(debug);
        allCards.addAll(goblinsVsGnomes);
        allCards.addAll(hallOfFame);
        allCards.addAll(heroSkin);
        allCards.addAll(journeyToUnGoro);
        allCards.addAll(meanStreetsofGadgetzan);
        allCards.addAll(missions);
        allCards.addAll(naxxramas);
        allCards.addAll(oneNightInKarazhan);
        allCards.addAll(promo);
        allCards.addAll(system);
        allCards.addAll(tavernBrawl);
        allCards.addAll(theGrandTournament);
        allCards.addAll(theLeagueofExplorers);
        allCards.addAll(whispersOfTheOldGods);
        return allCards;
    }

    public List<Card> getBasic() {
        return basic;
    }

    public void setBasic(List<Card> basic) {
        this.basic = basic;
    }

    public List<Card> getBlackRockMountain() {
        return blackRockMountain;
    }

    public void setBlackRockMountain(List<Card> blackRockMountain) {
        this.blackRockMountain = blackRockMountain;
    }

    public List<Card> getClassic() {
        return classic;
    }

    public void setClassic(List<Card> classic) {
        this.classic = classic;
    }

    public List<Card> getCredits() {
        return credits;
    }

    public void setCredits(List<Card> credits) {
        this.credits = credits;
    }

    public List<Card> getDebug() {
        return debug;
    }

    public void setDebug(List<Card> debug) {
        this.debug = debug;
    }

    public List<Card> getGoblinsVsGnomes() {
        return goblinsVsGnomes;
    }

    public void setGoblinsVsGnomes(List<Card> goblinsVsGnomes) {
        this.goblinsVsGnomes = goblinsVsGnomes;
    }

    public List<Card> getHallOfFame() {
        return hallOfFame;
    }

    public void setHallOfFame(List<Card> hallOfFame) {
        this.hallOfFame = hallOfFame;
    }

    public List<Card> getHeroSkin() {
        return heroSkin;
    }

    public void setHeroSkin(List<Card> heroSkin) {
        this.heroSkin = heroSkin;
    }

    public List<Card> getJourneyToUnGoro() {
        return journeyToUnGoro;
    }

    public void setJourneyToUnGoro(List<Card> journeyToUnGoro) {
        this.journeyToUnGoro = journeyToUnGoro;
    }

    public List<Card> getMeanStreetsofGadgetzan() {
        return meanStreetsofGadgetzan;
    }

    public void setMeanStreetsofGadgetzan(List<Card> meanStreetsofGadgetzan) {
        this.meanStreetsofGadgetzan = meanStreetsofGadgetzan;
    }

    public List<Card> getMissions() {
        return missions;
    }

    public void setMissions(List<Card> missions) {
        this.missions = missions;
    }

    public List<Card> getNaxxramas() {
        return naxxramas;
    }

    public void setNaxxramas(List<Card> naxxramas) {
        this.naxxramas = naxxramas;
    }

    public List<Card> getOneNightInKarazhan() {
        return oneNightInKarazhan;
    }

    public void setOneNightInKarazhan(List<Card> oneNightInKarazhan) {
        this.oneNightInKarazhan = oneNightInKarazhan;
    }

    public List<Card> getPromo() {
        return promo;
    }

    public void setPromo(List<Card> promo) {
        this.promo = promo;
    }

    public List<Card> getSystem() {
        return system;
    }

    public void setSystem(List<Card> system) {
        this.system = system;
    }

    public List<Card> getTavernBrawl() {
        return tavernBrawl;
    }

    public void setTavernBrawl(List<Card> tavernBrawl) {
        this.tavernBrawl = tavernBrawl;
    }

    public List<Card> getTheGrandTournament() {
        return theGrandTournament;
    }

    public void setTheGrandTournament(List<Card> theGrandTournament) {
        this.theGrandTournament = theGrandTournament;
    }

    public List<Card> getTheLeagueofExplorers() {
        return theLeagueofExplorers;
    }

    public void setTheLeagueofExplorers(List<Card> theLeagueofExplorers) {
        this.theLeagueofExplorers = theLeagueofExplorers;
    }

    public List<Card> getWhispersOfTheOldGods() {
        return whispersOfTheOldGods;
    }

    public void setWhispersOfTheOldGods(List<Card> whispersOfTheOldGods) {
        this.whispersOfTheOldGods = whispersOfTheOldGods;
    }
}
