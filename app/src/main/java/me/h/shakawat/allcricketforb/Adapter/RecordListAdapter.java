package me.h.shakawat.allcricketforb.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.h.shakawat.allcricketforb.Model.Model;
import me.h.shakawat.allcricketforb.R;

public class RecordListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Model> recordList;

    public RecordListAdapter(Context context, int layout, ArrayList<Model> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }



    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    public class ViewHolder{
        ImageView imageViews;
        TextView txtName,txtPost;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtName = row.findViewById(R.id.txtName);
            holder.txtPost = row.findViewById(R.id.txtPost);
            holder.imageViews = row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Model model = recordList.get(position);

        holder.txtName.setText(model.getName());
        holder.txtPost.setText(model.getPost());

        byte[] recordImage = model.getImage();

        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
        holder.imageViews.setImageBitmap(bitmap);

        return row;
    }
}
