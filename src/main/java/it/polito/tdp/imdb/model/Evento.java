package it.polito.tdp.imdb.model;

public class Evento implements Comparable<Evento> {
	
	public enum TipoEvento{
		INTERVISTA,
		PAUSA 
	}
	
	private Actor a;
	private TipoEvento tipo;
	private int giornoAcuisonoArrivata;
	

	public Evento(Actor a, TipoEvento tipo,int giornoAcuisonoArrivata) {
		super();
		this.a = a;
		this.tipo = tipo;
		this. giornoAcuisonoArrivata =  giornoAcuisonoArrivata;
		
		
	}

	public Actor getA() {
		return a;
	}

	public void setA(Actor a) {
		this.a = a;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}
	
	

	public int getGiornoAcuisonoArrivata() {
		return giornoAcuisonoArrivata;
	}

	public void setGiornoAcuisonoArrivata(int giornoAcuisonoArrivata) {
		this.giornoAcuisonoArrivata = giornoAcuisonoArrivata;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.giornoAcuisonoArrivata-o.getGiornoAcuisonoArrivata() ;
	}
	
	
	
	
}
