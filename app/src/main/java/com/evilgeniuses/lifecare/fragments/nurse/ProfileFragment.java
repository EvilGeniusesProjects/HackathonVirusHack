package com.evilgeniuses.lifecare.fragments.nurse;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.AuthenticationActivity;
import com.evilgeniuses.lifecare.interfaces.SwitchFragment;
import com.evilgeniuses.lifecare.models.User;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    SwitchFragment switchFragment;

    ImageView imageViewProfileImage;

    TextView textViewSetProfileImage;

    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextName;
    EditText editTextLastname;
    EditText editTextPhone;
    EditText editTextSkills;
    EditText editTextVk;
    EditText editTextTelegram;
    EditText editTextInst;

    Button buttonLogout;


    DatabaseReference myRef;

    private static final int IMAGE_REQUEST = 1;
    private Uri filePath;
    private StorageTask uploadTask;
    StorageReference storageReference;
    FirebaseStorage storage;
    FirebaseUser user;
    Context mContext;
    private ValueEventListener valueEventListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nurse_profile, container,false);

        mContext = getContext();

        imageViewProfileImage = rootView.findViewById(R.id.imageViewProfileImage);

        textViewSetProfileImage = rootView.findViewById(R.id.textViewSetProfileImage);

        editTextUsername = rootView.findViewById(R.id.editTextUsername);
        editTextEmail = rootView.findViewById(R.id.editTextEmail);
        editTextName = rootView.findViewById(R.id.editTextName);
        editTextLastname = rootView.findViewById(R.id.editTextLastname);
        editTextPhone = rootView.findViewById(R.id.editTextPhone);
        editTextSkills = rootView.findViewById(R.id.editSkills);
        editTextVk = rootView.findViewById(R.id.editVk);
        editTextTelegram = rootView.findViewById(R.id.editTelegram);
        editTextInst = rootView.findViewById(R.id.editInst);

        buttonLogout = rootView.findViewById(R.id.buttonLogout);

        textViewSetProfileImage.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();



        myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);

                if(value.userProfileImageURL.equals("STANDARD")){
                    imageViewProfileImage.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(mContext).load(value.userProfileImageURL).override(512, 512).into(imageViewProfileImage);
                }

                editTextUsername.setText(value.userUsername);
                editTextEmail.setText(value.userEmail);
                editTextName.setText(value.userName);
                editTextLastname.setText(value.userLastname);
//                editTextPhone.setText(value.userPhoneNumber);
//                editTextSkills.setText(value.userAbilities);
//                editTextVk.setText(value.userVK);
//                editTextTelegram.setText(value.userTelegram);
//                editTextInst.setText(value.userInstagram);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.w(TAG, "Не удалось прочитать значение", error.toException());
            }
        };
        myRef.addValueEventListener(valueEventListener);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;

            case R.id.textViewSetProfileImage:
                SelectImage();
                break;
        }
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageViewProfileImage.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
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

                        user = FirebaseAuth.getInstance().getCurrentUser();
                        myRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("userProfileImageURL", "" + mUri);
                        myRef.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof SwitchFragment){
            switchFragment = (SwitchFragment) context;
        }
    }

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (valueEventListener != null) {
                    valueEventListener = null;
                }
                Map<String, Object> value= new HashMap<>();
                value.put("userPhoneNumber", s.toString());
                myRef.updateChildren(value);
                editTextPhone.setSelection(s.toString().length());
            }
        });
        editTextSkills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (valueEventListener != null) {
                    valueEventListener = null;
                }
                Map<String, Object> value= new HashMap<>();
                value.put("userAbilities", s.toString());
                myRef.updateChildren(value);
                editTextSkills.setSelection(s.toString().length());
            }
        });
        editTextVk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (valueEventListener != null) {
                    valueEventListener = null;
                }
                Map<String, Object> value= new HashMap<>();
                value.put("userVK", s.toString());
                myRef.updateChildren(value);
                editTextVk.setSelection(s.toString().length());
            }
        });
        editTextTelegram.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (valueEventListener != null) {
                    valueEventListener = null;
                }
                Map<String, Object> value= new HashMap<>();
                value.put("userTelegram", s.toString());
                myRef.updateChildren(value);
                editTextTelegram.setSelection(s.toString().length());
            }
        });
        editTextInst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (valueEventListener != null) {
                    valueEventListener = null;
                }
                Map<String, Object> value= new HashMap<>();
                value.put("userInstagram", s.toString());
                myRef.updateChildren(value);
                editTextInst.setSelection(s.toString().length());
            }
        });
    }
}