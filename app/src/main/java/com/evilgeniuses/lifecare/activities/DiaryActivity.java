package com.evilgeniuses.lifecare.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.models.Diary;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String diaryID;

    ImageView imageViewBack;

    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;

    SeekBar seekBar;

    Button buttonСreateDiary;

    TextView textViewSeekBarProgres;

    EditText editTextComment;

    //Add Image
    private static final int IMAGE_REQUEST = 1;
    private Uri filePath;

    int stringImage = 0;
    String image = "STANDARD";
    String image1 = "STANDARD";
    String image2 = "STANDARD";
    String image3 = "STANDARD";
    String image4 = "STANDARD";
    String image5 = "STANDARD";



    private StorageTask uploadTask;
    Boolean imagePick = false;

    //Firebase
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageReference;
    String authenticationID;
    private String text = "Запись успешна создана";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        intent = getIntent();
        diaryID = intent.getStringExtra("diaryID");

        buttonСreateDiary = findViewById(R.id.buttonСreateDiary);
        if(diaryID != null){
            readData();
            text = "Запись успешна изменена";
            buttonСreateDiary.setText("Сохранить изменения");
        }

        textViewSeekBarProgres = findViewById(R.id.textViewSeekBarProgres);
        seekBar = findViewById(R.id.seekBar);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);


        buttonСreateDiary.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);


        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(this);

        editTextComment = findViewById(R.id.editTextComment);


        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekBarProgres.setText(seekBar.getProgress() + " из 10");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void readData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Diary/" + diaryID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Diary value = dataSnapshot.getValue(Diary.class);

                if (value.getComment() != null) {
                    editTextComment.setText((value.getComment()));
                }

                seekBar.setProgress(value.getPain());
                textViewSeekBarProgres.setText(seekBar.getProgress() + " из 10");
                if (value.getImgage1() != null) {
                    if (!value.getImgage1().equals("STANDARD"))
                    Glide.with(getApplicationContext()).load(value.getImgage1()).override(256, 256).into(imageView1);
                }

                if (value.getImgage2() != null) {
                    if (!value.getImgage2().equals("STANDARD"))
                    Glide.with(getApplicationContext()).load(value.getImgage2()).override(256, 256).into(imageView2);
                }

                if (value.getImgage3() != null) {
                    if (!value.getImgage3().equals("STANDARD"))
                    Glide.with(getApplicationContext()).load(value.getImgage3()).override(256, 256).into(imageView3);
                }

                if (value.getImgage4() != null) {
                    if (!value.getImgage4().equals("STANDARD"))
                    Glide.with(getApplicationContext()).load(value.getImgage4()).override(256, 256).into(imageView4);
                }

                if (value.getImgage5() != null) {
                    if (!value.getImgage5().equals("STANDARD"))
                    Glide.with(getApplicationContext()).load(value.getImgage5()).override(256, 256).into(imageView5);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewBack:
                finish();
                break;
            case R.id.imageView1:
                imageView = imageView1;
                stringImage = 1;
                SelectImage();
                break;
            case R.id.imageView2:
                imageView = imageView2;
                stringImage = 2;
                SelectImage();
                break;
            case R.id.imageView3:
                imageView = imageView3;
                stringImage = 3;
                SelectImage();
                break;
            case R.id.imageView4:
                imageView = imageView4;
                stringImage = 4;
                SelectImage();
                break;
            case R.id.imageView5:
                imageView = imageView5;
                stringImage = 5;
                SelectImage();
                break;
            case R.id.buttonСreateDiary:
                writeNewUser();
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

    private void SelectImage() {
        imagePick = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private void writeNewUser() {
        Diary diary = new Diary();
        DateTime today = new DateTime();

        diary.setImgage1(image1);
        diary.setImgage2(image2);
        diary.setImgage3(image3);
        diary.setImgage4(image4);
        diary.setImgage5(image5);


        int days = today.getYear() * 365 + today.getMonthOfYear() * 30 + today.getDayOfMonth() + today.getHourOfDay() + today.getMinuteOfDay();
        diary.setDays(days);

        diary.setComment(editTextComment.getText().toString());


        diary.setDate(today.toString());

        diary.setPain(seekBar.getProgress());

        String uuid = UUID.randomUUID().toString();



        if(diaryID != null){
            DatabaseReference databaseReferenceStatus = FirebaseDatabase.getInstance().getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Diary").child(diaryID);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("comment", editTextComment.getText().toString());
            hashMap.put("pain", seekBar.getProgress());


            if(!image1.equals("STANDARD")){
                hashMap.put("imgage1", image1);
            }
            if(!image2.equals("STANDARD")){
                hashMap.put("imgage2", image2);
            }
            if(!image3.equals("STANDARD")){
                hashMap.put("imgage3", image3);
            }
            if(!image4.equals("STANDARD")){
                hashMap.put("imgage4", image4);
            }
            if(!image5.equals("STANDARD")){
                hashMap.put("imgage5", image5);
            }


            databaseReferenceStatus.updateChildren(hashMap);
        }else {
            diary.setId(uuid);
            myRef.child("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Diary/" + uuid).setValue(diary);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                Glide.with(this).load(bitmap).override(512, 512).into(imageView);

                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Загрузка");
        pd.show();

        if (filePath != null) {
            final StorageReference fileReference = storageReference.child(UUID.randomUUID().toString() + "." + getFileExtension(filePath));

            uploadTask = fileReference.putFile(filePath);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        switch (stringImage){
                            case 1:
                                image1 = mUri;
                                break;
                            case 2:
                                image2 = mUri;
                                break;
                            case 3:
                                image3 = mUri;
                                break;
                            case 4:
                                image4 = mUri;
                                break;
                            case 5:
                                image5 = mUri;
                                break;


                            default:
                                image = mUri;
                                break;
                        }

                        pd.dismiss();
                    } else {
                        Toast.makeText(getApplication(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getApplication(), "No image selected", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }
}
