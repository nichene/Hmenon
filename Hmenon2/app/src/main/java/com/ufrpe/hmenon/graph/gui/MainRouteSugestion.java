package com.ufrpe.hmenon.graph.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.graph.domain.Graph;
import com.ufrpe.hmenon.graph.domain.Node;
import com.ufrpe.hmenon.graph.path.Path;
import com.ufrpe.hmenon.graph.path.Script;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.gui.MainMyPage;

import java.util.ArrayList;


public class MainRouteSugestion extends ActionBarActivity {
    private ListView routes;
    private ArrayList<Node> routeList;
    private ImageButton btnNext;
    private ImageButton btnPrev;
    private Button btnOkRoute;
    private Script script;
    private Graph graph;
    private int currentPath;
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
        currentPath = 0;
        graph = StaticUser.getGraph();
        script = new Script();
        script.setOrigin(graph.get(StaticUser.getCloserPoint()));
        script.generatePlan(timeLimit);
        routes = (ListView) findViewById(R.id.listRouteSugestion);
        btnNext = (ImageButton) findViewById(R.id.imageButtonNext);
        btnPrev = (ImageButton) findViewById(R.id.imageButtonPrevious);
        btnOkRoute = (Button) findViewById(R.id.btnOkRoute);
        updateSugestion();
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPath > 0){
                    currentPath -= 1;
                    updateSugestion();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPath < script.getPaths().size()-1){
                    currentPath += 1;
                    updateSugestion();
                }
            }
        });
        btnOkRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Path path = script.getPaths().get(currentPath);
                MainRoute.setStaticScriptList(path.getNodes());
                Intent intentGoScript = new Intent(MainRouteSugestion.this, MainRoute.class);
                startActivity(intentGoScript);
            }
        });
    }
    private void updateSugestion(){
        if (script.getPaths().size() == 0 || script.getPaths().get(currentPath).getNodes().size()  == 0) {
            btnOkRoute.setEnabled(false);
            Toast.makeText(this, "Nenhum roteiro possível", Toast.LENGTH_SHORT).show();
        } else {
            Path path = script.getPaths().get(currentPath);
            routeList = path.getNodes();
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
            Node currentNode = routeList.get(position);
            TouristicPoint currentPoint = currentNode.getData();
            TextView txtItemName = (TextView) view.findViewById(R.id.txtSugestionItem);
            txtItemName.setText("- Visitar " + currentPoint.getName());
            return view;
            }
        }
    public static void setTimeLimit(long limit){
        timeLimit = limit;
    }
}
