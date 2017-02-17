package team.bsru.apirat.bsruteam;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class SingUpActivity extends AppCompatActivity {
    // ประกาศตัวแปล
    private EditText nameEditText, userEditText, passEditText;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private Button button;
    private String nameString, userString, passString,
            pathImageString, nameImageString;
    private Uri uri;
    private boolean aBoolean = true;
    private int anInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        // Bind Wiget
        bindWidget();

        ButtonControler();

        //Image Controller
        immageController();

        radioGroupController();


    } // Main Method

    private void radioGroupController() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton:
                        anInt = 0;
                        break;
                    case R.id.radioButton2:
                        anInt = 1;
                        break;
                    case R.id.radioButton6:
                        anInt = 2;
                        break;
                    case R.id.radioButton7:
                        anInt = 3;
                        break;
                    case R.id.radioButton8:
                        anInt = 4;
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

            aBoolean = false;
            uri = data.getData();
            // set up image choose to image view

            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();

            }
            // find path image Choose
            String[] strings = new String[]{MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, strings, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                pathImageString = cursor.getString(index);
            } else {
                pathImageString = uri.getPath();
            }
            Log.d("10febV1", "pathImage ==> " + pathImageString);



        }
    }// on Activity resule

    private void immageController() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"โปรดเลือกแอพดูภาพ"), 1);
            }
        });
    }

    private void ButtonControler() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get value From Edit text
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passString = passEditText.getText().toString().trim();

                // check space
                if (nameString.equals("") || userString.equals("") || passString.equals("")){
                    // true == > Have space
                    MyAlert myAlert = new MyAlert(SingUpActivity.this);
                    myAlert.myDialog("มีช่องว่าง","กรุณากรอกให้ครบ");

                } else if (aBoolean) {
                    // Non Choose image
                    MyAlert myAlert = new MyAlert(SingUpActivity.this);
                    myAlert.myDialog("ยังไม่เลือกรูปภาพ","กรุณาเลือกรูปภาพ");
                } else {
                    // EveayThing OK

                    uploadValueToServer();
                }


            }
        });
    }

    private void uploadValueToServer() {
        try {
            //Upload Image ทำให้ ใช้ protocal ได้
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                    .Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com",21,"bsru@swiftcodingthai.com","Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("/imges_apirat");
            simpleFTP.stor(new File(pathImageString));
            simpleFTP.disconnect();

            // Upload Text
            String tag = "10febV2";
            Log.d(tag, "Name ==>" + nameString);
            Log.d(tag, "User ==>" + userString);
            Log.d(tag, "Password ==>" + passString);

            nameImageString = "http://swiftcodingthai.com/bsru/imges_apirat" + pathImageString.substring(pathImageString.lastIndexOf("/"));
            Log.d(tag, "Image ==>" + nameImageString);
            Log.d(tag, "Avata ==>" + anInt);

            AddValueToUser addValueToUser = new AddValueToUser(SingUpActivity.this,
                    nameString, userString, passString, nameImageString,
                    Integer.toString(anInt));

            addValueToUser.execute("http://swiftcodingthai.com/bsru/add_master.php");
            String s = addValueToUser.get();
            Log.d(tag, "Result ==>" + s);
            MyAlert myAlert = new MyAlert(SingUpActivity.this);
            if (Boolean.parseBoolean(s)) {
                myAlert.myDialog("Upload","Upload True");
                finish();
            } else {

                myAlert.myDialog("Canot Upload","Upload False");
            }

        } catch (Exception e) {
            Log.d("10fabV1", "e upload ==>" + e.toString());
        }
    }

    private void bindWidget() {
        nameEditText = (EditText) findViewById(R.id.editText3);
        userEditText = (EditText) findViewById(R.id.editText4);
        passEditText = (EditText) findViewById(R.id.editText5);
        imageView = (ImageView) findViewById(R.id.imageView3);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvata);
        button = (Button) findViewById(R.id.regit);
    }// bind Widget
}
