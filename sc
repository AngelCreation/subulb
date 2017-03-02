package in.co.surya.suryabulb.Helper;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class SocketConnection extends AsyncTask<String, Void, String> {
    String host = "10.10.100.254";
    int port = 8899;
    int len;
    Socket socket = new Socket();
    byte buf[] = new byte[255];

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
//                socket.bind(null);

            Log.e("Request",strings[0]);
            socket.connect((new InetSocketAddress(host, port)));
            DataOutputStream request = new DataOutputStream(socket.getOutputStream());
            DataInputStream response = new DataInputStream(socket.getInputStream());
            String command = strings[0];
            ByteArrayInputStream inputStream = null;
            inputStream = new ByteArrayInputStream(command.getBytes());
            while ((len = inputStream.read(buf)) != -1) {
                request.write(buf, 0, len);
            }
            ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream(255);

            StringBuffer stringBuffer = new StringBuffer();
            if ((len = response.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, len);
                stringBuffer.append(byteArrayOutputStream.toString("UTF-8"));
//                for (byte b : byteArrayOutputStream.toByteArray()){
//                    stringBuffer.append(String.format("%02X", b));
//                }
//                if (stringBuffer.toString().contains("@")) {
//                    break;
//                }
            }
            Log.e("Response",stringBuffer.toString());
            request.close();
            response.close();
            return stringBuffer.toString();
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {

                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
