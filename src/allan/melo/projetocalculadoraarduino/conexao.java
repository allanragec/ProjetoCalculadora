package allan.melo.projetocalculadoraarduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

public class conexao {
	private static conexao instancia = null;
	static BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();
	static String macend;
	static BluetoothDevice BTdev;
	static BluetoothSocket mmSocket;
	static BluetoothDevice mmDevice;
	static Method m;
	static int controle;

	public static conexao obterInstancia(String macaddress) {
		if (instancia == null || macend != macaddress && controle == 0) {
			instancia = new conexao();
			macend = macaddress;
			setBluetoothDevice();
		}
		return instancia;
	}

	public static void setBluetoothDevice() {
		try {
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
					.getBondedDevices();
			if (pairedDevices.size() > 0) {
				for (BluetoothDevice bluetoothDevice : pairedDevices) {
					if (bluetoothDevice.getAddress().equals(macend)) {
						BTdev = bluetoothDevice;
						ConnectThread(BTdev);
					}
				}
			}

		} catch (Exception e) {

			// nomelabel.setText(e.getMessage());

		}
	}

	public static void ConnectThread(BluetoothDevice device)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		 mmDevice = device;

		// Force a BluetoothSocket for a connection with the
		// given BluetoothDevice

		m = mmDevice.getClass().getMethod("createRfcommSocket",
				new Class[] { int.class });
		mmSocket = (BluetoothSocket) m.invoke(mmDevice,
				Integer.valueOf(1));
		try {
			mmSocket.connect();
			controle = 1;
		} catch (IOException e) {
			controle = 0;
		}
	}
	public static void enviarDados(String a){
	
		try {
			OutputStream mmOutStream = mmSocket.getOutputStream();
			mmOutStream.write(a.getBytes()); 
		} catch (IOException e) {
		}
	}
	public static String recebeDados(){
		String retorno = null;
		try {
			InputStream mmInputStream = mmSocket.getInputStream();
			int available = mmInputStream.available();
			if(available == 0){
				return null;
			}
			byte chunk[] = new byte[available];
			mmInputStream.read(chunk, 0, available);
			retorno = new String(chunk);
		} catch (IOException e) {
		}
		return retorno;
	}
	public static int getControleConexao(){
		return controle;
	}

}
