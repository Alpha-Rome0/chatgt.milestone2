package hackgteeny.chat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends Activity {
    String TAG="gtchat";

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
                listAdapter.add(editText1.getText().toString());
                editText1.setText("");
                myRef.setValue(chatLines);
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                ArrayList<String> arr = dataSnapshot.getValue(t);
                if(arr!=null){
                    listAdapter.clear();
                    for(String s:arr)listAdapter.add(s);
                    listView1.setSelection(listAdapter.getCount() - 1);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



    }
}
