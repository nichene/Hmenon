package com.ufrpe.hmenon.graph.domain;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;

import java.util.ArrayList;

/**
 * Classe que representa um grafo, com conjuntos de nós e arestas
 *
 * @see com.ufrpe.hmenon.graph.domain.Edge
 * @see com.ufrpe.hmenon.graph.domain.Node
 */
public class Graph {
	private ArrayList <Node> nodes = new ArrayList<>();
	private ArrayList <Edge> edges = new ArrayList<>();
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	public void addNode(Node node){
		nodes.add(node);
	}
	public void addEdge(Node a, Node b, int distance){
		a.addConnection(b, distance);
        b.addConnection(a, distance);
		Edge edge = new Edge(a, b, distance);
		edges.add(edge);
		edge = new Edge(b, a, distance);
		edges.add(edge);
	}
    public Node get(TouristicPoint point){
        Node node = null;
        for (Node n : nodes){
            if (n.getData().getName().equals(point.getName())){
                node = n;
            }
        }
        return node;
    }
	
}
