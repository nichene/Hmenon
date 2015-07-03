package com.ufrpe.hmenon.graph.domain;

public class Edge {
	private Node nodeA;
	private Node nodeB;
	private int distance;
	
	public Edge(Node f, Node t, int distance){
		this.nodeA = f;
		this.nodeB = t;
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
