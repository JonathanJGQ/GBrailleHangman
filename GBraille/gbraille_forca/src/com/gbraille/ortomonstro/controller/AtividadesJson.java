package com.gbraille.ortomonstro.controller;


 

 
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 

import android.R.integer;
import android.app.ProgressDialog;

import android.util.Log;


import android.widget.TextView;



import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gbraille.ortomonstro.R;
import com.gbraille.ortomonstro.database.DbAdapter;
 
public class AtividadesJson {
 
    
     
    // json array response url
    private static String urlJsonArry = "http://gbraile-brsilva.rhcloud.com/rest/gbraile/student/jonathan@gmail/";
 
    private static String TAG = AtividadesJson.class.getSimpleName();
   
 
    // Progress dialog
    private static ProgressDialog pDialog;
 
    private TextView txtResponse;
 
    // temporary string to show the parsed response
    private static String jsonResponse;
 
    
  
 
    /**
     * Method to make json object request where json response starts wtih {
     * */
    
    /**
     * Method to make json array request where response starts with [
     * */
    public static void makeJsonArrayRequest() {
    	
        //showpDialog();
    	System.out.println("TAMO NO MÉTODO!!");
 
        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    	
                        //Log.d(TAG, response.toString());
                        System.out.println("ANTES DO TRY!!");
                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {
 
                               JSONObject person = (JSONObject) response.get(i);
 
                               person.toString();
                                
                                //seta as paradas aqui :)
                                String codigo = person.getString("code");
                                String nome = person.getString("name");
                                String dataInicial = person.getString("dateInitial");
                                String dataFinal = person.getString("dateFinal");
                                String numeroDeQuestoes = person.getString("numberOfQuestion");
                                String descricao = person.getString("descripton");
                                String level = person.getString("level");
                                
                                
                                System.out.println("TÁ LENDO!!");
                                System.out.println("AQUI" + nome);
                                
                               
                                DbAdapter.insertAtividade(codigo, nome, dataInicial, dataFinal, numeroDeQuestoes, descricao, level);
                               
 
                            }
 
                            
 
                        } catch (JSONException e) {
                            e.printStackTrace();
                            
                        }
 
                        //hidepDialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                       // hidepDialog();
                    }
                });
 
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
 
    /*private static void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
 
    private static void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/
}