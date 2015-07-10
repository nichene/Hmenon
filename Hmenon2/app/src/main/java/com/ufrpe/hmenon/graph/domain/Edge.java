package com.ufrpe.hmenon.graph.domain;

/**
 * A classe Edge representa as arestas de um grafo, cada instância da classe sempre está conectada
 * à duas instâncias de {@link Node} para formar um grafo não-direcionado.
 * <p>
 * Implementa getters e setters, a classe fica responsável por manter as referências dos nós de
 * ambas as suas extremidades e suas respectivas distâncias.
 */
public class Edge {
	private Node nodeA;
	private Node nodeB;
	private int distance;
	public Edge(Node a, Node b, int distance) {
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
