package team.bsru.apirat.bsruteam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ikool009 on 10/2/2560.
 */

public class MyAlert {
    // Explicit
    private Context context;

    public MyAlert(Context context) {
        this.context = context;
    }// constructs
    public void myDialog(String strTitle,String strMessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);// undo fase
        builder.setIcon(R.drawable.rat48);
        builder.setTitle(strTitle);
        builder.setMessage(strMessage);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.show();
    }

}// main class
