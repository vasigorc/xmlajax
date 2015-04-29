/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ajaxandxml.model;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.ISOPeriodFormat;
import org.joda.time.format.PeriodFormatter;

/**
 *
 * @author vasigorc
 */
public class Movie {

    private String title, genre, rank;
    private boolean noPasses;
    /*
     * the below listed variables are from Joda time API. The PeriodFormatter
     *(as a static var) will return to view a nice string representation of ISO8601
     * duration format
     */
    private Period duration;
    private static PeriodFormatter parser = ISOPeriodFormat.standard();
    private LocalTime[] showingon;
    private LocalDate released;
    private String[] actors, directors, producers, studios;

    public Movie() {
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setShowingon(LocalTime[] showingon) {
        this.showingon = showingon;
    }

    public void setReleased(LocalDate released) {
        this.released = released;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public void setProducers(String[] producers) {
        this.producers = producers;
    }

    public void setStudios(String[] studios) {
        this.studios = studios;
    }   

    public String getGenre() {
        return genre;
    }

    public String getRank() {
        return rank;
    }

    public String[] getShowingon() {
        String[] showtimes = new String[showingon.length];
        for (int i = 0; i < showingon.length; i++) {
            //if the pattern won't work refer to Joda's DateTimeFormatter
            showtimes[i] = showingon[i].toString("HH:mm:ss");
        }
        return showtimes;
    }

    public String getReleased() {
        return released.getYear()+"-"+released.getMonthOfYear()+"-"+released.getDayOfMonth();
    }

    public String[] getActors() {
        return actors;
    }

    public String[] getDirectors() {
        return directors;
    }

    public String[] getProducers() {
        return producers;
    }

    public String[] getStudios() {
        return studios;
    }

    public String getDuration() {
        return parser.print(duration);
    }

    public void setDuration(Period duration) {
        this.duration = duration;
    }

    public void setNoPasses(boolean noPasses) {
        this.noPasses = noPasses;
    }

    public boolean isNoPasses() {
        return noPasses;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
