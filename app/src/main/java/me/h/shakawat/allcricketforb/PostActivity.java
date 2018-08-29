package me.h.shakawat.allcricketforb;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

import me.h.shakawat.allcricketforb.SQLiteDatabase.SQLiteHelper;

public class PostActivity extends AppCompatActivity {

    EditText mEditName,mEditPost;
    Button mBtnAdd,mBtnList;
    ImageView mImageView;

    final int REQUEST_CODE_GALLERY = 999;


    public static SQLiteHelper mSQLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);





        mEditName = findViewById(R.id.editName);
        mEditPost = findViewById(R.id.editPost);

        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnList = findViewById(R.id.btnList);

        mImageView = findViewById(R.id.imageView);




        ////creating data base
        mSQLiteHelper = new SQLiteHelper(this,"RECORDDB.sqlite",null,1);

        ///creating table in database
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS RECORD(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, post VARCHAR, image BLOB)");






        ////select image by on imageview click
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///runtime permission for device android 6.0 and above
                ActivityCompat.requestPermissions(
                        PostActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );

            }
        });





        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String eName = mEditName.getText().toString().trim();
                String ePost = mEditPost.getText().toString().trim();
                byte[] imageV = imageViewToByte(mImageView);


                if (eName.isEmpty()||ePost.isEmpty()){
                    mEditName.setError("Name Error");
                    mEditPost.setError("Description missing");
                }
                else {
                    try {
                        mSQLiteHelper.insertData(eName, ePost,imageV);
                        Toast.makeText(PostActivity.this, "Post SuccessFully", Toast.LENGTH_SHORT).show();

                        mEditName.setText("");
                        mEditPost.setText("");
                        mImageView.setImageResource(R.drawable.addphoto);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }

        });




        mBtnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///start recordList activity
                startActivity(new Intent(PostActivity.this,RecordListActivity.class));
            }
        });



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

        if (requestCode==REQUEST_CODE_GALLERY) {

            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
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

        if (requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK)
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
                mImageView.setImageURI(resultUri);
            }
            else if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }




}