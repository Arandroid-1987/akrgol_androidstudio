package com.arandroid.risultatilive.core;

import android.graphics.Bitmap;

public class Squadra {
	private String nome;
	private String score;
	private Bitmap logo;
	private String link;
	private String Posizione;
	private String Incontri;
	private String Won;
	private String Tied;
	private String Lost;
	private String prossimoMatch;
	
	public String getProssimoMatch() {
		return prossimoMatch;
	}
	public void setProssimoMatch(String prossimoMatch) {
		this.prossimoMatch = prossimoMatch;
	}
	
	
	public void setPosizione(String posizione) {
		Posizione = posizione;
	}
	
	public String getPosizione() {
		return Posizione;
	}
	
	public void setIncontri(String incontri) {
		Incontri = incontri;
	}
	
	public String getIncontri() {
		return Incontri;
	}
	public void setWon(String won) {
		Won = won;
	}
	public String getWon() {
		return Won;
	}
	public void setTied(String tied) {
		Tied = tied;
	}
	
	public String getTied() {
		return Tied;
	}
	
	public String getLost() {
		return Lost;
	}
	
	public void setLost(String lost) {
		Lost = lost;
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

}
