package com.example.pm_mal_pr01;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Editar extends AppCompatActivity implements View.OnClickListener {

    EditText nombre,usuario,clave;
    Button actualizar,eliminar,cancelar;
    private int id;
    RequestQueue requestQueue;
    private static final String url ="http://192.168.43.100/api/v1/eliminarEmpleado.php";
    private static final String url2 ="http://192.168.43.100/api/v1/editarEmpleado.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        nombre = findViewById(R.id.nombre);
        usuario = findViewById(R.id.usuario);
        clave = findViewById(R.id.clave);
        Bundle datos = this.getIntent().getExtras();
        this.id = datos.getInt("id");
        nombre.setText(datos.getString("nombre"));
        usuario.setText(datos.getString("usuario"));
        clave.setText(datos.getString("clave"));

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        actualizar=findViewById(R.id.actualizar);
        actualizar.setOnClickListener(this);
        eliminar = findViewById(R.id.eliminar);
        eliminar.setOnClickListener(this);
        cancelar = findViewById(R.id.cancelar);
        cancelar.setOnClickListener(this);

    }

    public void editarEmpleado(final int idEmpleado, final String name, final String user, final String clave){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url2,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Edición realizada exitosamente", Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms = new HashMap<String,String>();
                parms.put("id",String.valueOf(idEmpleado));
                parms.put("nombre",name);
                parms.put("usuario",user);
                parms.put("clave",clave);
                return parms;
            }
        };
        requestQueue.add(request);
    }

    public void eliminarEmpleado(final int idEmpleado) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Usuario eliminado satisfactoriamente", Toast.LENGTH_LONG).show();
                }
            },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parms = new HashMap<String,String>();
                parms.put("id",String.valueOf(idEmpleado));
                return parms;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.actualizar:
                String nom = nombre.getText().toString();
                String cla = clave.getText().toString();
                String use = usuario.getText().toString();
                editarEmpleado(id,nom,use,cla);
                if (!requestQueue.equals(null)) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.eliminar:
                eliminarEmpleado(id);
                if (!requestQueue.equals(null)) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.cancelar:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}