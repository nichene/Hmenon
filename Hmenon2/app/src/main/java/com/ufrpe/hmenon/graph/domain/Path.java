package com.ufrpe.hmenon.graph.domain;

import java.util.ArrayList;


/**
 * Classe que representa um caminho no grafo
 *
 * @see com.ufrpe.hmenon.graph.domain.Script
 */
public class Path {
	private ArrayList<Node> nodes;
	private long wheight;
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public long getWheight() {
		return wheight;
	}
	public void setWheight(long wheight) {
		this.wheight = wheight;
	}
	public void addNode(Node node){
		nodes.add(node);
	}
    public void removeNode(Node node){
        nodes.remove(node);
    }
    public void addNode(int index, Node node){
        nodes.add(index, node);
    }
	public void show(){
		for (Node node : nodes){
			System.out.print(node.getData() + " ");
		}
	}
}
