package com.ufrpe.hmenon.graph.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ufrpe.hmenon.R;
import com.ufrpe.hmenon.graph.domain.Node;
import com.ufrpe.hmenon.infrastructure.domain.StaticUser;
import com.ufrpe.hmenon.infrastructure.gui.MainActivity;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import com.ufrpe.hmenon.touristicpoint.service.TouristicPointBusiness;
import com.ufrpe.hmenon.user.gui.MainMyPage;

import java.util.ArrayList;
import java.util.List;


/**
 * Activity responsável pela exibição da lista de opções de pontos turísticos a serem visitados
 */
public class MainChoosePoints extends ActionBarActivity {
    private ListView script;
    private Button btnCreateNewScript;
    private List<Node> scriptList;
    private TouristicPointBusiness pointBusiness;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intentGoMyPage = new Intent(MainChoosePoints.this, MainMyPage.class);
        startActivity(intentGoMyPage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_points);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_white_24dp);

        StaticUser.setContext(this);
        script = (ListView) findViewById(R.id.listScript);
        pointBusiness = new TouristicPointBusiness(StaticUser.getContext());
        btnCreateNewScript = (Button) findViewById(R.id.btnCreateNewScript);
        scriptList = StaticUser.getGraph().getNodes();
        for (Node node : scriptList){
            node.setChecked(false);
        }
        populate();
        script.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Node node = scriptList.get(position);
                node.setChecked(!node.getChecked());
                populate();
            }
        });
        btnCreateNewScript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Node> notChecked = new ArrayList<>();
                for (Node node : scriptList){
                    if (!node.getChecked()){
                        notChecked.add(node);
                    } else {
                        TouristicPoint point = node.getData();
                        int checked = point.getChecked();
                        point.setChecked(checked +1);
                        String name = point.getName();
                        pointBusiness.checkUpdateChecked(name, checked);

                    }
                }

                MainRouteSugestion.setClosedNodes(notChecked);
                showDialog(MainChoosePoints.this);
            }
        });
    }

    private void populate() {
        ArrayAdapter<Node> adapter = new ContactListAdapter();
        script.setAdapter(adapter);
    }
    private class ContactListAdapter extends ArrayAdapter<Node> {
        public ContactListAdapter(){
            super(MainChoosePoints.this, R.layout.route_list_item, scriptList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.route_list_item, parent, false);
            }
            Node currentNode = scriptList.get(position);
            TouristicPoint currentPoint = currentNode.getData();
            ImageView checkImage = (ImageView) view.findViewById(R.id.imgChecked);
            TextView textView = (TextView) view.findViewById(R.id.txtNameCheck);
            textView.setText(currentPoint.getName());
            if (currentNode.getChecked()){
                checkImage.setImageResource(R.drawable.check_icon);
            }
            checkImage.setBackgroundResource(android.R.color.transparent);
            return view;
        }
    }
    public void showDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Definir novo roteiro");
        builder.setMessage("Quantas horas você tem disponível?");
        final EditText prompt = new EditText(this);
        prompt.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(prompt);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    int time = Integer.parseInt(prompt.getText().toString());
                    if (time <= 24 && time >= 0) {
                        MainRouteSugestion.setTimeLimit(time * 60);
                        finish();
                        Intent intentGoRouteSuggestion = new Intent(MainChoosePoints.this, MainRouteSugestion.class);
                        startActivity(intentGoRouteSuggestion);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intentGoHome = new Intent(StaticUser.getContext(), MainActivity.class);
                finish();
                startActivity(intentGoHome);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
