package com.ufrpe.hmenon.graph.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.graph.domain.Node;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.user.gui.MainMyPage;

import java.util.List;

public class MainRoute extends ActionBarActivity {
    private ListView script;
    private Button btnCreateNewScript;
    private List<Node> scriptList;
    private static List<Node> staticScriptList;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMyPage = new Intent(MainRoute.this, MainMyPage.class);
        startActivity(intentGoMyPage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        script = (ListView) findViewById(R.id.listScript);
        btnCreateNewScript = (Button) findViewById(R.id.btnCreateNewScript);
        scriptList = staticScriptList;
        populate();
        script.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Node node = scriptList.get(position);
                node.setChecked(!node.getChecked());
            }
        });
    }

    private void populate() {
        ArrayAdapter<Node> adapter = new ContactListAdapter();
        script.setAdapter(adapter);
    }
    private class ContactListAdapter extends ArrayAdapter<Node> {
        public ContactListAdapter(){
            super(MainRoute.this, R.layout.route_list_item, scriptList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.route_list_item, parent, false);
            }
            Node currentNode = scriptList.get(position);
            TouristicPoint currentPoint = currentNode.getData();
            CheckBox item = (CheckBox) view.findViewById(R.id.checkBox);
            item.setText("Visitar " + currentPoint.getName());
            item.setChecked(currentNode.getChecked());
            return view;
        }
    }

    public static void setStaticScriptList(List<Node> staticScriptList) {
        MainRoute.staticScriptList = staticScriptList;
    }
}
