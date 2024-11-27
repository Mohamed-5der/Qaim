package com.qaim.qaim.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
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
import com.qaim.qaim.Activities.EmployeeActivity;
import com.qaim.qaim.Classes.EmpAttributesAdapter;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Models.EmpolyeeAddNotes.EmployeeAddNotesResponse;
import com.qaim.qaim.Models.MyListEmployeeDetails.MyListEmployeeDetailsResponse;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeListDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int id;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    TextView tittle  ,description ;
    EditText address ;
    String fileURL , imageURL ;
    RelativeLayout openFile ;
    Button addNotes  ;
    int info_id;


    RecyclerView recyclerView ;
    EmpAttributesAdapter adapter ;
    ImageSlider imageSlider ;
    ArrayList<SlideModel> arrayList ;

    ActivityResultLauncher<Intent> resultLauncher;
    private String pdfPath;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String PDF_DIRECTORY = "/demonuts_upload_gallery";

    public EmployeeListDetailsFragment() {
        // Required empty public constructor
    }

    public static EmployeeListDetailsFragment newInstance(int id) {
        EmployeeListDetailsFragment fragment = new EmployeeListDetailsFragment();
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
        View v = inflater.inflate(R.layout.fragment_employee_list_details, container, false);
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeMainFragment fragment = new EmployeeMainFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout , fragment).commit();
            }
        });
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(
                            ActivityResult result) {
                        Intent data = result.getData();
                        // check condition
                        if (data != null) {
                            // Akl code
                            Uri uri = data.getData();
                            pdfPath = getFilePathFromURI(uri);

                            // When data is not equal to empty
                            // Get PDf uri
//                            Uri sUri = data.getData();
//                            String sPath = sUri.getPath();
//                            Toast.makeText(getActivity() , sUri + "" , Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity() , sPath + "" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        tittle = v.findViewById(R.id.tittle);
        description = v.findViewById(R.id.description);
//        realStateType = v.findViewById(R.id.realStateType);
//        realStateArea = v.findViewById(R.id.realStateArea);
//        neighborhood = v.findViewById(R.id.neighborhood);
        address = v.findViewById(R.id.addnotestxt);
//        city = v.findViewById(R.id.city);
        imageSlider = v.findViewById(R.id.image_slider);
        arrayList = new ArrayList<>();
        openFile = v.findViewById(R.id.openFile);
        addNotes = v.findViewById(R.id.addNotesBtn);
        recyclerView = v.findViewById(R.id.rec);
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EmployeeActivity.dialog.show();
        Call<MyListEmployeeDetailsResponse> call = jsonApi.myListEmployeeDetails("Bearer " + EmployeeActivity.token , new OrderListItemParams(id));
        call.enqueue(new Callback<MyListEmployeeDetailsResponse>() {
            @Override
            public void onResponse(Call<MyListEmployeeDetailsResponse> call, Response<MyListEmployeeDetailsResponse> response) {
                EmployeeActivity.dialog.dismiss();
                MyListEmployeeDetailsResponse myListPrevDetailsResponse = response.body();
                if (myListPrevDetailsResponse.getCode()== 200){

                    if (!myListPrevDetailsResponse.getData().getRow().getAttributes().isEmpty() && myListPrevDetailsResponse.getData().getRow().getAttributes() != null){

                        adapter = new EmpAttributesAdapter( myListPrevDetailsResponse.getData().getRow().getAttributes());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(lm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }
                    tittle.setText(String.valueOf(myListPrevDetailsResponse.getData().getRow().getRealEstate().getTitle()));
                    description.setText(String.valueOf(myListPrevDetailsResponse.getData().getRow().getRealEstate().getDescription()));
//                    realStateArea.setText(String.valueOf(myListPrevDetailsResponse.getData().getRow().getRealEstate().getDistance()));
//                    neighborhood.setText(String.valueOf(myListPrevDetailsResponse.getData().getRow().getRealEstate().getRegion().getName()));
//                    city.setText(String.valueOf(myListPrevDetailsResponse.getData().getRow().getRealEstate().getCity().getName()));
                    fileURL =  myListPrevDetailsResponse.getData().getRow().getDoc();
                    if (!myListPrevDetailsResponse.getData().getRow().getRealEstate().getFiles().isEmpty() && myListPrevDetailsResponse.getData().getRow().getRealEstate().getFiles() != null){

                        for (int i = 0 ; i<myListPrevDetailsResponse.getData().getRow().getRealEstate().getFiles().size() ; i++){
                            String  file = myListPrevDetailsResponse.getData().getRow().getRealEstate().getFiles().get(0).getFile();
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
                }
            }
            @Override
            public void onFailure(Call<MyListEmployeeDetailsResponse> call, Throwable t) {
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
                addNotesAPICall();
            }
        });
    }

    public void addNotesAPICall(){
        EmployeeActivity.dialog.show();
        HashMap<String, RequestBody> map = new HashMap<>();
        MultipartBody.Part body = null;
        if (pdfPath != null){
            body = MultipartBody.Part.createFormData("emp_file", "document", preparePdfFileAndGetRequestBody());
        }

        map.put("order_id", RequestBody.create(MultipartBody.FORM , "" +id ));
        map.put("emp_notes", RequestBody.create(MultipartBody.FORM , "" +address.getText() ));


        EmployeeActivity.dialog.show();
        Call<EmployeeAddNotesResponse> call = jsonApi.employeeAddNotesAndReports("Bearer " + EmployeeActivity.token ,map , body);
        call.enqueue(new Callback<EmployeeAddNotesResponse>() {
            @Override
            public void onResponse(Call<EmployeeAddNotesResponse> call, Response<EmployeeAddNotesResponse> response) {
                EmployeeActivity.dialog.dismiss();
                EmployeeAddNotesResponse sendOfferResponse = response.body() ;
                if (sendOfferResponse.getCode() == 200) {
                    Toast.makeText(getContext(), sendOfferResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    getActivity().recreate();
                }else {
                    Toast.makeText(getContext(), sendOfferResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EmployeeAddNotesResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public String getFilePathFromURI(Uri contentUri) {
        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + PDF_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
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

    public void copy(Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = getActivity().getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    private RequestBody preparePdfFileAndGetRequestBody() {
        String pdfname = String.valueOf(Calendar.getInstance().getTimeInMillis());
        //Create a file object using file path
        File file = new File(pdfPath);
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("/"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("doc", file.getName(), requestBody);
        RequestBody mRequestBodyForPDF = RequestBody.create(MediaType.parse("application/pdf"), pdfname);

//        .*\.pdf$
//        application/pdf
//        text/plain

        return mRequestBodyForPDF;
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