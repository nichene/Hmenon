package com.ufrpe.hmenon.graph.domain;

/**
 * Classe que representa uma aresta no grafo
 */
public class Edge {
	private Node nodeA;
	private Node nodeB;
	private int distance;

	public Edge(Node a, Node b, int distance){
		this.nodeA = a;
		this.nodeB = b;
		this.distance = distance;
	}

	public Node getNodeA() {
		return nodeA;
	}

	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	
}
