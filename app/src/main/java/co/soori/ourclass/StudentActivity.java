package co.soori.ourclass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.soori.ourclass.db.Contact;
import co.soori.ourclass.db.DBContactHelper;

public class StudentActivity extends AppCompatActivity {

    private int totalSelected = 0;
    private ListView studentList;
    private ArrayList<listItem> listAllItems = new ArrayList<listItem>();
    private ArrayList<listItem> listDispItems = new ArrayList<listItem>();
    private ArrayList<String> itemFiles = new ArrayList<String>();
    private MyListAdapter listadapter;
    private EditText editView;

    private DBContactHelper db;
    private List<Contact> contacts;
    private FloatingActionButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        db = new DBContactHelper(this);

        contacts = db.getAllContacts();


        if(contacts.size()> 0) {
            setupList();
            setupAdapter();
            setupFilter();
        }

        addButton = (FloatingActionButton) findViewById(R.id.student_add_button);
        addButton.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View view){
               editView = (EditText) findViewById(R.id.student_filter);
               db.addContact(new Contact(editView.getText().toString()));
           }
        });

    }


    public class listItem{
        int selectedNumber;
        boolean checked;
        String picture_path;
        int id;
        String name;
        String phone;
        String etc1;
        String etc2;
    }

    public class AdapterAsyncTask extends AsyncTask<String,Void,String> {
        private ProgressDialog mDlg;
        Context mContext;

        public AdapterAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDlg = new ProgressDialog(mContext);
            mDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDlg.setMessage( "loading" );
            mDlg.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            // listAllItems MyListItem
            listAllItems.clear();
            listDispItems.clear();

            getStudentInfo();

            for(Contact cn : contacts){
                listItem item = new listItem();
                item.checked = false;
                item.id = cn.getID();
                item.name = cn.getName();
                item.phone = cn.getPhoneNumber();
                item.picture_path = null;
                item.etc1 = null;
                item.etc2 = null;
                listAllItems.add(item);
                Log.w("Add Item", item.name);
            }

            if (listAllItems != null) {
                Collections.sort(listAllItems, nameComparator);
            }
            listDispItems.addAll(listAllItems);
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mDlg.dismiss();
            listadapter = new MyListAdapter(mContext);
            studentList.setAdapter(listadapter);

            String searchText = editView.getText().toString();
            if( listadapter!=null ) listadapter.fillter(searchText);

        }

        private final Comparator<listItem> nameComparator
                = new Comparator<listItem>() {
            public final int
            compare(listItem a, listItem b) {
                return collator.compare(a.name, b.name);
            }
            private final Collator collator = Collator.getInstance();
        };
    }

    private void setupList(){
        studentList = (ListView) findViewById(R.id.student_list);
    }
    private void setupAdapter() {
        AdapterAsyncTask adaterAsyncTask = new AdapterAsyncTask(StudentActivity.this);
        adaterAsyncTask.execute();
    }

    private void setupFilter() {
        editView=(EditText)findViewById(R.id.student_filter);
        editView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String searchText = editView.getText().toString();
                if( listadapter!=null ) listadapter.fillter(searchText);
            }
        });
    }

    private int getSelectedItemCount() {
        int checkcnt = 0;
        for(int i=0;i<listDispItems.size();i++){
            StudentActivity.listItem item = listDispItems.get(i);
            if( item.checked )
                checkcnt++;
        }
        return checkcnt;
    }

    private List<String> getSelectedItems() {
        List<String> ret = new ArrayList<String>();
        int count = 0;
        for(int i=0;i<listDispItems.size();i++){
            StudentActivity.listItem item = listDispItems.get(i);
            if( item.checked ) {
                if( count < item.selectedNumber ){
                    count = item.selectedNumber;
                }
            }
        }
        for(int j=1;j<=count;j++) {
            for (int i = 0; i<listDispItems.size() ;i++ ){
                StudentActivity.listItem item = listDispItems.get(i);
                if( item.checked && item.selectedNumber == j){
                    ret.add(item.name);
                }
            }
        }
        return ret;
    }

    private String getSelectedItem() {
        List<String> ret = new ArrayList<String>();
        for(int i=0;i<listDispItems.size();i++){
            StudentActivity.listItem item = listDispItems.get(i);
            if( item.checked ) {
                return item.name;
            }
        }
        return "";
    }

    private void printDebug() {
        StringBuilder sb = new StringBuilder();
        sb.append("Count:"+getSelectedItemCount()+"\n");
        sb.append("getSelectedItem:"+getSelectedItem()+"\n");
        sb.append("getSelectedItems:");
        List<String> data = getSelectedItems();
        for(int i=0;i<data.size();i++){
            String item = data.get(i);
            sb.append(item+",");
        }
        //textView.setText(sb.toString());
    }

    public class MyListAdapter extends ArrayAdapter<listItem>{
        public MyListAdapter(Context context){
            super(context, R.layout.student_item);
            totalSelected = 0;
            setSource(listDispItems);
        }
        @Override
        public void bindView(View view, listItem item) {
            TextView name = (TextView) view.findViewById(R.id.student_name);
            name.setText(item.name);
            CheckBox cb = (CheckBox) view.findViewById(R.id.student_check);
            cb.setChecked(item.checked);
            ImageView imv = (ImageView) view.findViewById(R.id.file_icon);
            TextView _id = (TextView) view.findViewById(R.id.student_id);
            _id.setText(""+item.id);
            TextView phone = (TextView) view.findViewById(R.id.student_phone);
            phone.setText(item.phone);
            TextView etc1 = (TextView) view.findViewById(R.id.student_etc1);
            TextView etc2 = (TextView) view.findViewById(R.id.student_etc2);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View retView = super.getView(position, convertView, parent);
            final int pos = position;
            final View parView = retView;
            CheckBox cb = (CheckBox) retView.findViewById(R.id.student_check);

            cb.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    listItem item = listDispItems.get(pos);
                    item.checked = !item.checked;
                    if(item.checked) {
                        totalSelected++;
                        item.selectedNumber = totalSelected;
                        Toast.makeText(StudentActivity.this, "Select: " + item.name
                                + ", Total: " + totalSelected, Toast.LENGTH_SHORT).show();
                    } else {
                        totalSelected--;
                        item.selectedNumber = totalSelected;
                    }

                }
            });
            TextView name = (TextView)retView.findViewById(R.id.student_name);
            name.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    listItem item = listDispItems.get(pos);
                    itemClick(item.name);
                }
            });

            return retView;
        }


        public void fillter(String s){
            final String search = s;

            for(int i = 0;i<listAllItems.size();i++){
                listItem item = listAllItems.get(i);
            }

            if(search.length() == 0){
                listDispItems.addAll(listAllItems);

            } else if(search!=null && search.length()>0){
                listDispItems.clear();
//                totalSelected = 0;
                listItem item = new listItem();
                for(Contact cn : contacts){
                    if(cn.getName() == search){
//                        item.name = cn.getName();
                        item.id = cn.getID();
                        item.phone = cn.getPhoneNumber();
                        listDispItems.add(item);
                    }else if(cn.getID() == Integer.parseInt(search)){
//                        item.checked = false;
                        item.name = cn.getName();
                        item.id = cn.getID();
                        item.phone = cn.getPhoneNumber();
                        listDispItems.add(item);
                    }else if(cn.getPhoneNumber() == search){
                        //                        item.checked = false;
                        item.name = cn.getName();
                        item.id = cn.getID();
                        item.phone = cn.getPhoneNumber();
                        listDispItems.add(item);
                    }
                    notifyDataSetChanged();
                }
            }
        }


    }

    private void itemClick(String name){
        final String[] selection = new String[]{"상담", "수정", "삭제"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle(name+" 학생")
                .setItems(selection, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), selection[which]+"하기를 선택하셨습니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                           dialog.cancel();
                    }
                });
    }

    private void getStudentInfo(){}
}
