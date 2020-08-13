package com.example.loterianavdrawer.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loterianavdrawer.MainActivity;
import com.example.loterianavdrawer.R;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    Button btn_login;
    EditText edtemail, edtpassword;
    String url = "http://www.ramiro174.com/oauth/token";
    private SharedPreferences credentials;
    String token;
    RequestQueue queue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = view.findViewById(R.id.btnLogin);
        edtemail = view.findViewById(R.id.edtUsuario);
        edtpassword = view.findViewById(R.id.edtPassword);

        queue = Volley.newRequestQueue(getContext());

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        return view;
    }

    private void login(){

        boolean filledInputs = false;



        if(!edtemail.getText().toString().equals("") && !edtpassword.getText().toString().equals(""))
        {
            filledInputs = true;
        }else{
            Toast.makeText(getContext(),"No hay datos en los campos", Toast.LENGTH_SHORT).show();
        }

        if(filledInputs){

            final String user = edtemail.getText().toString();
            final String password = edtpassword.getText().toString();

            final JSONObject data = new JSONObject();
            try {
                data.put("client_id", 6);
                data.put("client_secret", "otuoWd9Zyukpu8WRItXxicoLlpfXbOYAX0zv4tEu");
                data.put("grant_type", "password");
                data.put("username", user);
                data.put("password", password);
            }catch (JSONException e){
                e.printStackTrace();
            }


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    data,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                credentials = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = credentials.edit();

                                editor.putString("token", response.getString("access_token"));
                                editor.apply();

                                pass();

                            }
                            catch (JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();

                        }

                    });

            queue.add(jsonObjectRequest);

        }

    }

    private void pass(){

        credentials = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        if(credentials.getString("token",null) != null) {
            //Toast.makeText(getContext(), credentials.getString("token",null), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

}