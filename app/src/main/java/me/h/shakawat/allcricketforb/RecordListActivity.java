package me.h.shakawat.allcricketforb;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import me.h.shakawat.allcricketforb.Adapter.RecordListAdapter;
import me.h.shakawat.allcricketforb.Model.Model;

public class RecordListActivity extends AppCompatActivity {


    ListView mListView;
    ArrayList<Model> mList;
    RecordListAdapter mAdapter = null;

    ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);





        FloatingActionButton fabAll = findViewById(R.id.fabAllPost);
        fabAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),PostActivity.class));

            }
        });




        mListView = findViewById(R.id.listView);
        mList = new ArrayList<>();
        mAdapter = new RecordListAdapter(this,R.layout.row,mList);
        mListView.setAdapter(mAdapter);


        ///get all data from sqlite database
        Cursor cursor = PostActivity.mSQLiteHelper.getData("SELECT * FROM RECORD");
        mList.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String post = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            ///add to list
            mList.add(new Model(id,name,post,image));
        }
        mAdapter.notifyDataSetChanged();
        if (mList.size()==0){
            ///if there is no record in database which means list view is empty
            Toast.makeText(this, "No record found.", Toast.LENGTH_SHORT).show();
        }

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                ////alert dialog to display options of update and delete
                final CharSequence[] items = {"Update","Delete"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(RecordListActivity.this);

                dialog.setTitle("Choose on action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (which==0){
                            ////update....
                            Cursor c = PostActivity.mSQLiteHelper.getData("SELECT id FROM RECORD");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            ////show update dialog

                            showDialogUpdate(RecordListActivity.this,arrID.get(position));

                        }
                        if (which==1){
                            ///delete.....

                            Cursor c = PostActivity.mSQLiteHelper.getData("SELECT id FROM RECORD");
                            ArrayList<Integer> arrID = new ArrayList<>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            showDialogDelete(arrID.get(position));
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });

    }



    private void showDialogDelete(final int idRecord) {

        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(RecordListActivity.this);
        dialogDelete.setTitle("Warning....!!");
        dialogDelete.setMessage("Are you sure to delete");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    PostActivity.mSQLiteHelper.deleteData(idRecord);
                    Toast.makeText(RecordListActivity.this, "Delete SuccessFully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.e("error",e.getMessage());
                }
                updateRecordList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();

    }





    private void showDialogUpdate(Activity activity, final int position){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_dialog);
        dialog.setTitle("UpDate");

        imageViewIcon = dialog.findViewById(R.id.imageViewRecord);

        final EditText editName = dialog.findViewById(R.id.editName);
        final EditText editPost = dialog.findViewById(R.id.editPost);

        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);


        ///get data of row clicked from sqlite
        Cursor cursor = PostActivity.mSQLiteHelper.getData("SELECT * FROM RECORD WHERE id="+position);
        mList.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);

            String name = cursor.getString(1);
            editName.setText(name);///set name to update

            String post = cursor.getString(2);
            editPost.setText(post);///set age to update

            byte[] image = cursor.getBlob(3);
            ///set image got from sqlite
            imageViewIcon.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));

            ///add to list
            mList.add(new Model(id,name,post,image));
        }



        ///set width of dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels*0.95);
        ///set height of
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels*0.7);

        dialog.getWindow().setLayout(width,height);
        dialog.show();


        ////in update dialog click image view to update image
        imageViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///check external storage permission
                ///runtime permission for device android 6.0 and above
                ActivityCompat.requestPermissions(
                        RecordListActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );

            }
        });



        ////for update button .....
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PostActivity.mSQLiteHelper.upDateData(
                            editName.getText().toString().trim(),
                            editPost.getText().toString().trim(),
                            PostActivity.imageViewToByte(imageViewIcon),
                            position

                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated SuccessFully", Toast.LENGTH_SHORT).show();
                }
                catch (Exception error){
                    Log.e("Updated error",error.getMessage());
                }

                updateRecordList();

            }
        });

    }




    private void updateRecordList() {

        ////get all data from sqlite
        Cursor cursor = PostActivity.mSQLiteHelper.getData("SELECT * FROM RECORD");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String post = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            mList.add(new Model(id,name,post,image));
        }
        mAdapter.notifyDataSetChanged();

    }


    public static byte[] imageViewToByte(ImageView image) {

        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode==888) {

            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 888);
            }
            else {
                Toast.makeText(this, "Don't have permission to access file location", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==888 && resultCode==RESULT_OK)
        {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)////image will be square
                    .start(this);
        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                Uri resultUri = result.getUri();
                imageViewIcon.setImageURI(resultUri);
            }
            else if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




}