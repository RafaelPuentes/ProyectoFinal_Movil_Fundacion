package com.yourdomain.fundacion.ui.Consulta;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.yourdomain.fundacion.R;
import com.yourdomain.fundacion.ui.adapter.AdapterDatos;
import com.yourdomain.fundacion.ui.adapter.usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultarListaFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{

    public ConsultarListaFragment() {
        // Required empty public constructor
    }

    ArrayList<usuario> listDatos;
    RecyclerView recyclerUsuario;
    ProgressDialog progress;
    RequestQueue rq;
    JsonRequest jrq;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_consultarlista, container, false);
        listDatos=new ArrayList<>();
        recyclerUsuario=(RecyclerView) view.findViewById(R.id.recyclerId);
        recyclerUsuario.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerUsuario.setHasFixedSize(true);
        rq = Volley.newRequestQueue(getContext());

        cargarWebService();

        return view;

    }

    private void cargarWebService() {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();
        String url="http://192.168.1.5/fundacion/consultarlista.php";

        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progress.hide();
        Toast.makeText(getContext(),"No se pudo consultar"+error.toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        usuario usuario =null;
        JSONArray json= response.optJSONArray("usuario");
        try {
        for (int i=0;i<json.length();i++){
            usuario=new usuario();
            JSONObject jsonObject=null;
            jsonObject=json.getJSONObject(i);

            usuario.setDocumento(jsonObject.optInt("documento"));
            usuario.setNombre(jsonObject.optString("nombre"));
            usuario.setTelefono(jsonObject.optString("telefono"));
            usuario.setEdad(jsonObject.optString("edad"));
            usuario.setFecha(jsonObject.optString("fecha"));
            listDatos.add(usuario);

        }
        progress.hide();
            AdapterDatos adapter=new AdapterDatos(listDatos);
            recyclerUsuario.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"No se ha podido establecer conexion con el servidor"+response,Toast.LENGTH_SHORT).show();
            progress.hide();
        }


    }
}
