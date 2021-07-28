package com.example.ncr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Handler mainHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new BackgroundCheck()).start();
    }

    public void transactionFoundOrNot(boolean found){
        if(found){
            customExitDialog().show();
        }
        else{
            ExitBox("Exit", "No Transaction Available at this time.").show();
        }
    }

    public AlertDialog ExitBox(String Title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(message);
        builder.setTitle(Title);
        builder.setCancelable(false);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
                finish();
            }
        });
        return builder.create();
    }


    public Dialog customExitDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_transaction_display);

        String txn_type = FetchTransaction.getInstance().getTxnType();
        String txn_id = Integer.toString(FetchTransaction.getInstance().getTxnId());
        String txn_amount = '$'+Double.toString(FetchTransaction.getInstance().getAmount());

        TextView txnType = (TextView) dialog.findViewById(R.id.txnType);
        TextView txnID = dialog.findViewById(R.id.txnID);
        TextView amount = dialog.findViewById(R.id.Amount);

        txnType.setText(txn_type);
        txnID.setText(txn_id);
        amount.setText(txn_amount);

        Button dialogButtonYes = dialog.findViewById(R.id.yess_transaction);
        Button dialogButtonNo = dialog.findViewById(R.id.no_transaction);

        dialog.setCancelable(false);
        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionFail();
                dialog.dismiss();
                finish();
            }
        });

        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionSuccess();
                dialog.dismiss();
                finish();
            }
        });

        return dialog;
    }

    public void onBackPressed(){
        ExitBox("Exit", "Going back means cancelling the transaction").show();
    }

    public void transactionSuccess(){
        boolean val = TransactionInfo.getInstance().authorise();
        String message = "Transaction Success";
        if( !val){
            message = "Transaction Failed";
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void transactionFail(){
        boolean val = TransactionInfo.getInstance().deauthorise();
        String message = "Issue caught";
        if( val){
            message = "Transaction Failed";
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    class BackgroundCheck implements Runnable{
        boolean flag = false;
        @Override
        public void run() {
            try {
                while(!flag) {
                    if (FetchTransaction.getInstance().check()) {
                        flag = true;
                    }
                    Thread.sleep(5000);
                }
            }
            catch (Exception e){
                flag = false;
            }
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    transactionFoundOrNot(flag);
                }
            });
        }
    }
}
