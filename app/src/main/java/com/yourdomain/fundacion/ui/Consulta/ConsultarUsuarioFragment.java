package com.yourdomain.fundacion.ui.Consulta;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.yourdomain.fundacion.ui.adapter.usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultarUsuarioFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener  {

    private SlideshowViewModel slideshowViewModel;
    EditText consulDocumento;
    TextView txtNombre,txtTelefono,txtEdad,txtFecha;
    Button consultar;
    ProgressDialog progreso;
    RequestQueue rq;
    JsonRequest jrq;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_consultarusuario, container, false);

        consulDocumento= vista.findViewById(R.id.consultarDocumento);
        txtNombre= vista.findViewById(R.id.consultarNombre);
        txtTelefono= vista.findViewById(R.id.consultarTele);
        txtEdad= vista.findViewById(R.id.consultarEdad);
        txtFecha= vista.findViewById(R.id.consultarFecha);

        consultar= vista.findViewById(R.id.btn_Consultar);
        rq = Volley.newRequestQueue(getContext());

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebServide();
            }
        });

        return vista;
    }

    private void cargarWebServide() {
        progreso= new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();
        String url="http://192.168.1.5/fundacion/consultar.php?documento="+consulDocumento.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se pudo consultar"+error.toString(),Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();

        Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        usuario miUsuario=new usuario();
        JSONArray json= response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            miUsuario.setNombre(jsonObject.optString("nombre"));
            miUsuario.setTelefono(jsonObject.optString("telefono"));
            miUsuario.setEdad(jsonObject.optString("edad"));
            miUsuario.setFecha(jsonObject.optString("fecha"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtNombre.setText("Nombre: "+miUsuario.getNombre());
        txtTelefono.setText("Telefono: "+miUsuario.getTelefono());
        txtEdad.setText("Edad: "+miUsuario.getEdad());
        txtFecha.setText("Fecha: "+miUsuario.getFecha());



    }
}
