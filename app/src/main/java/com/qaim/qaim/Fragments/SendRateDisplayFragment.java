package com.qaim.qaim.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.CompanyRealstateIdParams;
import com.qaim.qaim.Models.MyRealstateCompanyList.FilesItem;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.SendOfferResponse.SendOfferResponse;
import com.qaim.qaim.Models.ShowCompanyRealstate.ShowCompanyRealstateResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendRateDisplayFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";

    ActivityResultLauncher<Intent> resultLauncher;
    private int id;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView tittle , description , realStateType , realStateArea ,neighborhood , city , address ;
    RelativeLayout openFile ;
    EditText addCost , addNotes ;
    Button sendRateOffer ;
    ImageSlider imageSlider ;
    ArrayList<SlideModel> arrayList ;
    String fileURL ;
    MultipartBody.Part body = null;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String PDF_DIRECTORY = "/demonuts_upload_gallery";

    TextView lblFileName;



    public SendRateDisplayFragment() {
        // Required empty public constructor
    }


    public static SendRateDisplayFragment newInstance(int id) {
        SendRateDisplayFragment fragment = new SendRateDisplayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_send_rate_display, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyListOfRealstateFragment fragment = new  CompanyListOfRealstateFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        replace(R.id.frameLayout , fragment).commit();
            }
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        tittle = v.findViewById(R.id.nameOforder);
        description =  v.findViewById(R.id.notes);
        realStateType =  v.findViewById(R.id.realsStateType);
        realStateArea =  v.findViewById(R.id.realStateArea);
        city =  v.findViewById(R.id.city);
        neighborhood =  v.findViewById(R.id.neiborhood);
//        address  = v.findViewById(R.id.additionalDetalis);
        addNotes = v.findViewById(R.id.addnotestxt);
        openFile = v.findViewById(R.id.openFile);
        sendRateOffer = v.findViewById(R.id.sendRateDisplay);
        imageSlider = v.findViewById(R.id.image_slider);
        arrayList = new ArrayList<>();
        addCost = v.findViewById(R.id.addCost);
        lblFileName = v.findViewById(R.id.lblFileName);
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(
                            ActivityResult result) {
                        Intent data = result.getData();
                        // check condition
                        if (data != null) {

                            try {
                                uploadFile( data.getData());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    }
                });


        //cheak permission
//        if (ContextCompat.checkSelfPermission(getContext() , Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//        ){ActivityCompat.requestPermissions(getActivity() ,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.READ_EXTERNAL_STORAGE} , 1
//                    );}
//        else {
//
//        }


        return v ;
    }


    public void uploadFile(Uri uri) throws IOException {
        if (uri != null) {
            File file =getFile(requireActivity(),uri);
            //Create a file object using file path
            String fName = file.getName();
            lblFileName.setText(fName);
            body = MultipartBody.Part.createFormData("doc", fName, RequestBody.create(MediaType.parse("*/*"), file));
        }
    }


    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static File getFile(Context context, Uri uri) throws IOException {
        File destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }

    public String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }


    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

    private void selectPDF()
    {
        // Initialize intent
        Intent intent
                = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        resultLauncher.launch(intent);
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);

        // check condition
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectPDF();
        }
        else {
            // When permission is denied
            // Display toast
            Toast
                    .makeText(getActivity(),
                            "Permission Denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CompanyActivity.dialog.show();
        Call<ShowCompanyRealstateResponse> call = jsonApi.showCompanyRealstateList("Bearer " + CompanyActivity.token , new CompanyRealstateIdParams(id));
        call.enqueue(new Callback<ShowCompanyRealstateResponse>() {
            @Override
            public void onResponse(Call<ShowCompanyRealstateResponse> call, Response<ShowCompanyRealstateResponse> response) {
                CompanyActivity.dialog.dismiss();
                ShowCompanyRealstateResponse companyRealstateResponse = response.body();
                if (companyRealstateResponse.getCode() == 200) {
                    tittle.setText(companyRealstateResponse.getData().getRow().getTitle());
//                    cost.setText(companyRealstateResponse.getData().getRow().getCost());
                    description.setText(companyRealstateResponse.getData().getRow().getDescription());
                    realStateArea.setText(companyRealstateResponse.getData().getRow().getDistance());
                    realStateType.setText(companyRealstateResponse.getData().getRow().getType().getName());
                    city.setText(companyRealstateResponse.getData().getRow().getCity().getName());
                    neighborhood.setText(companyRealstateResponse.getData().getRow().getRegion().getName());
//                    address.setText(companyRealstateResponse.getData().getRow().getAddress());
//                    companyRealstateResponse.getData().getRow()
                    fileURL  = String.valueOf(companyRealstateResponse.getData().getRow().getFiles());
//                    companyRealstateResponse.getData().getRow().;

                    if (!companyRealstateResponse.getData().getRow().getFiles().isEmpty() && companyRealstateResponse.getData().getRow().getFiles() != null){
                        List<FilesItem> files = companyRealstateResponse.getData().getRow().getFiles() ;
                        for (int i = 0 ; i<files.size() ; i++){
                            String  file = files.get(i).getFile();
                            arrayList.add(new SlideModel(file , ScaleTypes.FIT));
                        }
                        imageSlider.setImageList(arrayList);
                        imageSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                String url = arrayList.get(i).getImageUrl();
                                Dialog dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.image_bottom_sheet_dialog);
                                ImageView dialog_image = dialog.findViewById(R.id.dialog_image);
                                Picasso.get().load(url).fit().into(dialog_image);
                                dialog.show();
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.getWindow().getAttributes().windowAnimations = R.style.ImageDialogAnimation ;
                                dialog.getWindow().setGravity(Gravity.BOTTOM);

                            }
                        });

                    }


                    openFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (Build.VERSION.SDK_INT < 23){
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

                                    return;
                                }
                            }else {
                                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
                                    ActivityCompat.requestPermissions(getActivity() ,new  String [] {Manifest.permission.READ_EXTERNAL_STORAGE} ,1);

                                }else {
                                    selectPDF();
                                }
                            }

//                            if (ContextCompat.checkSelfPermission(
//                                    getContext(),
//                                    Manifest.permission
//                                            .READ_EXTERNAL_STORAGE)
//                                    != PackageManager
//                                    .PERMISSION_GRANTED) {
//                                // When permission is not granted
//                                // Result permission
//                                ActivityCompat.requestPermissions(
//                                        getActivity(),
//                                        new String[] {
//                                                Manifest.permission
//                                                        .READ_EXTERNAL_STORAGE },
//                                        1);
//                            }
//                            else {
//                                // When permission is granted
//                                // Create method
//                                selectPDF();
//                            }
                        }
                    });
                    sendRateOffer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendRateOfferBtnPressed(companyRealstateResponse.getData().getRow().getId()
                                    , String.valueOf(addCost.getText())
                                    ,String.valueOf(addNotes.getText())
                                    );
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ShowCompanyRealstateResponse> call, Throwable t) {

            }
        });




    }
    public void sendRateOfferBtnPressed(int id , String cost , String notes){
        HashMap<String, RequestBody> map = new HashMap<>();

            map.put("real_estate_id", RequestBody.create(MultipartBody.FORM , "" +id ));
            map.put("cost", RequestBody.create(MultipartBody.FORM , "" +cost ));
            map.put("notes", RequestBody.create(MultipartBody.FORM , "" +notes ));


            CompanyActivity.dialog.show();
            Call<SendOfferResponse> call = jsonApi.sendOffer("Bearer " + CompanyActivity.token ,map , body);
            call.enqueue(new Callback<SendOfferResponse>() {
                @Override
                public void onResponse(Call<SendOfferResponse> call, Response<SendOfferResponse> response) {
                    CompanyActivity.dialog.dismiss();
                    SendOfferResponse sendOfferResponse = response.body() ;
                    if (sendOfferResponse.getCode() == 200) {
                        CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                        Toast.makeText(getContext(), sendOfferResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        getActivity().recreate();

                    }else {
//                        Toast.makeText(getContext(), sendOfferResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                    }
                }

                @Override
                public void onFailure(Call<SendOfferResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



    }




}