package com.arandroid.risultatilive.core;

public class Risultato {
	private String match;
	private String risultato;
	private String date="";

	public Risultato() {

	}
	
	public String getMatch() {
		return match;
	}
	
	public void setMatch(String match) {
		this.match = match;
	}

	public String getRisultato() {
		return risultato;
	}

	public void setRisultato(String risultato) {
		this.risultato = risultato;
	}

	public void setDate(String date) {
		this.date=date;
	}
	
	public String getDate(){
		return date;
	}

}
