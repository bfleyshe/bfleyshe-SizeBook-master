package com.example.bfleyshe.bfleyshe_sizebook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * The primary class that runs this application. In this class, user interaction and
 * control flow are maintained.
 *
 * @author bfleyshe
 * @version 1.0
 * @see Person
 * @since 1.0
 */
public class SizeBook extends AppCompatActivity {

    private static final String FILENAME = "file.sav";
    private static final Integer SelectPerson = 1;
    private ListView RecordsList;
    private Integer recordCount;

    private ArrayList<Person> personList;
    private ArrayAdapter<Person> adapter;
    private Integer interactMode = 0; // 0 is view, 1 is edit mode, 2 is delete: //TODO should be changed to enumeration



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_book);

        RecordsList = (ListView) findViewById(R.id.personList);
        Button addButton = (Button) findViewById(R.id.add);
        final Button editButton = (Button) findViewById(R.id.edit);
        final Button deleteButton = (Button) findViewById(R.id.delete);
        final TextView recordText = (TextView) findViewById(R.id.recordsView);  //records:num of records text at top

        RecordsList.setAdapter(adapter);

        // ListView on item selected listener.
        RecordsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               if(interactMode == 1){   //interactMode = edit
                   editButton.setBackgroundResource(android.R.drawable.btn_default);
                   Intent intent = new Intent(SizeBook.this, EditRecordsActivity.class);
                   intent.putExtra("person", personList.get(position));
                   intent.putExtra("position", position);
                   interactMode = 0;
                   startActivityForResult(intent, SelectPerson);
               }
               else if(interactMode == 2){  //interactMode = delete
                   personList.remove(position);
                   recordCount--;
                   recordText.setText("Records:" + recordCount, TextView.BufferType.EDITABLE);
                   saveInFile();
               }

               else if(interactMode == 0){ //interactMode = view
                   Intent intent = new Intent(SizeBook.this, ViewRecordsActivity.class);
                   intent.putExtra("person", personList.get(position));
                   startActivity(intent);
               }

               adapter.notifyDataSetChanged();
           }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);

                String name = "Name"; //creates record with the name "Name"

                try {
                    Person person = new Person(name);
                    personList.add(person);
                } catch (NameTooLongException e) {
                    e.printStackTrace();
                }

                recordCount++;
                recordText.setText("Records:" + recordCount, TextView.BufferType.EDITABLE); //updates recordCount

                interactMode = 0;   // returns to view mode
                editButton.setBackgroundResource(android.R.drawable.btn_default);   //resets interactMode and updates view
                deleteButton.setBackgroundResource(android.R.drawable.btn_default);

                adapter.notifyDataSetChanged();

                saveInFile();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {   //activates or deactivates edit mode
                setResult(RESULT_OK);
                if(interactMode == 1){
                    interactMode = 0;
                    //Button colors taken Feb 3rd, 2017 at 15:46.
                    //http://stackoverflow.com/questions/14802354/how-to-reset-a-buttons-background-color-to-default
                    editButton.setBackgroundResource(android.R.drawable.btn_default);
                }
                else {
                    interactMode = 1;
                    editButton.setBackgroundColor(Color.DKGRAY);
                    deleteButton.setBackgroundResource(android.R.drawable.btn_default);
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {   //activates or deactivates delete mode

                setResult(RESULT_OK);
                if(interactMode == 2){
                    interactMode = 0;
                    deleteButton.setBackgroundResource(android.R.drawable.btn_default);
                }
                else {
                    interactMode = 2;
                    editButton.setBackgroundResource(android.R.drawable.btn_default);
                    deleteButton.setBackgroundColor(Color.DKGRAY);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, personList);
        RecordsList.setAdapter(adapter);

        recordCount = personList.size();    //gets record count and updates it
        final TextView recordText = (TextView) findViewById(R.id.recordsView);
        recordText.setText("Records:" + recordCount, TextView.BufferType.EDITABLE);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SelectPerson) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                //Intent returnedIntent = getIntent();

                setResult(RESULT_OK);
                interactMode = 0;   // returns to view mode
                Person person = data.getParcelableExtra("person");  //retrieves person from EditActivity
                int position = data.getIntExtra("position", 0);

                personList.remove(position);
                personList.add(position, person);
                recordCount++;

                adapter.notifyDataSetChanged();
                saveInFile();
                RecordsList.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Loads records from specified file.
     *
     * @exception FileNotFoundException if the file is not created first.
     */
    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-24 18:19
            Type listType = new TypeToken<ArrayList<Person>>(){}.getType();
            personList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            personList = new ArrayList<Person>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    /**
     * Saves records into specified file.
     *
     */

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(personList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the Exception
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
