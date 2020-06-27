package it.polito.tdp.imdb.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.imdb.db.ImdbDAO;
import it.polito.tdp.imdb.model.Evento.TipoEvento;

public class Simulatore {
	
	private int nGiorniMax;
	
	private int nGiorni =0;
	private ImdbDAO dao = new ImdbDAO();
	private List<Actor> attori ;
	private Graph<Actor,DefaultWeightedEdge> grafo;
	private PriorityQueue<Evento> queue = new PriorityQueue<>();
	private List<Actor> attoriIntervistati = new LinkedList<Actor>() ;
	private int pausa=0;
	private Random rm = new Random();
	private int giorniConsecutivi=0;;
	
	public Simulatore(String genere, Graph<Actor,DefaultWeightedEdge> grafo, int n) {
		Map<Integer,Actor> mappa = new HashMap<Integer,Actor>();
		this.dao.listAllActors(mappa);
		this.grafo = grafo;
		this.nGiorniMax = n;
		this.attori = new LinkedList<Actor>(this.dao.getAttoriPerGenere(genere, mappa));
		
		
	}
	
	public Actor getAttoreCasuale() {
		if(this.attori.size()==0) {
			return null;
		}
		
		int index = this.rm.nextInt(this.attori.size());
		return this.attori.get(index);
	}
	
	public void init() {
		Actor aa = this.getAttoreCasuale();
		if(aa!=null) {
			queue.add(new Evento(aa,TipoEvento.INTERVISTA));
			this.run();
		}
	}
	
	public void run() {
		while(!queue.isEmpty() && nGiorni<nGiorniMax) {
			Evento ev = queue.poll();
			switch(ev.getTipo()) {
			case PAUSA:
				pausa++;
				nGiorni++;
				Actor aa = this.getAttoreCasuale();
				if(aa!=null) {
					queue.add(new Evento(aa,TipoEvento.INTERVISTA));
					this.attori.remove(aa);
				}
				break;
				
			case INTERVISTA:
				this.attoriIntervistati.add(ev.getA());
				nGiorni++;
				if(this.giorniConsecutivi>=2) {
					if(this.rm.nextFloat()<=0.9) {
						queue.add(new Evento(null, TipoEvento.PAUSA));
						giorniConsecutivi=0;
						break;
					}
					
					
				}
				giorniConsecutivi++;
				Actor d = this.getAttoreProb(ev.getA());
				if(d==null) {
					return ;
				}
				queue.add(new Evento(d,TipoEvento.INTERVISTA));
				break;
			
			}
			
		}
		
	}
	
	public List<Actor> getList {
		return null;
	}
	
	public Actor getAttoreProb(Actor a) {
		int max = -1;
		Actor scelto=null;
		
		if(this.rm.nextFloat()<=0.6) {
			return this.getAttoreCasuale();
			
		}
		else {
			List<Actor> vicini = new LinkedList<Actor>(Graphs.neighborListOf(this.grafo,a));
			for (Actor b: vicini) {
				DefaultWeightedEdge edge = this.grafo.getEdge(a, b);
				if(this.grafo.getEdgeWeight(edge)>max) {
					max = (int) this.grafo.getEdgeWeight(edge);
					scelto = b;
				}
					
				
			}
			return scelto;
		}
	}
	
	
	

}
