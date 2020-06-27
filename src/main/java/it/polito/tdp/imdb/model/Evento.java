package it.polito.tdp.imdb.model;

public class Evento {
	
	public enum TipoEvento{
		INTERVISTA,
		PAUSA 
	}
	
	private Actor a;
	private TipoEvento tipo;
	

	public Evento(Actor a, TipoEvento tipo) {
		super();
		this.a = a;
		this.tipo = tipo;
		
		
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
	
	
	
	
}
