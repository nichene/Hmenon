package com.ufrpe.hmenon.graph.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe que representa um roteiro com uma coleção de caminhos
 *
 * @see com.ufrpe.hmenon.graph.domain.Path
 */
public class Script {
    /**
     * Nó inicial de onde parte todos os caminhos
     */
	private Node origin;
	private List<Path> paths = new ArrayList<>();
	
	public Node getOrigin() {
		return origin;
	}
	public void setOrigin(Node origin) {
		this.origin = origin;
	}
	public List<Path> getPaths() {
		return paths;
	}
	public void setPaths(ArrayList<Path> paths) {
		this.paths = paths;
	}

    /**
     * Cria os caminhos possiveis com base no peso limite e nos nós disponiveis
     *
     * @param limitWheight Peso limite que o caminho pode atingir
     * @param closedNodes Coleção de nós que devem ser desconsiderados para os caminhos
     */
	public void generatePlan(long limitWheight, ArrayList<Node> closedNodes){
		Path path = new Path();
		path.setNodes(new ArrayList<Node>());
		path.setWheight(0);
        if (origin!=null) {
            blowNode(origin, path, limitWheight, 0, closedNodes);
        }
	}

    /**
     * Função recursiva que percorre o grafo formando os caminhos
     *
     * @param node Nó de referência
     * @param currentPath Caminho construído até o momento
     * @param limit Peso limite do caminho
     * @param travelTime Peso da aresta do nó anterior ao nó de referencia
     * @param closedNodes Coleção de nós que devem ser desconsiderados para os caminhos
     */
	private void blowNode(Node node, Path currentPath, long limit, long travelTime, ArrayList<Node> closedNodes){
		long wheight = currentPath.getWheight() + node.getCost();
		if (limit < wheight){
            currentPath = review(currentPath, closedNodes);
            if (!checkDuplicate(currentPath.getNodes())) {
                this.paths.add(currentPath);
            }
			return;
		}

        Node n = new Node(node.getData());
        n.setCost(node.getCost());
        n.setChecked(node.getChecked());
        n.setConections(node.getConections());
        n.setTravelTime(travelTime);
        currentPath.addNode(n);
        long currentWheight = wheight;
		if (node.hasOpenedConnection(currentPath.getNodes())){
			for (Edge edge : node.getOpenConnections(currentPath.getNodes())){
                currentWheight += edge.getDistance();
                Path path = new Path();
                ArrayList<Node> clone = (ArrayList<Node>) currentPath.getNodes().clone();
                path.setNodes(clone);
                path.setWheight(currentWheight);
                currentWheight = wheight;
                blowNode(edge.getNodeB(), path, limit, edge.getDistance(), closedNodes);
			}
		}
		else {
            currentPath = review(currentPath, closedNodes);
            if (!checkDuplicate(currentPath.getNodes())) {
                this.paths.add(currentPath);
            }
		}
	}

    /**
     * Verifica se já existe o mesmo caminho formado
     *
     * @param nodes Coleção de nós a ser comparada
     * @return true se houver o caminho já formado, false caso contrário
     */
    private boolean checkDuplicate(ArrayList<Node> nodes){
        boolean duplicated = false;
        for (Path p : paths){
            ArrayList<Node> n = p.getNodes();
            if (nodes.equals(n)){
                duplicated = true;
            }

        }
        return duplicated;
    }

    /**
     * Verifica se o Nó está contido na coleção com base no atributo Name do atributo Data do nó
     *
     * @param list Coleção de nós
     * @param node Nó a ser verificado
     * @return true se o nó está contido, false caso contrário
     */
    public boolean contains(ArrayList<Node> list, Node node){
        boolean contains = false;
        for (Node n : list) {
            if (n.getData().getName().equals(node.getData().getName())){
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Filtra o caminho retirando os nós que devem ser desconsiderados
     *
     * @param path Caminho verificado
     * @param closed Coleção de nós que devem ser desconsiderados
     * @return Caminho atualizado
     */
    public Path review(Path path, ArrayList<Node> closed){
        ArrayList<Node> nodes = path.getNodes();
        for (Iterator<Node> iter = nodes.iterator(); iter.hasNext(); ){
            Node nodeTest = iter.next();
            if (contains(closed, nodeTest)){
                int index = path.getNodes().indexOf(nodeTest);
                Node addNode = path.getNodes().get(index);
                addNode.setTravelTime(addNode.getTravelTime()+nodeTest.getTravelTime());
                iter.remove();
            }
        }
        path.setNodes(nodes);
        return path;
    }

}
