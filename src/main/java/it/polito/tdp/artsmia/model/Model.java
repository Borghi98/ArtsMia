package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private Graph<ArtObject,DefaultWeightedEdge>grafo ;
	private ArtsmiaDAO dao;
	private Map<Integer, ArtObject> idMap;
	
	public Model() {
		dao= new ArtsmiaDAO();
		idMap = new HashMap<Integer, ArtObject>();
	}

	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo vertici 
		dao.listObjects(idMap);
		Graphs.addAllVertices(grafo, idMap.values());
		
		//aggiungo archi
		//approccio 1 più semplice ma meno performante ( ci mette giorni/settimane)
		
//		for(ArtObject a1 : this.grafo.vertexSet()) {
//			for(ArtObject a2 : this.grafo.vertexSet()) {
//				if (!a1.equals(a2) && !this.grafo.containsEdge(a1,a2)) {
//					// collego a1 e a2
//					int peso = dao.getPeso(a1,a2);
//					if(peso>0) {
//						Graphs.addEdge(this.grafo, a1, a2, peso);
//					}
//				}
//			}
//		}
		
		// approccio 2 
		
		for(Adiacenza a : dao.getAdiacenze()) {
			Graphs.addEdge(this.grafo, idMap.get(a.getId1()), idMap.get(a.getId2()), a.getPeso());
			
		}
		
		System.out.println("Grafo creato!");
		System.out.println("Vertici: " + grafo.vertexSet().size());
		System.out.println("Archi: " + grafo.edgeSet().size());
			
	}
	
	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
			
}
