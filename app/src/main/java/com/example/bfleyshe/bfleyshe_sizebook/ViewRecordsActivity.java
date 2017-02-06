package com.example.bfleyshe.bfleyshe_sizebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewRecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        //viewAble text
        TextView editNameText = (TextView) findViewById(R.id.nameView);
        TextView dateText = (TextView) findViewById(R.id.dateView);
        TextView neckText = (TextView) findViewById(R.id.neckView);
        TextView bustText = (TextView) findViewById(R.id.bustView);
        TextView chestText = (TextView) findViewById(R.id.chestView);
        TextView waistText = (TextView) findViewById(R.id.waistView);
        TextView hipText = (TextView) findViewById(R.id.hipView);
        TextView inseamText = (TextView) findViewById(R.id.inseamView);
        TextView commentText = (TextView) findViewById(R.id.commentView);

        Button backButton = (Button) findViewById(R.id.backButton);

        Intent intent = getIntent();
        final Person person = intent.getParcelableExtra("person");

        //loads data
        editNameText.setText("Name:" + person.getName(), TextView.BufferType.EDITABLE);
        dateText.setText("Date:" + person.getDate(), TextView.BufferType.EDITABLE);
        neckText.setText("Neck:" + person.getNeck(), TextView.BufferType.EDITABLE);
        bustText.setText("Bust:" + person.getBust(), TextView.BufferType.EDITABLE);
        chestText.setText("Chest:" + person.getChest(), TextView.BufferType.EDITABLE);
        waistText.setText("Waist:" + person.getWaist(), TextView.BufferType.EDITABLE);
        hipText.setText("Hip:" + person.getHip(), TextView.BufferType.EDITABLE);
        inseamText.setText("Inseam:" + person.getInseam(), TextView.BufferType.EDITABLE);
        commentText.setText("Comment:" + person.getComment(), TextView.BufferType.EDITABLE);


        //returns back to SizeBook
        backButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                finish();
            }
        });
    }


}
