package org.tll.falciniz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by abdullahtellioglu on 21/04/17.
 */
public class MainActivity extends Activity {
    private TextView genderTextView,relationTextView;
    private EditText nameEditText,ageEditText;
    private ImageView genderImageView,relationImageView;
    private ImageView cup1,cup2,cup3;
    private Button sendMagicButton;
    private Actions.User user;
    private final Bitmap[] images = new Bitmap[3];
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("TOKEN", FirebaseInstanceId.getInstance().getToken());
        genderTextView = (TextView)findViewById(R.id.txtViewGender);
        relationTextView =(TextView)findViewById(R.id.txtViewRelation);
        nameEditText = (EditText)findViewById(R.id.editTextName);
        ageEditText = (EditText)findViewById(R.id.editTextAge);
        genderImageView = (ImageView)findViewById(R.id.genderImageView);
        relationImageView = (ImageView)findViewById(R.id.relationImageView);
        sendMagicButton = (Button)findViewById(R.id.btnSendMagic);
        cup1 = (ImageView)findViewById(R.id.coffeImageView1);
        cup2 = (ImageView)findViewById(R.id.coffeImageView2);
        cup3 = (ImageView)findViewById(R.id.coffeImageView3);
        cup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(1);
            }
        });
        cup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(2);
            }
        });
        cup3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(3);
            }
        });
        genderImageView.setOnClickListener(changeGenderViewClickListener());
        genderTextView.setOnClickListener(changeGenderViewClickListener());
        relationTextView.setOnClickListener(changeRelationViewClickListener());
        relationImageView.setOnClickListener(changeRelationViewClickListener());
        sendMagicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actions.getInstance().register(getApplicationContext(), user);
                try{
                    Actions.getInstance().sendMagic(images,user,getApplicationContext());
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
        setUserInfo(Actions.getInstance().getUser(getApplicationContext()));
    }
    private View.OnClickListener changeRelationViewClickListener(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                user.setName(nameEditText.getText().toString());
                try{
                    user.setAge(Integer.parseInt(ageEditText.getText().toString()));
                }catch (Exception ex){
                    user.setAge(0);
                }
                user.setRelation(!user.isRelation());
                setUserInfo(user);
            }
        };
    }
    private View.OnClickListener changeGenderViewClickListener(){
       return  new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                user.setGender(!user.isGender());
                user.setName(nameEditText.getText().toString());
                try{
                    user.setAge(Integer.parseInt(ageEditText.getText().toString()));
                }catch (Exception ex){
                    user.setAge(0);
                }

                setUserInfo(user);
            }
        };
    }
    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
    }

    private void setUserInfo(Actions.User user){
        this.user = user;
        nameEditText.setText(user.getName());
        if(user.getAge()>0){
            ageEditText.setText(String.valueOf(user.getAge()));
        }
        if(!user.isGender()){
            //kadinsa
            genderImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.female_icon));
            genderTextView.setText("Kadın");
        }else{
            //erkekse ?
            genderTextView.setText("Erkek");
        }
        if(user.isRelation()){
            relationImageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.hearth_fill_square));
            relationTextView.setText("İlişkisi var");
        }else{
            relationTextView.setText("İlişkisi yok");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private int lastClickedImageView;
    private void dispatchTakePictureIntent(int id) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.lastClickedImageView = id;
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            images[lastClickedImageView-1] = imageBitmap;
            getImageView(lastClickedImageView).setImageBitmap(imageBitmap);
        }
    }
    private ImageView getImageView(int id){
        switch (id){
            case 1:
                return cup1;
            case 2:
                return cup2;
            case 3:
                return cup3;
        }
        return null;
    }
}
