package com.example.ncr;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class FetchTransaction {
    private String txnType;
    private Integer txnId;
    private Double Amount;
    private static FetchTransaction instance;
//    private static final String URL = "http://192.168.1.11:8084/available-transaction";
    private static final String URL = "http://192.168.101.11:8084/CustomerMobileConformation/paymentResource/available-transaction?name=upendra";

    private FetchTransaction(){
        txnType = null;
        txnId = null;
        Amount = null;
    }

    public boolean check() throws IOException, JSONException {
        URL urlForGetRequest = new URL(URL);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        int responseCode = conection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            } in .close();
            if( !response.toString().equals("")){
                JSONObject jobj = new JSONObject(response.toString());
                txnId = jobj.getInt("transactionID");
                txnType = jobj.getString("transactionType");
                Amount = jobj.getDouble("amount");
            }
        }
        return txnId != null;
    }

    public void setNullAllValues(){
        txnType = null;
        txnId = null;
        Amount = null;
    }

    public String getTxnType(){
        return txnType;
    }

    public Integer getTxnId(){
        return txnId;
    }

    public Double getAmount(){
        return Amount;
    }

    public static FetchTransaction getInstance(){
        if( instance == null){
            instance = new FetchTransaction();
        }
        return instance;
    }

}

class TransactionInfo{
    static TransactionInfo instance;
    private static final String str_url = "http://192.168.101.11:8084/CustomerMobileConformation/paymentResource/authorisation?response=";

    public static TransactionInfo getInstance(){
        if( instance == null){
            instance = new TransactionInfo();
        }
        return instance;
    }
    private boolean check(){
        FetchTransaction obj = FetchTransaction.getInstance();
        return obj.getTxnId() != null && obj.getTxnType() != null && obj.getAmount() != null;
    }

    public boolean authorise() {
        try {
            Thread t1 = new Thread(new PostRequest(true));
            t1.start();
            t1.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        FetchTransaction.getInstance().setNullAllValues();
        return true;
    }

    public boolean deauthorise() {
        try {
            Thread t1 = new Thread(new PostRequest(false));
            t1.start();
            t1.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        FetchTransaction.getInstance().setNullAllValues();
        return true;
    }

    class PostRequest implements Runnable{
        boolean ch;

        PostRequest(boolean ch){
            this.ch = ch;
        }
        public void run(){
            try {
                if (check()) {

                    URL url = new URL(str_url+ch);
                    URLConnection con = url.openConnection();
                    HttpURLConnection http = (HttpURLConnection) con;
                    http.setRequestMethod("GET"); // PUT is another valid option
                    http.setDoOutput(false);

////                    String temp = "{\"auth\":\""+ Boolean.toString(ch) +"\"}";
//                    String temp = "{auth: "+ ch +" }";
//                    byte[] out = temp.getBytes(StandardCharsets.UTF_8);
//                    int length = out.length;

//                    http.setFixedLengthStreamingMode(length);
//                    http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    http.connect();
                    int responseCode = http.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        System.out.println("Post method called");
                    }
//                    try (OutputStream os = http.getOutputStream()) {
//                        os.write(out);
//                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        }
}

