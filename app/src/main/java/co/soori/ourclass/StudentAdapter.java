package co.soori.ourclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.soori.ourclass.db.Student;

/**
 * Created by Soo-Young on 2018-02-27.
 */

public class StudentAdapter extends BaseAdapter {

    private List<Student> studentInfoList;
    private Context context;

    public StudentAdapter(List<Student> studentInfoList, Context context){
        this.studentInfoList = studentInfoList;
        this.context = context;
    }

    @Override
    public int getCount(){
        return studentInfoList.size();
    }

    @Override
    public Object getItem(int position){
        return studentInfoList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        StudentHolder holder = null;
        if(convertView==null){
            holder = new StudentHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.student_item, parent, false);
            holder.studName = (TextView) convertView.findViewById(R.id.student_name);
            holder.studId = (TextView) convertView.findViewById(R.id.student_id);
            holder.studPhone = (TextView) convertView.findViewById(R.id.student_phone);
            holder.studImage = (ImageView) convertView.findViewById(R.id.student_icon);
        }else{
            holder = (StudentHolder) convertView.getTag();
        }

        Student student = (Student) getItem(position);

        holder.studName.setText(student.getName());
        holder.studId.setText(student.get_id());
        holder.studPhone.setText(student.getPhone());
        holder.studEtc1.setText(student.getEtc1());
        holder.studEtc2.setText(student.getEtc2());

        convertView.setTag(holder);
        return convertView;

    }

    public class StudentHolder{
        public TextView studName;
        public TextView studId;
        public ImageView studImage;
        public TextView studPhone;
        public TextView studEtc1;
        public TextView studEtc2;
    }


}
