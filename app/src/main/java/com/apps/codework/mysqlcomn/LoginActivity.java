package com.apps.codework.mysqlcomn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    public static String login=null;
    ConnectionClass connectionClass;
    ProgressDialog progressDialog;




    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        Button register =(Button)findViewById(R.id.email_register_button);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Registeru.class);
                startActivity(intent);
            }
        });


        connectionClass =new ConnectionClass();
        progressDialog=new ProgressDialog(this);


    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();



            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute("");

    }




    public class UserLoginTask extends AsyncTask<String, String, String> {

        private final String mEmail;
        private final String mPassword;
        Boolean isSuccess=false;
        String z;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }


        @Override
        protected void onPreExecute(){
                progressDialog.setMessage("Loading...");
                progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {


            try {
                // Simulate network access.
                Connection con=connectionClass.CONN();
                if(con==null){
                    z="Pls check internet connection";
                }
                /*else {
                    String query="insert into info values('"+mEmail+"','"+mPassword+"',0)";
                    Statement stmt=con.createStatement();
                    stmt.executeUpdate(query);
                    z="Register Successful";
                    isSuccess=true;
                }*/
                else{
                    String query="select * from info where name='"+mEmail+"'";
                    Statement stmt=con.createStatement();
                    ResultSet rs=stmt.executeQuery(query);


                    while(rs.next())
                    {
                        String paw=rs.getString("paw");
                        int x=rs.getInt("type");
                        Log.d("valueeeee",paw);
                        Log.d("valueeeee",mPassword);
                        Log.d("sdfsdf",String.valueOf(mPassword.equals(paw)));
                        if(mPassword.equals(paw))
                        {
                            Log.d("done","????");
                            if(x==1)
                            {
                                Intent intent = new Intent(LoginActivity.this, Adminscr.class);
                                login=mEmail;
                                startActivity(intent);

                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, Fstud1.class);
                                login=mEmail;
                                startActivity(intent);

                            }
                            //EditText editText = (EditText) findViewById(R.id.editText);
                             //String message = editText.getText().toString();
                            // intent.putExtra(EXTRA_MESSAGE, message);

                        }
                        else
                        {
                            //Toast.makeText(getBaseContext(),"Incorrect Password",Toast.LENGTH_SHORT).show();
                            Log.d("why",mPassword);
                        }

                    }
                    rs.close();
                    isSuccess=true;
                }
            } catch (Exception e) {
                isSuccess=false;
            }
            return z;
        }

        @Override
        protected void onPostExecute(String s) {
            mAuthTask = null;


            if (isSuccess) {
               // Toast.makeText(getBaseContext(),"done",Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
            else
            {
                Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

