package allan.melo.projetocalculadoraarduino;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.projetocalculadoraarduino.R;

public class MainActivity extends Activity {
	String macadress = "00:12:03:28:02:71";
	Button btCalcular;
	EditText edtValA, edtValB, edtResult;
	RadioButton rdSomar, rdSubtrair, rdMultiplicar, rdDividir;


	// Somar +
	// Subtrair -
	// Multiplicar *
	// Dividir /

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		conexao c = conexao.obterInstancia(macadress);

		btCalcular = (Button) findViewById(R.id.btCalcular);
		edtValA = (EditText) findViewById(R.id.edtTxtValA);
		edtValB = (EditText) findViewById(R.id.edtTxtValB);
		edtResult = (EditText) findViewById(R.id.edtResultado);
		rdSomar = (RadioButton) findViewById(R.id.rdSomar);
		rdSubtrair = (RadioButton) findViewById(R.id.rdSubtrair);
		rdMultiplicar = (RadioButton) findViewById(R.id.rdMultiplicar);
		rdDividir = (RadioButton) findViewById(R.id.rdDividir);

	}

	public void calcular(View v) {
		if (!edtValA.getText().toString().equals("")
				&& !edtValB.getText().toString().equals("")) {
			String envio = "";
			envio = Double.parseDouble(edtValA.getText().toString()) + "0|";
			if (rdSomar.isChecked()) {
				envio += "+|";
			} else if (rdSubtrair.isChecked()) {
				envio += "-|";
			} else if (rdMultiplicar.isChecked()) {
				envio += "*|";
			} else if (rdDividir.isChecked()) {
				envio += "/|";
			}
			envio += Double.parseDouble(edtValB.getText().toString()) + "0|";
			Toast.makeText(this, envio, Toast.LENGTH_SHORT).show();
			conexao.enviarDados(envio);

			String dado = "", resultado = "";
			while (dado != null) {
				resultado += dado;
				dado = conexao.recebeDados();
			}
			edtResult.setText(resultado.replace("|", ""));

		} else {
			Toast.makeText(getApplicationContext(), "Insira todos os campos!!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
