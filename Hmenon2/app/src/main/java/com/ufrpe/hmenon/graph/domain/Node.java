package com.ufrpe.hmenon.graph.domain;

import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;

import java.util.ArrayList;

/**
 * Representação dos nós para implementação de grafo.
 */
public class Node {
	/**
	 * Ponto turístico que o nó representa.
	 */
	private TouristicPoint data;
	/**
	 * Custo de tempo de visitação do ponto.
	 */
	private int cost;

    private boolean checked;
	/**
	 *
	 */
	private long travelTime;
	/**
	 * Lista de arestas que o nó conecta-se.
	 */
	private ArrayList<Edge> conections = new ArrayList<>();

	public Node(TouristicPoint data){
		this.data = data;
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

	/**
	 * Adiciona conexão com um outro nó do grafo.
	 *
	 * @param node Novo nó a quem este se conecta.
	 * @param distance Valor da distância entre este e o outro nó da conexão.
	 */
	public void addConnection(Node node, int distance){
		Edge edge = new Edge(this, node, distance);
		conections.add(edge);
	}

	/**
	 * Verifica se o nó possui conexão com
	 *
	 * @param closed Lista de nós.
	 * @return Booleano referente à.
	 */
	public boolean hasOpenedConnection(ArrayList<Node> closed){
		boolean open = false;

		for (Edge edge : conections){
			if (!contains(closed, edge.getNodeB())){
				open = true;
			}
		}

		return open;
	}

	/**
	 *
	 * @param closed
	 * @return
	 */
	public ArrayList<Edge> getOpenConnections(ArrayList<Node> closed){
		ArrayList<Edge> opened = new ArrayList<>();
		for (Edge edge : conections){
			if (!contains(closed, edge.getNodeB())){
				opened.add(edge);
			}
		}
		return opened;
	}
    public boolean contains(ArrayList<Node> list, Node node){
        boolean contains = false;
        for (Node n : list) {
            if (n.getData().getName().equals(node.getData().getName())){
                contains = true;
            }
        }
        return contains;
    }
}
