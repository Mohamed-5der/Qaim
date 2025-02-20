package com.qaim.qaim.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.ImageAdapter;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.CompanyMakeReport.CompanyMakeReportResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyWriterReportsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private int id;
//    Spinner realStateTypeV ;
//    CustomRealstateTypeAdapter customRealstateTypeAdapter;
    int typeId;
    Retrofit retrofit;
    JsonApi jsonApi;
//    CustomCityAdapter cityAdapter;
//    int cityId;int cityId2;
//    CustomRegionAdapter regionAdapter;
//    int regionId;int regionId2;

    MultipartBody.Part body = null;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String PDF_DIRECTORY = "/demonuts_upload_gallery";
    int frstFile ;
    RelativeLayout uploadFile ;
    TextView lblFileName;
    String fName ;
    MultipartBody.Part body_final_file = null;
    RelativeLayout uploadFinalFile ;
    TextView lblFinalFileName;
    String fFinalName ;

    Spinner  neighborhoodSpinnerV, cityV;

    Button sendRate ;
//    String widgt ,realstateLimits ,realstateGeneralLocation ,attributed , service , nearstStreat ,howFasrRealstateRating ,
//            howFarRealstateCenterCity , Views , thePartyDirected , rateUser ,ratePurpose ,realstateLocation ,
//            ownerShipType ,
//            rate ,valueBasis ,rateDate ,previweDate ,usedMethods ,methodsAndWays , valueHypothesis , applicableEvaluationCriteria ,
//            ndependentConflictOfInterest ,
//            referanceNumber , rateType ,rateDate1 ,rateVersion , rateUses ,infoSources , notes , doc , docName ;
//String notes ;
//EditText notesV ;
//    EditText widgtV   ,realstateLimitsV ,realstateGeneralLocationV ,attributedV , serviceV , nearstStreatV ,howFasrRealstateRatingV ,
//            howFarRealstateCenterCityV , ViewsV , thePartyDirectedV , rateUserV ,ratePurposeV  ,realstateLocationV , ownerShipTypeV ,
//            rateV ,valueBasisV  ,usedMethodsV ,methodsAndWaysV , valueHypothesisV , applicableEvaluationCriteriaV , ndependentConflictOfInterestV ,
//            referanceNumberV , rateTypeV  ,rateVersionV , rateUsesV ,infoSourcesV , notesV ;




    // first section
    EditText realeStateCode, general_description_realestate, description_location, instrument_number,
            realStateType;
    Button ratingDataBtn , dateBtn;
    RadioButton yes, no;
    CheckBox phone ,out ,elc ,water ;
    int phoneTx , outTx ,elcTx ,waterTx ;
    EditText realState_age ;
    String rateDate , his_date , Infrastructure = null;
    RadioButton ready , under_construction , space ;
    String ready_for_user;

    EditText number ,piece_number ,
            source ,entry_type ,distance ;


    List<Uri> fileUris = new ArrayList<>();
    ImageView imageView ;
    List<Bitmap> bitmaps = new ArrayList<>();
    RecyclerView imageRecycleView ;
    ImageAdapter imageAdapter ;

    Button addEstatePhoto ;


    EditText north ,northTall , south ,southTall , east , eastTall ,
            west ,westTall ,in_range , attributed , building_condition ,
            general_description , theNumberOfTurns,
            landRate , buildingRate , marketing_value,
            totalCost, realestate_comparing,
            conflict_interest, measurement, preview,
            direction, thePurposeOf, valueBasis,
            reportCurrency,measurement2 , own_assumptions,
            reliable_data ,company_opinion ,notesV ;
    String notes ;

    public CompanyWriterReportsFragment() {
        // Required empty public constructor
    }

    public static CompanyWriterReportsFragment newInstance(int param1) {
        CompanyWriterReportsFragment fragment = new CompanyWriterReportsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
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
//    public void callRealstateType(){
//        Call<GetTypeResponse> getTypeResponseCall = jsonApi.getTypes();
//        getTypeResponseCall.enqueue(new Callback<GetTypeResponse>() {
//            @Override
//            public void onResponse(Call<GetTypeResponse> call, Response<GetTypeResponse> response) {
//                GetTypeResponse getTypeResponse = response.body();
//                if (response.code() == 200) {
//                    customRealstateTypeAdapter = new CustomRealstateTypeAdapter(getTypeResponse.getData().getTypes());
//                    realStateTypeV.setAdapter(customRealstateTypeAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetTypeResponse> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_writer_reports, container, false);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealStateDetailsFragment fragment = RealStateDetailsFragment.newInstance(id);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , fragment).commit();
            }
        });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        inflateViews(v);

        uploadFile = v.findViewById(R.id.uploadFile);
        uploadFinalFile = v.findViewById(R.id.uploadFinalFile);
        lblFileName = v.findViewById(R.id.lblFileName);
        lblFinalFileName = v.findViewById(R.id.lblFinalFileName);
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frstFile = 1;
                selectPDF();
            }
        });
        uploadFinalFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frstFile = 0;
                selectPDF();
            }
        });

        imageRecycleView = v.findViewById(R.id.imageRecycleView);
        addEstatePhoto = v.findViewById(R.id.addEstatePhoto);
        addEstatePhoto.setOnClickListener(view -> selectImage());

        ratingDataBtn.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    // on below line we are passing context.
                    getActivity(),
                    (view1, year1, monthOfYear, dayOfMonth) -> {
                        // on below line we are setting date to our text view.
                        rateDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1;
                        ratingDataBtn.setText(rateDate + "");

                    },
                    // on below line we are passing year,
                    // month and day for selected date in our date picker.
                    year, month, day);
            datePickerDialog.show();

        });
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                his_date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                dateBtn.setText(his_date + "");

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                datePickerDialog.show();

            }
        });
        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (yes.isChecked()){
                    Infrastructure = "yes" ;
                }
            }
        });
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (no.isChecked()){
                    Infrastructure = "no" ;
                }
            }
        });


//        CheckBox phone ,out ,elc ,water ;
//        String phoneTx , outTx ,elcTx ,waterTx ;
        phone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (phone.isChecked()){
                    phoneTx = 1 ;
                }else {
                    phoneTx = 0 ;
                }
            }
        });
        out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (out.isChecked()){
                    outTx = 1 ;
                }else {
                    outTx = 0 ;
                }
            }
        });
        elc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (elc.isChecked()){
                    elcTx = 1 ;
                }else {
                    elcTx = 0 ;
                }

            }
        });
        water.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (water.isChecked()){
                    waterTx = 1 ;
                }else {
                    waterTx = 0 ;
                }
            }
        });

//        RadioButton ready , under_construction , space ;
        ready.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (ready.isChecked()){
                    ready_for_user = "جاهز" ;
                }
            }
        });
        under_construction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (under_construction.isChecked()){
                    ready_for_user = "تحت الانشاء" ;
                }
            }
        });
        space.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (space.isChecked()){
                    ready_for_user = "فضاء" ;
                }
            }
        });

        return v;
    }



    public void uploadFile(Uri uri) throws IOException {
        if (uri != null) {
            File file =getFile(getActivity(),uri);
            //Create a file object using file path
            if (frstFile == 1) {
                fName = file.getName();
                lblFileName.setText(fName);
                body = MultipartBody.Part.createFormData("document", fName, RequestBody.create(MediaType.parse("*/*"), file));
            }else {
                fFinalName = file.getName();
                lblFinalFileName.setText(fFinalName);
                body_final_file = MultipartBody.Part.createFormData("rate_final_file", fFinalName, RequestBody.create(MediaType.parse("*/*"), file));
            }

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
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1111);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beforCallAPI();
                List<MultipartBody.Part> imgs = new ArrayList<>();

                for (int i = 0; i < fileUris.size(); i++) {
                    if (fileUris.get(i) != null && getPath(fileUris.get(i)) != null) {
                        File file = new File(getPath(fileUris.get(i)));
                        // create RequestBody instance from file
                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse(getContext().getContentResolver().getType(fileUris.get(i))),
                                        file
                                );
//
//               // MultipartBody.Part is used to send also the actual file name
                        MultipartBody.Part body1 = MultipartBody.Part.createFormData("images[" + i + "]", file.getName(), requestFile);
                        imgs.add(body1);
                    }
                }
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("order_id", RequestBody.create(MultipartBody.FORM , "" + id ));

                map.put("realeStateCode", RequestBody.create(MultipartBody.FORM , "" + realeStateCode.getText().toString() ));
                map.put("general_description_realestate", RequestBody.create(MultipartBody.FORM , "" + general_description_realestate.getText().toString() ));
                map.put("description_location", RequestBody.create(MultipartBody.FORM , "" + description_location.getText().toString() ));
                map.put("instrument_number", RequestBody.create(MultipartBody.FORM , "" + instrument_number.getText().toString() ));
                map.put("infrastructure", RequestBody.create(MultipartBody.FORM , "" + Infrastructure));
                map.put("rateDate", RequestBody.create(MultipartBody.FORM , "" + rateDate));
                map.put("his_date", RequestBody.create(MultipartBody.FORM , "" + his_date));
                map.put("realState_type", RequestBody.create(MultipartBody.FORM , "" + realStateType.getText().toString()));
                map.put("realState_age", RequestBody.create(MultipartBody.FORM , "" + realState_age.getText().toString()));
//                String phoneTx , outTx ,elcTx ,waterTx ;
                map.put("there_phone", RequestBody.create(MultipartBody.FORM , "" + phoneTx));
                map.put("there_out", RequestBody.create(MultipartBody.FORM , "" + outTx));
                map.put("there_electricity", RequestBody.create(MultipartBody.FORM , "" + elcTx));
                map.put("there_water", RequestBody.create(MultipartBody.FORM , "" + waterTx));
                map.put("ready_for_user", RequestBody.create(MultipartBody.FORM , "" + ready_for_user));
                map.put("number", RequestBody.create(MultipartBody.FORM , "" + number.getText().toString()));
                map.put("piece_number", RequestBody.create(MultipartBody.FORM , "" + piece_number.getText().toString()));
                map.put("source", RequestBody.create(MultipartBody.FORM , "" + source.getText().toString()));
                map.put("entry_type", RequestBody.create(MultipartBody.FORM , "" + entry_type.getText().toString()));
                map.put("distance", RequestBody.create(MultipartBody.FORM , "" + distance.getText().toString()));




                // second section
                map.put("north", RequestBody.create(MultipartBody.FORM , "" + north.getText().toString()));
                map.put("northTall", RequestBody.create(MultipartBody.FORM , "" + northTall.getText().toString()));
                map.put("south", RequestBody.create(MultipartBody.FORM , "" + south.getText().toString()));
                map.put("southTall", RequestBody.create(MultipartBody.FORM , "" + southTall.getText().toString()));
                map.put("east", RequestBody.create(MultipartBody.FORM , "" + east.getText().toString()));
                map.put("eastTall", RequestBody.create(MultipartBody.FORM , "" + eastTall.getText().toString()));
                map.put("west", RequestBody.create(MultipartBody.FORM , "" + west.getText().toString()));
                map.put("westTall", RequestBody.create(MultipartBody.FORM , "" + westTall.getText().toString()));
                map.put("in_range", RequestBody.create(MultipartBody.FORM , "" + in_range.getText().toString()));
                map.put("attributed", RequestBody.create(MultipartBody.FORM , "" + attributed.getText().toString()));
                map.put("building_condition", RequestBody.create(MultipartBody.FORM , "" + building_condition.getText().toString()));
                map.put("general_description", RequestBody.create(MultipartBody.FORM , "" + general_description.getText().toString()));
                map.put("theNumberOfTurns", RequestBody.create(MultipartBody.FORM , "" + theNumberOfTurns.getText().toString()));
                map.put("landRate", RequestBody.create(MultipartBody.FORM , "" + landRate.getText().toString()));
                map.put("buildingRate", RequestBody.create(MultipartBody.FORM , "" + buildingRate.getText().toString()));
                map.put("marketing_value", RequestBody.create(MultipartBody.FORM , "" + marketing_value.getText().toString()));
                map.put("totalCost", RequestBody.create(MultipartBody.FORM , "" + totalCost.getText().toString()));
                map.put("realestate_comparing", RequestBody.create(MultipartBody.FORM , "" + realestate_comparing.getText().toString()));
                map.put("conflict_interest", RequestBody.create(MultipartBody.FORM , "" + conflict_interest.getText().toString()));
                map.put("measurement", RequestBody.create(MultipartBody.FORM , "" + measurement.getText().toString()));
                map.put("preview", RequestBody.create(MultipartBody.FORM , "" + preview.getText().toString()));
                // company
                map.put("direction", RequestBody.create(MultipartBody.FORM , "" + direction.getText().toString()));
                map.put("thePurposeOf", RequestBody.create(MultipartBody.FORM , "" + thePurposeOf.getText().toString()));
                map.put("valueBasis", RequestBody.create(MultipartBody.FORM , "" + valueBasis.getText().toString()));
                map.put("reportCurrency", RequestBody.create(MultipartBody.FORM , "" + reportCurrency.getText().toString()));
                map.put("company_measurement", RequestBody.create(MultipartBody.FORM , "" + measurement2.getText().toString()));
                map.put("own_assumptions", RequestBody.create(MultipartBody.FORM , "" + own_assumptions.getText().toString()));
                map.put("reliable_data", RequestBody.create(MultipartBody.FORM , "" + reliable_data.getText().toString()));
                map.put("company_opinion", RequestBody.create(MultipartBody.FORM , "" + company_opinion.getText().toString()));

                        map.put("notes", RequestBody.create(MultipartBody.FORM , "" +  notes));


                CompanyActivity.dialog.show();
                Call<CompanyMakeReportResponse> call = jsonApi.makeReport(LocaleHelper.getLanguage(getContext()), "Bearer "+ CompanyActivity.token , map, body , body_final_file , imgs);

                call.enqueue(new Callback<CompanyMakeReportResponse>() {
                    @Override
                    public void onResponse(Call<CompanyMakeReportResponse> call, Response<CompanyMakeReportResponse> response) {
                        CompanyActivity.dialog.dismiss();
                        CompanyMakeReportResponse companyMakeReportResponse =response.body();
                        if (companyMakeReportResponse != null && companyMakeReportResponse.getCode() == 200){
                            CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
                            getActivity().recreate();
//                            Toast.makeText(getContext() ,companyMakeReportResponse.getMessage()  ,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {

                            CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                            Toast.makeText(getContext() ,companyMakeReportResponse.getMessage() ,Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CompanyMakeReportResponse> call, Throwable t) {
                        CompanyActivity.dialog.dismiss();
                        Toast.makeText(getContext() , t.getMessage()  ,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void beforCallAPI(){
//        widgt =String.valueOf(widgtV.getText());
//        realstateLimits = String.valueOf(realstateLimitsV.getText()) ;
//        realstateGeneralLocation =String.valueOf(realstateGeneralLocationV.getText()) ;
//        attributed =String.valueOf(attributedV.getText() + "");
//        service =String.valueOf(serviceV.getText()) ;
//        nearstStreat = String.valueOf(nearstStreatV.getText()) ;
//        howFasrRealstateRating =String.valueOf(howFasrRealstateRatingV.getText()) ;
//        howFarRealstateCenterCity =String.valueOf(howFarRealstateCenterCityV.getText()) ;
//        Views = String.valueOf(ViewsV.getText()) ;
//        thePartyDirected =String.valueOf(thePartyDirectedV.getText()) ;
//        rateUser =String.valueOf(rateUserV.getText()) ;
//        ratePurpose =String.valueOf(ratePurposeV.getText()) ;
//        realstateLocation =String.valueOf(realstateLocationV.getText()) ;
//        ownerShipType =String.valueOf(ownerShipTypeV.getText()) ;
//        rate =String.valueOf(rateV.getText()) ;
//        valueBasis =String.valueOf(valueBasisV.getText()) ;
//        usedMethods =String.valueOf(usedMethodsV.getText()) ;
//        methodsAndWays =String.valueOf(methodsAndWaysV.getText()) ;
//        valueHypothesis =String.valueOf(valueHypothesisV.getText()) ;
//        applicableEvaluationCriteria =String.valueOf(applicableEvaluationCriteriaV.getText()) ;
//        ndependentConflictOfInterest =String.valueOf(ndependentConflictOfInterestV.getText()) ;
//        referanceNumber=String.valueOf(referanceNumberV.getText()) ;
//        rateType =String.valueOf(rateTypeV.getText()) ;
//        rateVersion=String.valueOf(rateVersionV.getText()) ;
//        rateUses =String.valueOf(rateUsesV.getText()) ;
//        infoSources =String.valueOf(infoSourcesV.getText()) ;
        notes =String.valueOf(notesV.getText()) ;


    }
    public void inflateViews (View v){


        realeStateCode = v.findViewById(R.id.realeStateCode) ;
        general_description_realestate = v.findViewById(R.id.general_description_realestate) ;
        description_location = v.findViewById(R.id.description_location) ;
        instrument_number = v.findViewById(R.id.instrument_number) ;
        realStateType = v.findViewById(R.id.realStateType) ;
        ratingDataBtn = v.findViewById(R.id.ratingDataBtn) ;
        dateBtn = v.findViewById(R.id.dateBtn) ;
        yes = v.findViewById(R.id.yes) ;
        no = v.findViewById(R.id.no) ;
        phone = v.findViewById(R.id.phone) ;
        out = v.findViewById(R.id.out) ;
        elc = v.findViewById(R.id.elc) ;
        water = v.findViewById(R.id.water) ;
        realState_age = v.findViewById(R.id.realState_age) ;
        ready = v.findViewById(R.id.ready) ;
        under_construction = v.findViewById(R.id.under_construction) ;
        space = v.findViewById(R.id.space) ;
        number = v.findViewById(R.id.number) ;
        piece_number = v.findViewById(R.id.piece_number) ;
        source = v.findViewById(R.id.source) ;
        entry_type = v.findViewById(R.id.entry_type) ;
        distance = v.findViewById(R.id.distance) ;


        // second section
        north = v.findViewById(R.id.north) ;
        northTall = v.findViewById(R.id.northTall) ;
        south = v.findViewById(R.id.south) ;
        southTall = v.findViewById(R.id.southTall) ;
        east = v.findViewById(R.id.east) ;
        eastTall = v.findViewById(R.id.eastTall) ;
        west = v.findViewById(R.id.west) ;
        westTall = v.findViewById(R.id.westTall) ;
        in_range = v.findViewById(R.id.in_range) ;
        attributed = v.findViewById(R.id.attributed) ;
        building_condition = v.findViewById(R.id.building_condition) ;
        general_description = v.findViewById(R.id.general_description) ;
        theNumberOfTurns = v.findViewById(R.id.theNumberOfTurns) ;
        landRate = v.findViewById(R.id.landRate) ;
        buildingRate = v.findViewById(R.id.buildingRate) ;
        marketing_value = v.findViewById(R.id.marketing_value) ;
        totalCost = v.findViewById(R.id.totalCost) ;
        realestate_comparing = v.findViewById(R.id.realestate_comparing) ;
        conflict_interest = v.findViewById(R.id.conflict_interest) ;
        measurement = v.findViewById(R.id.measurement) ;
        preview = v.findViewById(R.id.preview) ;
        notesV = v.findViewById(R.id.addNotes) ;
        // company
        direction = v.findViewById(R.id.direction) ;
        thePurposeOf = v.findViewById(R.id.thePurposeOf) ;
        valueBasis = v.findViewById(R.id.valueBasis) ;
        reportCurrency = v.findViewById(R.id.reportCurrency) ;
        measurement2 = v.findViewById(R.id.measurement2) ;
        own_assumptions = v.findViewById(R.id.own_assumptions) ;
        reliable_data = v.findViewById(R.id.reliable_data) ;
        company_opinion = v.findViewById(R.id.company_opinion) ;





//        widgtV = v.findViewById(R.id.EditProfileNameEditText) ;
//        cityV = v.findViewById(R.id.citySpinnerspinner) ;
//        neighborhoodSpinnerV = v.findViewById(R.id.neiborhood) ;
//        realstateLimitsV = v.findViewById(R.id.addRealStateLimits) ;
//        realstateGeneralLocationV = v.findViewById(R.id.generalRealStateLocation) ;
//        attributedV = v.findViewById(R.id.addAttributed) ;
//        serviceV = v.findViewById(R.id.addService);
//        nearstStreatV = v.findViewById(R.id.addstreat) ;
//        howFasrRealstateRatingV = v.findViewById(R.id.howFarRealstateRation) ;
//        howFarRealstateCenterCityV = v.findViewById(R.id.howFarRealstateFromCityCenter);
//        ViewsV = v.findViewById(R.id.theViews) ;
//        thePartyDirectedV = v.findViewById(R.id.theDirection) ;
//        rateUserV = v.findViewById(R.id.reportUser) ;
//        ratePurposeV = v.findViewById(R.id.thePurposeOf) ;
//        realStateTypeV = v.findViewById(R.id.realsStateType);
//        realstateLocationV  = v.findViewById(R.id.realStateLocation);
//        ownerShipTypeV = v.findViewById(R.id.ownerShipType) ;
//        rateV = v.findViewById(R.id.valueBasis) ;
//        valueBasisV = v.findViewById(R.id.valueBasis) ;
//        rateDateV = v.findViewById(R.id.rateDate) ;
//        previweDateV = v.findViewById(R.id.previewDate) ;
//        usedMethodsV  = v.findViewById(R.id.rateTechniques);
//        methodsAndWaysV = v.findViewById(R.id.approvedStyleAndMethod) ;
//        valueHypothesisV = v.findViewById(R.id.valueHypothesis) ;
//        applicableEvaluationCriteriaV = v.findViewById(R.id.evaluationCriteria) ;
//        ndependentConflictOfInterestV= v.findViewById(R.id.independentConflictOfInterest) ;
//        referanceNumberV = v.findViewById(R.id.referenceNumber) ;
//        rateTypeV = v.findViewById(R.id.reportType) ;
//        rateDate1V= v.findViewById(R.id.reportDate) ;
//        rateVersionV= v.findViewById(R.id.reportVersion) ;
//        rateUsesV = v.findViewById(R.id.ReportUseAndPublicationAndDistributionRestrictions) ;
//      infoSourcesV= v.findViewById() ;
//        neighborhoodSpinnerV2 = v.findViewById(R.id.neiborhood2);
//        cityV2 = v.findViewById(R.id.citySpinnerspinner2);
        sendRate = v.findViewById(R.id.sendRate);
    }

//    public void callRegionstype(int id){
//        Call<GetRegionResponse> getRegionResponseCall = jsonApi.getRegions(new RegionParams(id));
//        getRegionResponseCall.enqueue(new Callback<GetRegionResponse>() {
//            @Override
//            public void onResponse(Call<GetRegionResponse> call, Response<GetRegionResponse> response) {
//                GetRegionResponse regionResponse = response.body();
//                if (response.code() == 200) {
//                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                    regionAdapter = new CustomRegionAdapter(regionResponse.getData().getCities());
//                    neighborhoodSpinnerV.setAdapter(regionAdapter);
////                    neighborhoodSpinnerV2.setAdapter(regionAdapter);
//                }else {
//                    CompanyActivity.alert.crateMsg(response.body().getMessage() , getContext());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetRegionResponse> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    public void callRegionstype2(int id){
//        Call<GetRegionResponse> getRegionResponseCall = jsonApi.getRegions(new RegionParams(id));
//        getRegionResponseCall.enqueue(new Callback<GetRegionResponse>() {
//            @Override
//            public void onResponse(Call<GetRegionResponse> call, Response<GetRegionResponse> response) {
//                GetRegionResponse regionResponse = response.body();
//                if (response.code() == 200) {
//                    regionAdapter = new CustomRegionAdapter(regionResponse.getData().getCities());
//                    neighborhoodSpinnerV.setAdapter(regionAdapter);
////                    neighborhoodSpinnerV2.setAdapter(regionAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetRegionResponse> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void selectImage() {
        Intent intent = new Intent(getActivity(), ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CROP, true);//default is false
        startActivityForResult(intent, 1213);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == RESULT_OK && data != null) {
            // check condition
            if (data.getData() != null) {
                try {
                    uploadFile(data.getData());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (requestCode==1213 && resultCode==RESULT_OK && data!=null) {
            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            fileUris.add(Uri.fromFile(new File(filePath)));
            bitmaps.add(selectedImage);
            imageAdapter = new ImageAdapter(bitmaps);
            LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
            imageRecycleView.setLayoutManager(lm);
            imageRecycleView.setHasFixedSize(true);
            imageRecycleView.setAdapter(imageAdapter);
        }
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }



}