package com.ufrpe.hmenon.graph.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.graph.domain.Graph;
import com.ufrpe.hmenon.graph.domain.Node;
import com.ufrpe.hmenon.graph.domain.Path;
import com.ufrpe.hmenon.graph.domain.Script;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.gui.MainMyPage;

import java.util.ArrayList;


/**
 * Activity responsável pela exibição das opções de roteiro
 */
public class MainRouteSugestion extends ActionBarActivity {
    private ListView routes;
    private static ArrayList<Node> closedNodes;
    private ArrayList<Node> routeList;
    private ImageButton btnNext;
    private ImageButton btnPrev;
    private Script script;
    private Graph graph;
    private int currentPathIndex;
    private static long timeLimit;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMyPage = new Intent(MainRouteSugestion.this, MainMyPage.class);
        startActivity(intentGoMyPage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_sugestions);
        currentPathIndex = 0;
        graph = StaticUser.getGraph();
        script = new Script();
        script.setOrigin(graph.get(StaticUser.getCloserPoint()));
        script.generatePlan(timeLimit - StaticUser.getCloserTime(), closedNodes);
        routes = (ListView) findViewById(R.id.listRouteSugestion);
        btnNext = (ImageButton) findViewById(R.id.imageButtonNext);
        btnPrev = (ImageButton) findViewById(R.id.imageButtonPrevious);
        updateSugestion();
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPathIndex > 0){
                    currentPathIndex -= 1;
                    updateSugestion();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPathIndex < script.getPaths().size()-1){
                    currentPathIndex += 1;
                    updateSugestion();
                }
            }
        });
    }
    private void updateSugestion(){
        if (script.getPaths().size() == 0 || script.getPaths().get(currentPathIndex).getNodes().size()  == 0) {
            Toast.makeText(this, "Nenhum roteiro possível", Toast.LENGTH_SHORT).show();
        } else {
            Path path = script.getPaths().get(currentPathIndex);
            routeList = path.getNodes();
            routeList.add(0, new Node(new TouristicPoint()));
            populate();
        }
    }
    private void populate() {
        ArrayAdapter<Node> adapter = new ContactListAdapter();
        routes.setAdapter(adapter);
    }
    private class ContactListAdapter extends ArrayAdapter<Node> {
        public ContactListAdapter(){
            super(MainRouteSugestion.this, R.layout.route_list_item, routeList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.route_sugestion_list_item, parent, false);
            }
            TextView txtItemName = (TextView) view.findViewById(R.id.txtSugestionItem);
            TextView txtDistance = (TextView) view.findViewById(R.id.txtRouteDistance);
            if (position == 0){
                txtItemName.setText("Sua localização atual");
                txtDistance.setText("~ " + StaticUser.getCloserTime() + " minutos");
            } else {
                Node currentNode = routeList.get(position);
                TouristicPoint currentPoint = currentNode.getData();
                txtItemName.setText("- Visitar " + currentPoint.getName() + "\npor " + currentNode.getCost() + " minutos");
                try {
                    Node nextNode = routeList.get(position + 1);
                    txtDistance.setText("~ " + nextNode.getTravelTime() + " minutos");
                } catch (Exception e) {
                    txtDistance.setText("");
                    ImageView imgRoute = (ImageView) view.findViewById(R.id.imgRoute);
                    imgRoute.setImageResource(android.R.color.transparent);
                }
            }
            return view;
            }
        }

    public static void setClosedNodes(ArrayList<Node> closedNodes) {
        MainRouteSugestion.closedNodes = closedNodes;
    }
    public static void setTimeLimit(long limit){
        timeLimit = limit;
    }
}
