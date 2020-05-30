package com.example.healthapp;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.healthapp.chatadapter.CustomIncomingImageMessageViewHolder;
import com.example.healthapp.chatadapter.CustomIncomingTextMessageViewHolder;
import com.example.healthapp.chatadapter.CustomOutcomingImageMessageViewHolder;
import com.example.healthapp.chatadapter.CustomOutcomingTextMessageViewHolder;
import com.example.healthapp.chatmodel.GetTimeCovertAgo;
import com.example.healthapp.chatmodel.ImageFilePath;
import com.example.healthapp.chatmodel.Message;
import com.example.healthapp.chatmodel.MessageList;
import com.example.healthapp.chatmodel.User;
import com.example.healthapp.model.Doctor;
import com.example.healthapp.model.Patient;
import com.example.healthapp.myapplication.Myapplication;
import com.example.healthapp.util.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;

import static com.example.healthapp.chatmodel.ConstantFunctions.requestOptionsRadioMatch;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    final private int REQUEST_CODE_ASK_PERMISSIONS_AGENT = 100;
    private static final int CAMERA_TAKE_PHOTO = 10;
    private static final int GALLERY_TAKE_PHOTO = 12;
    private ImageView img_back, img_video, img_call, img_search, img_more, img_gallery, img_camera, img_send;
    private EditText txtMsg;
    private MessagesList messagesList;
    private MessagesListAdapter mMessagesListAdapter;
    private ImageLoader imageLoader;
    private DatabaseReference mDatabaseReference = null;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference storageRef;
    private String receiver_id = "";
    private String app_user_id = "";
    private ValueEventListener eventListener;
    private ChildEventListener mChildEventListener;
    private Context mContext;
    private String selectedImagePath = "";
    private String imageUploadFromserve = "";
    private ProgressDialog mProgressDialog;
    private int chatSystem = 0;
    private TextView txtName;
    private Doctor currentDoctor;
    private Patient mPatient;
    private String chatRoom = "";
    private String userName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mContext = this;
        app_user_id = Myapplication.myUserId;
        chatSystem = getIntent().getIntExtra("chatSystem", 0);

        if (chatSystem == 0) {
            currentDoctor = (Doctor) getIntent().getSerializableExtra("doctor");
            receiver_id = currentDoctor.getDoctorKey();
            if(receiver_id==null ||receiver_id.equalsIgnoreCase("")){
                Toast.makeText(mContext,"Don't get correct doctor informaiton",Toast.LENGTH_LONG).show();
                finish();
            }
            chatRoom = receiver_id + "_" + app_user_id;
            userName = "Doctor: " + currentDoctor.getFirstName() + " " + currentDoctor.getLastName();
        } else if (chatSystem == 1) {
            mPatient = (Patient) getIntent().getSerializableExtra("patient");
            receiver_id = mPatient.getPatientKey();
            if(receiver_id==null ||receiver_id.equalsIgnoreCase("")){
                Toast.makeText(mContext,"Don't get correct patient informaiton",Toast.LENGTH_LONG).show();
                finish();
            }
            chatRoom = app_user_id + "_" + receiver_id;
            userName = "Patient: " + mPatient.getFirstName() + " " + mPatient.getLastName();
        }
        initui();
    }

    public void initui() {
        txtName = findViewById(R.id.txtName);
        img_back = findViewById(R.id.img_back);
        img_video = findViewById(R.id.img_video);
        img_call = findViewById(R.id.img_call);
        img_search = findViewById(R.id.img_search);
        img_more = findViewById(R.id.img_more);
        img_gallery = findViewById(R.id.img_gallery);
        img_camera = findViewById(R.id.img_camera);
        img_send = findViewById(R.id.img_send);
        txtMsg = findViewById(R.id.txtMsg);
        txtName.setText(userName);


        Utils.changeImageViewColor(this, img_back, R.color.white);
        Utils.changeImageViewColor(this, img_video, R.color.white);
        Utils.changeImageViewColor(this, img_call, R.color.white);
        Utils.changeImageViewColor(this, img_search, R.color.white);
        Utils.changeImageViewColor(this, img_more, R.color.white);

        Utils.changeImageViewColor(this, img_gallery, R.color.colorPrimaryDark);
        Utils.changeImageViewColor(this, img_camera, R.color.colorPrimaryDark);
        Utils.changeImageViewColor(this, img_send, R.color.colorPrimaryDark);

        img_back.setOnClickListener(this);
        img_video.setOnClickListener(this);
        img_call.setOnClickListener(this);
        img_search.setOnClickListener(this);
        img_more.setOnClickListener(this);
        img_gallery.setOnClickListener(this);
        img_camera.setOnClickListener(this);
        img_send.setOnClickListener(this);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference("chatRoom").child(chatRoom);
        messagesList = (MessagesList) this.findViewById(R.id.messagesList);
        imageLoader = new ImageLoader() {
            @Override
            public void loadImage(final ImageView imageView, @Nullable String url, @Nullable Object payload) {
                Glide.with(ChatActivity.this)
                        .asBitmap()
                        .load(url)
                        .apply(requestOptionsRadioMatch)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                imageView.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });

            }
        };
        MessageHolders holdersConfig = new MessageHolders()
                .setIncomingTextConfig(
                        CustomIncomingTextMessageViewHolder.class,
                        R.layout.item_custom_incoming_text_message)
                .setOutcomingTextConfig(
                        CustomOutcomingTextMessageViewHolder.class,
                        R.layout.item_custom_outcoming_text_message)
                .setIncomingImageConfig(
                        CustomIncomingImageMessageViewHolder.class,
                        R.layout.item_custom_incoming_image_message)
                .setOutcomingImageConfig(
                        CustomOutcomingImageMessageViewHolder.class,
                        R.layout.item_custom_outcoming_image_message);
        mMessagesListAdapter = new MessagesListAdapter<>(app_user_id, holdersConfig, imageLoader);
        messagesList.setAdapter(mMessagesListAdapter);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MessageList mMessageList = dataSnapshot.getValue(MessageList.class);
                long time = GetTimeCovertAgo.dateToMillisecond(mMessageList.getDuration());
                String idForList = mMessageList.getSender_id();
                if (mMessageList.getMessageType().equalsIgnoreCase("0")) {
                    mMessagesListAdapter.addToStart(addMessage(idForList, mMessageList.getMessage(), "" + time), true);
                } else if (mMessageList.getMessageType().equalsIgnoreCase("1")) {
                    mMessagesListAdapter.addToStart(getImageMessage(idForList, mMessageList.getImagePath(), "" + time), true);
                } else if (mMessageList.getMessageType().equalsIgnoreCase("2")) {
                    mMessagesListAdapter.addToStart(getImageMessage(idForList, "", "" + time), true);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
        mFirebaseStorage = FirebaseStorage.getInstance();
        storageRef = mFirebaseStorage.getReference();


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {


    }

    public void sendMessage() {
        String message = txtMsg.getText().toString().trim();
        if (!message.equalsIgnoreCase("")) {
            final MessageList mMessageList = new MessageList();
            mMessageList.setMessage(message);
            mMessageList.setSender_id(app_user_id);
            mMessageList.setReceiver_id(receiver_id);
            mMessageList.setMessageType("0");
            mMessageList.setImagePath("");
            mMessageList.setVideoPath("");
            mMessageList.setDuration(GetTimeCovertAgo.getCurrentTimeForsend());
            mDatabaseReference.push().setValue(mMessageList).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    txtMsg.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        Utils.clickEffect(v);
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_send:
                sendMessage();
                break;
            case R.id.img_camera:
                checkFileUploadPermissions();
                break;
            case R.id.img_gallery:
                checkFileUploadPermissions();
                break;
        }
    }

    public Message getImageMessage(String userId, String imagefile, String durarion) {
        User user = new User(userId, "Tobi", null, true);
        long totaldurarion = Long.parseLong(durarion);
        Message message = new Message(userId, user, null, new Date(totaldurarion));
        message.setImage(new Message.Image(imagefile));
        return message;
    }

    private Message addMessage(String userId, String text, String durarion) {
        User user = new User(userId, "Tobi", null, true);
        long totaldurarion = Long.parseLong(durarion);
        return new Message(userId, user, text, new Date(totaldurarion));
    }

    List<String> permissions = new ArrayList<String>();

    public void checkFileUploadPermissions() {
        permissions.clear();
        if (Build.VERSION.SDK_INT > 22) {
            String storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            String readstoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE;


            int hasstoragePermission = checkSelfPermission(storagePermission);
            int readhasstoragePermission = checkSelfPermission(readstoragePermission);


            if (hasstoragePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(storagePermission);
            }
            if (readhasstoragePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(readstoragePermission);
            }

            if (!permissions.isEmpty()) {
                String[] params = permissions.toArray(new String[permissions.size()]);
                requestPermissions(params, REQUEST_CODE_ASK_PERMISSIONS_AGENT);
            } else {
                showDialogForVideo();
            }
        } else
            showDialogForVideo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS_AGENT:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        showDialogForVideo();
                    }
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void showDialogForVideo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mView = inflater.inflate(R.layout.dialog_photoselction, null);
        builder.setView(mView);
        builder.setCancelable(true);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
        final LinearLayout Camera = (LinearLayout) mView.findViewById(R.id.layout_Camera);
        final LinearLayout layout_Gellery = (LinearLayout) mView.findViewById(R.id.layout_Gellery);
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                pickFromCamera();

            }
        });
        layout_Gellery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                pickFromGallery();

            }
        });
    }

    public void pickFromCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = timeStamp + "_";
        try {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            imageUploadFromserve = image.getAbsolutePath();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoURI = FileProvider.getUriForFile(mContext,
                    BuildConfig.APPLICATION_ID + ".provider", image);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(cameraIntent, CAMERA_TAKE_PHOTO);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pickFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_TAKE_PHOTO) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                selectedImagePath = ImageFilePath.getPath(mContext, selectedImageUri);
                File file = new File(selectedImagePath);
                try {
                    imageUPload(file);
                    File compressedImage = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.JPEG)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(file);
                    //imageUPload(compressedImage);
                } catch (Exception ex) {
                }


            } else if (requestCode == CAMERA_TAKE_PHOTO) {
                File file = new File(imageUploadFromserve);
                imageUPload(file);
                try {
                    File compressedImage = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.JPEG)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(file);
                    //imageUPload(compressedImage);
                } catch (Exception ex) {
                }


            }
        }
    }

    public void imageUPload(File mfile) {
        mProgressDialog = new ProgressDialog(ChatActivity.this);
        mProgressDialog.setMessage("loading");
        mProgressDialog.show();
        Uri file = Uri.fromFile(mfile);
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                mProgressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("UploadTask", "UploadTask");
                mProgressDialog.dismiss();
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while (!uri.isComplete()) ;
                Uri url = uri.getResult();
                sendImage(url.toString());


            }
        });
    }

    public void sendImage(String message) {
        if (!message.equalsIgnoreCase("")) {
            final MessageList mMessageList = new MessageList();
            mMessageList.setMessage("");
            mMessageList.setSender_id(app_user_id);
            mMessageList.setReceiver_id(receiver_id);
            mMessageList.setMessageType("1");
            mMessageList.setImagePath(message);
            mMessageList.setVideoPath("");
            mMessageList.setDuration(GetTimeCovertAgo.getCurrentTimeForsend());
            mDatabaseReference.push().setValue(mMessageList).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    txtMsg.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }


}
