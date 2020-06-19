package it.polito.tdp.imdb.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	private Graph<Actor,DefaultWeightedEdge> grafo;
	private Map<Integer,Actor> mappaAttori;
    private List<Actor> attoriGen;
	
	public Model() {
		this.dao = new ImdbDAO();
		this.mappaAttori = new HashMap<Integer,Actor>();
	}
	
	public List<String> getGeneri(){
		List<String> listaGeneri = new LinkedList<String>(this.dao.getGeneri());
		return listaGeneri;
	}
	
	
	public List<Actor> getActorGenre(String g){
		this.attoriGen = new LinkedList<Actor>(this.dao.getAttoriPerGenere(g, mappaAttori));
		Collections.sort(attoriGen);
		return attoriGen;
	}
	
	public void creaGrafo(String genere) {
		//System.out.println(mappaAttori);
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.listAllActors(mappaAttori); //DEVI RIEMPIRE LA MAPPA DUMM!!!!!!!!!!
		Graphs.addAllVertices(this.grafo, this.dao.getAttoriPerGenere(genere, this.mappaAttori));
		for(Arco  a : this.dao.getArchi(genere, this.mappaAttori)) {
			if(this.grafo.containsVertex(a.getA1()) && this.grafo.containsVertex(a.getA2())) {
				DefaultWeightedEdge edge = this.grafo.getEdge(a.getA1(), a.getA2());
				if(edge==null) {
					Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(), a.getPeso());
				}
				else {
					this.grafo.setEdgeWeight(a.getA1(), a.getA2(), a.getPeso());
				}
			}
		}
		
		System.out.println(this.grafo);
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}

}
