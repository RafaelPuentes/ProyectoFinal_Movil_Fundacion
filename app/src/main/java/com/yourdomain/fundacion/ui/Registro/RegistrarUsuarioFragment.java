package com.yourdomain.fundacion.ui.Registro;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.yourdomain.fundacion.R;

import org.json.JSONObject;

public class RegistrarUsuarioFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    EditText campoDocumennto,campoNombre,campoTelefono,campoEdad,campoFecha;
    Button registro;
    ProgressDialog progreso;
    RequestQueue rq;
    JsonRequest jrq;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View vista= inflater.inflate(R.layout.fragment_registrarusuario, container, false);
        campoDocumennto= vista.findViewById(R.id.documento);
        campoNombre= vista.findViewById(R.id.nombre);
        campoTelefono= vista.findViewById(R.id.telefono);
        campoEdad= vista.findViewById(R.id.edad);
        campoFecha= vista.findViewById(R.id.ingreso);
        registro= vista.findViewById(R.id.registro);

        rq = Volley.newRequestQueue(getContext());
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
        return vista;
    }

    private void cargarWebService() {
        progreso= new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String url = "http://192.168.1.5/fundacion/registrarse.php?documento="+campoDocumennto.getText().toString()+
                "&nombre="+campoNombre.getText().toString()+"&telefono="+campoTelefono.getText().toString()+"&edad="+campoEdad.getText().toString()+"" +
                "&fecha="+campoFecha.getText().toString();


        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se pudo registrar"+error.toString(),Toast.LENGTH_SHORT).show();

    }

    void limpiarCajas() {
        campoDocumennto.setText("");
        campoNombre.setText("");
        campoTelefono.setText("");
        campoEdad.setText("");
        campoFecha.setText("");
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(),"Se ha registrado exitosamente",Toast.LENGTH_SHORT).show();
        progreso.hide();
        limpiarCajas();


    }
}
