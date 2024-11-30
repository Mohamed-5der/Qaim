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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Classes.CustomPrevAttributesAdapter;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Models.AddNotesPreviwer.PreviewerAddNotesResponse;
import com.qaim.qaim.Models.MyListPrevDetails.MyListPrevDetailsResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewerListDetailsFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";

    private int id;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView tittle , cost , description , realStateType , realStateArea ,neighborhood , city ,additionalDetalis  ;
    String fileURL , imageURL ;
    EditText notes ;
    RelativeLayout openFile ;
    Button addNotes  ;
    int info_id;
    CustomPrevAttributesAdapter adapter ;
    RecyclerView recyclerview ;
    ArrayList<SlideModel> arrayList ;
    ImageSlider imageSlider ;
    TextView txtFileName ;

    MapView mapView ;
    LatLng latLng ;
    ActivityResultLauncher<Intent> resultLauncher;
    MultipartBody.Part body = null;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String PDF_DIRECTORY = "/demonuts_upload_gallery";
    public PreviewerListDetailsFragment() {
        // Required empty public constructor
    }


    public static PreviewerListDetailsFragment newInstance(int id) {
        PreviewerListDetailsFragment fragment = new PreviewerListDetailsFragment();
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
        View v = inflater.inflate(R.layout.fragment_previewer_list_details, container, false);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewerBusinessInProgressFragment mainFragment = new PreviewerBusinessInProgressFragment ();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , mainFragment).commit();
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        tittle = v.findViewById(R.id.tittle);
//        cost = v.findViewById(R.id.cost);
        description = v.findViewById(R.id.description);
//        realStateType = v.findViewById(R.id.realStateType);
//        realStateArea = v.findViewById(R.id.realStateArea);
//        neighborhood = v.findViewById(R.id.neighborhood);
        notes = v.findViewById(R.id.addnotestxt);
        addNotes = v.findViewById(R.id.addNotesBtn);
//        city = v.findViewById(R.id.city);
//        additionalDetalis = v.findViewById(R.id.additionalDetalis);
        arrayList = new ArrayList<>();
        imageSlider = v.findViewById(R.id.image_slider) ;
        openFile = v.findViewById(R.id.openFile);
        recyclerview = v.findViewById(R.id.recyclerview);
        txtFileName = v.findViewById(R.id.txtFileName);
        mapView =  v.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
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


        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng , 12));
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PreviewerActivity.dialog.show();
        Call<MyListPrevDetailsResponse> call = jsonApi.myListPreviewerDetails("Bearer " + PreviewerActivity.token , new OrderListItemParams(id));
        call.enqueue(new Callback<MyListPrevDetailsResponse>() {
            @Override
            public void onResponse(Call<MyListPrevDetailsResponse> call, Response<MyListPrevDetailsResponse> response) {
                PreviewerActivity.dialog.dismiss();
                MyListPrevDetailsResponse myListPrevDetailsResponse = response.body();
                if (myListPrevDetailsResponse.getCode()== 200){
                    PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
                    if (myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate() != null) {
                        myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getAttributes();
                        if (myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getAttributes() != null && !myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getAttributes().isEmpty() ){
                            adapter = new CustomPrevAttributesAdapter(myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getAttributes());
                            RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                            recyclerview.setLayoutManager(lm);
                            recyclerview.setHasFixedSize(true);
                            recyclerview.setAdapter(adapter);
                        }
                        tittle.setText(String.valueOf(myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getTitle()));
                        description.setText(String.valueOf(myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getDescription()));
                        latLng = new LatLng(Double.parseDouble(myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getLatitude()) , Double.parseDouble(myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getLongitude()));
                        if (!myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getFiles().isEmpty() && myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getFiles() !=null){
                            for (int i = 0 ; i< myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getFiles().size() ; i++){
                                String f= myListPrevDetailsResponse.getData().getRow().getInfo().getRealEstate().getFiles().get(i).getFile();
                                arrayList.add(new SlideModel(f , ScaleTypes.FIT));
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
                    }else {
                        tittle.setText("شركة");
                        description.setText("قيمها");
                        latLng = new LatLng(0,0);
                    }
                    fileURL =  myListPrevDetailsResponse.getData().getRow().getInfo().getDoc();
                    info_id =  myListPrevDetailsResponse.getData().getRow().getInfo().getId();
                    mapView.getMapAsync(PreviewerListDetailsFragment.this);
                }
                else {
                    PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext(), myListPrevDetailsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MyListPrevDetailsResponse> call, Throwable t) {
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });



        openFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission
                                .READ_EXTERNAL_STORAGE)
                        != PackageManager
                        .PERMISSION_GRANTED) {
                    // When permission is not granted
                    // Result permission
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[] {
                                    Manifest.permission
                                            .READ_EXTERNAL_STORAGE },
                            1);
                }
                else {
                    // When permission is granted
                    // Create method
                    selectPDF();
                }
            }
        });

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendRateOfferBtnPressed(info_id
                        ,String.valueOf(notes.getText())
                );
            }
        });


    }



    public void sendRateOfferBtnPressed(int id , String notes){
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("info_id", RequestBody.create(MultipartBody.FORM , "" +id ));
        map.put("pre_notes", RequestBody.create(MultipartBody.FORM , "" +notes ));
        PreviewerActivity.dialog.show();
        Call<PreviewerAddNotesResponse> call = jsonApi.addNotes("Bearer " + PreviewerActivity.token ,map , body);
        call.enqueue(new Callback<PreviewerAddNotesResponse>() {
            @Override
            public void onResponse(Call<PreviewerAddNotesResponse> call, Response<PreviewerAddNotesResponse> response) {
                PreviewerActivity.dialog.dismiss();
                PreviewerAddNotesResponse sendOfferResponse = response.body() ;
                if (sendOfferResponse.getCode() == 200) {
                    PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext(), sendOfferResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    getActivity().recreate();
                }else {
                    PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                    Toast.makeText(getContext(), sendOfferResponse.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<PreviewerAddNotesResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }






    public void uploadFile(Uri uri) throws IOException {
        if (uri != null) {
            File file =getFile(requireActivity(),uri);
            //Create a file object using file path
            String fName = file.getName();
            txtFileName.setText(fName);
            body = MultipartBody.Part.createFormData("pre_file", fName, RequestBody.create(MediaType.parse("*/*"), file));
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
        if (requestCode == 1 && grantResults.length > 0
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
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
}