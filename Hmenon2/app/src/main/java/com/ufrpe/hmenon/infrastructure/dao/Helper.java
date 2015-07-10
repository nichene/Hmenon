package com.ufrpe.hmenon.infrastructure.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ufrpe.hmenon.touristicpoint.domain.History;
import com.ufrpe.hmenon.touristicpoint.domain.TouristicPoint;
import org.w3c.dom.Document;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Implementação de OpenHelper para as classes de DAO do projeto.
 */
public class Helper extends SQLiteOpenHelper{
    private static final String NAMEDB = "hmenon";
    private static final int VERSIONDB = 17;
    private Document document;

    public static final String TABLE_USER = "user";
    public static final String USER_ID = "_user_id";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "user_password";

    public static final String TABLE_TOURISTICPOINT = "touristic_point";
    public static final String TOURISTICPOINT_ID = "_touristic_point_id";
    public static final String TOURISTICPOINT_NAME = "touristic_point_name";
    public static final String TOURISTICPOINT_RESUME = "touristic_point_resume";
    public static final String TOURISTICPOINT_HISTORY = "touristic_point_history";
    public static final String TOURISTICPOINT_IMAGE = "touristic_point_image";
    public static final String TOURISTICPOINT_ACTIVITYTEXT = "touristic_point_activity_text";
    public static final String TOURISTICPOINT_ADDRESS = "touristic_point_adress";
    public static final String TOURISTICPOINT_MAP = "touristic_point_map";
    public static final String TOURISTICPOINT_COORDINATES = "touristic_point_coordinate";
    public static final String TOURISTICPOINT_CHECKED = "touristic_point_checked";

    public static final String TABLE_FAVOURITE = "favourite";
    public static final String FAVOURITE_ID = "favourite_id";
    public static final String FAVOURITE_USER_ID = "favourite_user_id";
    public static final String FAVOURITE_POINT_ID = "favourite_point_id";

    public Helper(Context context){
        super(context, NAMEDB, null, VERSIONDB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_USER + "(" +
                USER_ID + " integer primary key autoincrement," +
                USER_EMAIL + " text not null," +
                USER_NAME + " text not null," +
                USER_PASSWORD + " text not null);");
        db.execSQL("create table "+ TABLE_TOURISTICPOINT +"(" +
                TOURISTICPOINT_ID +" integer primary key autoincrement," +
                TOURISTICPOINT_NAME + " text not null,"+
                TOURISTICPOINT_RESUME +" text not null," +
                TOURISTICPOINT_HISTORY +" text not null," +
                TOURISTICPOINT_IMAGE + " text not null," +
                TOURISTICPOINT_ACTIVITYTEXT + " text not null," +
                TOURISTICPOINT_ADDRESS + " text not null," +
                TOURISTICPOINT_MAP + " text not null," +
                TOURISTICPOINT_CHECKED + " integer," +
                TOURISTICPOINT_COORDINATES + " text not null);");
        db.execSQL("create table " + TABLE_FAVOURITE + "(" +
                FAVOURITE_ID + " integer primary key autoincrement," +
                FAVOURITE_USER_ID + " integer, " +
                FAVOURITE_POINT_ID + " integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ TABLE_USER);
        db.execSQL("drop table if exists "+ TABLE_TOURISTICPOINT);
        db.execSQL("drop table if exists "+ TABLE_FAVOURITE);
        onCreate(db);
    }

    private void openFile(){
        File file = new File("/touristic_point_resource.xml");
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public TouristicPoint createPoint(String pointName){
        TouristicPoint point = new TouristicPoint();
        point.setName(document.getElementsByTagName(pointName+"Name").item(0).getTextContent());
        point.setHistory(new History());
        point.setHistoryResume(document.getElementsByTagName(pointName+"Resume").item(0).getTextContent());
        point.setHistoryText(document.getElementsByTagName(pointName+"History").item(0).getTextContent());
        point.setImage(document.getElementsByTagName(pointName+"Image").item(0).getTextContent());
        point.setActivityText(document.getElementsByTagName(pointName+"ActivityText").item(0).getTextContent());
        point.setCoordinates(document.getElementsByTagName(pointName+"Coordinates").item(0).getTextContent());
        point.setAddress(document.getElementsByTagName(pointName+"Address").item(0).getTextContent());
        point.setMap(document.getElementsByTagName(pointName+"Map").item(0).getTextContent());
        return point;
    }

    private void insertTouristicPoint(TouristicPoint point, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(Helper.TOURISTICPOINT_NAME, point.getName());
        values.put(Helper.TOURISTICPOINT_RESUME, point.getHistory().getResume());
        values.put(Helper.TOURISTICPOINT_HISTORY, point.getHistory().getCompleteHistory());
        values.put(Helper.TOURISTICPOINT_IMAGE, point.getImage());
        values.put(Helper.TOURISTICPOINT_ACTIVITYTEXT, point.getActivityText());
        values.put(Helper.TOURISTICPOINT_ADDRESS, point.getAddress());
        values.put(Helper.TOURISTICPOINT_MAP, point.getMap());
        values.put(Helper.TOURISTICPOINT_COORDINATES, point.getCoordinates());
        db.insert(Helper.TABLE_TOURISTICPOINT, null, values);
    }
}
