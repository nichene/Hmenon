package com.ufrpe.hmenon.graph.domain;

import java.util.ArrayList;

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
	public void show(){
		for (Node node : nodes){
			System.out.print(node.getData() + " ");
		}
	}
}
