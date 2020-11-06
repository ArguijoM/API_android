package com.example.pm_mal_pr01;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class Agregar extends Fragment implements View.OnClickListener {
    View vista;
    EditText nombre,usuario,clave;
    Button guardar,cancelar;
    RequestQueue requestQueue;
    private static final String url ="http://192.168.43.100/api/v1/empleados.php";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Agregar() {
    }
    public static Agregar newInstance(String param1, String param2) {
        Agregar fragment = new Agregar();
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
        vista = inflater.inflate(R.layout.fragment_agregar, container, false);
        nombre = vista.findViewById(R.id.nombre);
        usuario = vista.findViewById(R.id.usuario);
        clave = vista.findViewById(R.id.clave);
        requestQueue = Volley.newRequestQueue(getContext());
        guardar = (Button)vista.findViewById(R.id.guardar);
        guardar.setOnClickListener(this);
        cancelar = (Button)vista.findViewById(R.id.cancelar);
        cancelar.setOnClickListener(this);

        return vista;
    }
    private void agregarEmpleado(){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            Toast.makeText(getContext(), "Usuario agregado satisfactoriamente", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext(),MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(), "Agrege los datos correspondientes", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("nombre",nombre.getText().toString());
                parametros.put("usuario",usuario.getText().toString());
                parametros.put("clave",clave.getText().toString());
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.guardar:
                agregarEmpleado();
                break;
            case R.id.cancelar:
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                break;
        }

    }
}