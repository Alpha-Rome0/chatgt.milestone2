package hackgteeny.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends Activity {
    String TAG = "gtchat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView1 = (ListView) findViewById(R.id.listView1);
        final EditText editText1 = (EditText) findViewById(R.id.editText1);
        final ArrayList<String> chatLines = new ArrayList<>();
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chatLines);
        listView1.setAdapter(listAdapter);
        Button button = (Button) findViewById(R.id.button1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("chat");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myRef.push().setValue(editText1.getText().toString());
                editText1.setText("");
            }
        });

        // Read from the database
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("CHAT",dataSnapshot.getValue(String.class));
                listAdapter.add(dataSnapshot.getValue(String.class));
                listView1.setSelection(listAdapter.getCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }
        };
        myRef.addChildEventListener(childEventListener);


    }
}
