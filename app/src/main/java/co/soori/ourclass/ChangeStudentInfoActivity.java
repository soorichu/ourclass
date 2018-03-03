package co.soori.ourclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import co.soori.ourclass.db.Contact;
import co.soori.ourclass.db.DBContactHelper;

public class ChangeStudentInfoActivity extends AppCompatActivity {

    private EditText infoImage;
    private EditText infoId;
    private EditText infoName;
    private EditText infoPhone;
    private EditText infoEtc1;
    private EditText infoEtc2;
    private Button infoCancel;
    private Button infoOk;
    private Intent intent;

    private DBContactHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_student_info);
 //       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        infoId = findViewById(R.id.info_id);
        infoName = findViewById(R.id.info_name);
        infoPhone = findViewById(R.id.info_phone);
        infoOk = findViewById(R.id.info_ok);
        infoCancel = findViewById(R.id.info_cancel);
        db = new DBContactHelper(this);

        infoCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deleteInfo();
            }
        });

        infoOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                db.addContact(new Contact(Integer.parseInt(infoId.getText().toString()),
                        infoName.getText().toString(), infoPhone.getText().toString()));
                deleteInfo();
                intent = new Intent(ChangeStudentInfoActivity.this, StudentActivity.class);
            }
        });

        // 집어넣은 데이타 다시 읽어들이기
        Log.d("Reading: ", "Reading all contacts..");
//        db.addContact(new Contact("", "");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            Log.d("Name: ", log);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
                deleteInfo();
            }
        });
    }

    private void deleteInfo(){
        Toast.makeText(getApplicationContext(), "작성하던 자료는 사라집니다.", Toast.LENGTH_SHORT).show();

        infoId.setText(null);
        infoName.setText(null);
        infoPhone.setText(null);
        infoEtc1.setText(null);
        infoEtc2.setText(null);
    }

}
