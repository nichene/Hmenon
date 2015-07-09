package com.ufrpe.hmenon.graph.domain;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;

import java.util.ArrayList;

public class Node {
	private TouristicPoint data;
	private int cost;
    private boolean checked;
    private long travelTime;
	private ArrayList<Edge> conections = new ArrayList<>();
	
	public Node(TouristicPoint dado){
		this.data = dado;
		this.cost = 0;
        this.checked = false;
	}
    public Node (TouristicPoint data, int cost){
        this.data = data;
        this.cost = cost;
        this.checked = false;
    }
	
	public TouristicPoint getData() {
		return data;
	}
	public void setData(TouristicPoint data) {
		this.data = data;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
    public boolean getChecked(){
        return this.checked;
    }
    public void setChecked(boolean checked){
        this.checked = checked;
    }
    public long getTravelTime() {
        return travelTime;
    }
    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }
    public ArrayList<Edge> getConections() {
		return conections;
	}
	public void setConections(ArrayList<Edge> conections) {
		this.conections = conections;
	}


	
	public void addConnection(Node node, int distance){
		Edge edge = new Edge(this, node, distance);
		conections.add(edge);
	}
	
	public boolean hasOpenedConnection(ArrayList<Node> closed){
		boolean open = false;
		for (Edge edge : conections){
			if (!closed.contains(edge.getNodeB())){
				open = true;
			}
		}
		return open;
	}
	public ArrayList<Edge> getOpenConnections(ArrayList<Node> closed){
		ArrayList<Edge> opened = new ArrayList<>();
		for (Edge edge : conections){
			if (!closed.contains(edge.getNodeB())){
				opened.add(edge);
			}
		}
		return opened;
	}
}
