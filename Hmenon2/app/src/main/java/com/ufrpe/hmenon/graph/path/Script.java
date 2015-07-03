package com.ufrpe.hmenon.graph.path;

import java.util.ArrayList;
import java.util.List;
import com.ufrpe.hmenon.graph.domain.Edge;
import com.ufrpe.hmenon.graph.domain.Node;

public class Script {
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
	public void generatePlan(long limitWheight){
		Path path = new Path();
		path.setNodes(new ArrayList<Node>());
		path.setWheight(0);
        if (origin!=null) {
            blowNode(origin, path, limitWheight);
        }
	}
	
	private void blowNode(Node node, Path currentPath, long limit){
		long wheight = currentPath.getWheight() + node.getCost();
		if (limit < wheight){
            if (!checkDuplicate(currentPath.getNodes())) {
                this.paths.add(currentPath);
            }
			return;
		}
		currentPath.addNode(node);
		long currentWheight = wheight;
		if (node.hasOpenedConnection(currentPath.getNodes())){
			for (Edge edge : node.getOpenConnections(currentPath.getNodes())){
				currentWheight += edge.getDistance();
				Path path = new Path();
				ArrayList<Node> clone = (ArrayList<Node>) currentPath.getNodes().clone();
				path.setNodes(clone);
				path.setWheight(currentWheight);
				currentWheight = wheight;
				blowNode(edge.getNodeB(), path, limit);
			}
		}
		else {
            if (!checkDuplicate(currentPath.getNodes())) {
                this.paths.add(currentPath);
            }
		}
	}
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

}
