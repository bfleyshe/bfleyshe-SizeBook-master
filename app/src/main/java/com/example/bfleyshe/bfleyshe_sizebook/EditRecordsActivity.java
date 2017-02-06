package com.example.bfleyshe.bfleyshe_sizebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class EditRecordsActivity extends AppCompatActivity {

    //edit fields
    private EditText editNameText;
    private EditText dateText;
    private EditText neckText;
    private EditText bustText;
    private EditText chestText;
    private EditText waistText;
    private EditText hipText;
    private EditText inseamText;
    private EditText commentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_records);

        editNameText = (EditText) findViewById(R.id.editTextName);
        dateText = (EditText) findViewById(R.id.dateText);
        neckText = (EditText) findViewById(R.id.neckText);
        bustText = (EditText) findViewById(R.id.bustText);
        chestText = (EditText) findViewById(R.id.chestText);
        waistText = (EditText) findViewById(R.id.waistText);
        hipText = (EditText) findViewById(R.id.hipText);
        inseamText = (EditText) findViewById(R.id.inseamText);
        commentText = (EditText) findViewById(R.id.commentText);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button backButton = (Button) findViewById(R.id.backButton);

        //Passing objects through intents, taken Feb 3rd at 17:20
        //http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
        Intent intent = getIntent();
        final Person person = intent.getParcelableExtra("person");
        final int position = intent.getIntExtra("position", 0);

        editNameText.setText(person.getName()); //sets all values from text fields to default values
        dateText.setText(person.getDate());
        neckText.setText(person.getNeck().toString());
        bustText.setText(person.getBust().toString());
        chestText.setText(person.getChest().toString());
        waistText.setText(person.getWaist().toString());
        hipText.setText(person.getHip().toString());
        inseamText.setText(person.getInseam().toString());
        commentText.setText(person.getComment());

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {   //saves values into person object
                try {
                    person.setName(editNameText.getText().toString());
                } catch (NameTooLongException e) {
                    e.printStackTrace();
                }
                person.setDate(dateText.getText().toString());
                person.setNeck(Float.parseFloat(neckText.getText().toString()));
                person.setBust(Float.parseFloat(bustText.getText().toString()));
                person.setChest(Float.parseFloat(chestText.getText().toString()));
                person.setWaist(Float.parseFloat(waistText.getText().toString()));
                person.setHip(Float.parseFloat(hipText.getText().toString()));
                person.setInseam(Float.parseFloat(inseamText.getText().toString()));
                person.setComment(commentText.getText().toString());

                Intent intent = new Intent(EditRecordsActivity.this, SizeBook.class);
                intent.putExtra("person", person);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("person", person);    //returns data
                returnIntent.putExtra("position", position);
                setResult(RESULT_OK, returnIntent);
                finish();

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {   //cancels edit

                finish();
            }
        });
    }


}
