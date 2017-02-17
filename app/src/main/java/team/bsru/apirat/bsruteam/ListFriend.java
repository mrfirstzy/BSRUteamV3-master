package team.bsru.apirat.bsruteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ListFriend extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend);

        ListView listView = (ListView) findViewById(R.id.livFriend);

        //Create ListView
        try {

            GetUser getUser = new GetUser(ListFriend.this);
            MyConstant myConstant = new MyConstant();
            getUser.execute(myConstant.getUrlPHPString());
            String strJSON = getUser.get();

            JSONArray jsonArray = new JSONArray(strJSON);
            final String[] nameStrings = new String[jsonArray.length()];
            final String[] imageStrings = new String[jsonArray.length()];
            final String[] latStrings = new String[jsonArray.length()];
            final String[] lngStrings = new String[jsonArray.length()];

            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStrings[i] = jsonObject.getString("Name");
                imageStrings[i] = jsonObject.getString("Image");
                latStrings[i] = jsonObject.getString("Lat");
                lngStrings[i] = jsonObject.getString("Lng");


            }   //for

            FriendAdapter friendAdapter = new FriendAdapter(ListFriend.this,
                    imageStrings,nameStrings);

            listView.setAdapter(friendAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ListFriend.this, Detail_Friend.class);
                    intent.putExtra("Name", nameStrings[position]);
                    intent.putExtra("Image", imageStrings[position]);
                    intent.putExtra("Lat", latStrings[position]);
                    intent.putExtra("Lng", lngStrings[position]);
                    startActivity(intent);

                }
            });


        } catch (Exception e) {
            Log.d("17febV3", "e ListView ==>" + e.toString());
        }


    }   //Main Method
}
