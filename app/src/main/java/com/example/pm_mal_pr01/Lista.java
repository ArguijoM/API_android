package com.example.pm_mal_pr01;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Lista extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    View vista;
    ListView lv;
    ArrayList<contacto> contactos;
    private static final String url ="http://192.168.43.100/api/v1/empleados.php";
    RequestQueue requestQueue; //Respuesta de la consulta

    private String mParam1;
    private String mParam2;

    public Lista() {
    }
    public static Lista newInstance(String param1, String param2) {
        Lista fragment = new Lista();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_lista, container, false);
        lv=vista.findViewById(R.id.listado);
        requestQueue = Volley.newRequestQueue(getContext());
        stringRequest();
        lv.setOnItemClickListener(this);

        return vista;
    }

    private void stringRequest(){
        contactos = new ArrayList<contacto>();//inicializamos la lista de contactos
        //inicializamos el adaptador
        final ListaAdaptador miAdaptador = new ListaAdaptador(getContext(),R.layout.diseno_lista,contactos);
        lv.setAdapter(miAdaptador);
        //Consulta
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String respuesta = jsonObject.getString("estado");
                            JSONArray jsonArray = jsonObject.getJSONArray("empleados");
                                for(int i=0; i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String nombre = object.getString("nombre");
                                    String usuario = object.getString("usuario");
                                    String clave = object.getString("clave");
                                    int id = object.getInt("id");
                                    int idcreador = object.getInt("idcreador");
                                    contacto con = new contacto(nombre,usuario,clave,id,idcreador);
                                    contactos.add(con);
                                    miAdaptador.notifyDataSetChanged();
                                }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error de respuesta", Toast.LENGTH_LONG).show();
                    }
                }
        ) ;
        requestQueue.add(request);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int idCon = contactos.get(position).getId();
        String nombre = contactos.get(position).getNombre();
        String usuario = contactos.get(position).getUsuario();
        String clave = contactos.get(position).getContrasena();
        Intent intent = new Intent(view.getContext(),Editar.class);
        intent.putExtra("id",idCon);
        intent.putExtra("nombre",nombre);
        intent.putExtra("usuario",usuario);
        intent.putExtra("clave",clave);
        startActivity(intent);
    }
}