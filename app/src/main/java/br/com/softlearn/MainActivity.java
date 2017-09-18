package br.com.softlearn;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends Activity {
	
	private static final String URL = "http://143.106.241.1/matioli/testeAPI/json/";
	private TextView txtResult;
    private ProgressBar progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		txtResult = (TextView) findViewById(R.id.txt_result);
        progress = (ProgressBar) findViewById(R.id.progress);


		InvokeWebServiceTask task = new InvokeWebServiceTask();
		task.execute();
	}

	private class InvokeWebServiceTask extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			progress.setVisibility(View.VISIBLE);
            txtResult.setVisibility(View.GONE);
		}

		@Override
		protected String doInBackground(Void... params) {
			return callWebServiceGet();
		}

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);
			try{
				progress.setVisibility(View.INVISIBLE);
				txtResult.setVisibility(View.VISIBLE);

				JSONObject dados = new JSONObject(s);
				String nome = dados.getString("nome");
				String ra = dados.getString("ra");
				Double media = Double.valueOf(dados.getString("media"));
				Integer faltas = Integer.valueOf(dados.getString("faltas"));
				txtResult.setText("RA : "+ra+"\nNome : "+nome+"\nMedia : "+media+ "\nfaltas : " +faltas);
			}
			catch(Exception e)
			{

			}
		}

		private String callWebServiceGet() {
            try {
                HttpClient http = new DefaultHttpClient();
                HttpGet get = new HttpGet(URL);
                HttpResponse response = http.execute(get);

                String result = EntityUtils.toString(response.getEntity());

                return result; //Integer.valueOf(result);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
	}
}



//	JSONObject root = new JSONObject(s);
//	JSONArray ar=root.getJSONArray("data");
//				for(int i=0;i<ar.length();i++){
//		JSONObject disc = new JSONObject(ar.getString(i));
//		txtResult.setText(txtResult.getText().toString()+"\n"+disc.getString("disciplina"));
//		}
//		txtResult.setText(s);
