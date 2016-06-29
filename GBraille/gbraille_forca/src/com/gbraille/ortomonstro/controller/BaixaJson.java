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
 
public class BaixaJson {
 
    
     
    // json array response url
    private static String urlJsonArry = "http://pesquisa.great.ufc.br/greattourv2/banco.json";
 
    private static String TAG = BaixaJson.class.getSimpleName();
   
 
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
                                String question = person.getString("questao");
                                String pergunta = person.getString("question");
                                String resposta = person.getString("answer");
                                String letraFaltaPos = person.getString("missingCharPos");
                                
                                
                                //descomenta à partir daqui :)
                                int dificuldade = Integer.parseInt(person.getString("dificuldade"));
                                int jogo = Integer.parseInt(person.getString("jogo"));
                                int linguagem = Integer.parseInt(person.getString("lingua"));
                                
                                System.out.println("TÁ LENDO!!");
                                System.out.println("AQUI" + dificuldade);
                                
                               
                                DbAdapter.insertQuestion(pergunta, resposta, letraFaltaPos, dificuldade, jogo, linguagem);
                               
 
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