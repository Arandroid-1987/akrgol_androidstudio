package com.arandroid.risultatilive.core;

import android.graphics.Bitmap;

public class Squadra {
    private String nome;
    private String score;
    private Bitmap logo;
    private String link;
    private String posizione;
    private String incontri;
    private String won;
    private String tied;
    private String lost;
    private String prossimoMatch;

    public String getProssimoMatch() {
        return prossimoMatch;
    }

    public void setProssimoMatch(String prossimoMatch) {
        this.prossimoMatch = prossimoMatch;
    }

    public void setPosizione(String posizione) {
        this.posizione = posizione;
    }

    public String getPosizione() {
        return posizione;
    }

    public void setIncontri(String incontri) {
        this.incontri = incontri;
    }

    public String getIncontri() {
        return incontri;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getWon() {
        return won;
    }

    public void setTied(String tied) {
        this.tied = tied;
    }

    public String getTied() {
        return tied;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public String getNome() {
        return nome;
    }

    public String getScore() {
        return score;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return nome;
    }
}
