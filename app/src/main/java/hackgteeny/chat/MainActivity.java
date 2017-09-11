package hackgteeny.chat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView1 = (ListView) findViewById(R.id.listView1);
        final EditText editText1=(EditText) findViewById(R.id.editText1);
        ArrayList<String> chatLines = new ArrayList<>();
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chatLines);
        listView1.setAdapter(listAdapter);
        listAdapter.add("hello");
        chatLines.add("world");
        Button button= (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listAdapter.add(editText1.getText().toString());
                editText1.setText("");
                listView1.setSelection(listAdapter.getCount() - 1);
            }
        });
    }
}
