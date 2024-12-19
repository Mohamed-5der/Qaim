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
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

import com.qaim.qaim.Activities.PreviewerActivity;
import com.qaim.qaim.Classes.ImageAdapter;
import com.qaim.qaim.LocaleHelper;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.PreviewerMakeReport.PreviewerMakeReportResponse;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreviewerWriteReportsFragment extends Fragment {

    Button uploadImage;
    LinearLayout uploadFile1;
    TextView lblFileName;
    ActivityResultLauncher<Intent> resultLauncher;
    MultipartBody.Part body = null;
    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String PDF_DIRECTORY = "/demonuts_upload_gallery";
    private static final String ARG_PARAM1 = "id";
    Retrofit retrofit;
    JsonApi jsonApi;
    private int id;
//    CustomCityAdapter cityAdapter;
//    int cityId;int cityId2;
//    CustomRegionAdapter regionAdapter;
//    int regionId;int regionId2;

//    Spinner  neighborhoodSpinnerV, cityV , neighborhoodSpinnerV2, cityV2;

    Button sendRate;
//    String widgt ,realstateLimits ,realstateGeneralLocation ,attributed , service , nearstStreat ,howFasrRealstateRating ,
//            howFarRealstateCenterCity , Views , thePartyDirected , rateUser ,ratePurpose ,realStateType ,realstateLocation ,
//            ownerShipType ,
//            rate ,valueBasis ,rateDate ,previweDate ,usedMethods ,methodsAndWays , valueHypothesis , applicableEvaluationCriteria ,
//            ndependentConflictOfInterest ,
//            referanceNumber , rateType ,rateDate1 ,rateVersion , rateUses ,infoSources ,  , doc , docName ;


//    EditText widgtV   ,realstateLimitsV ,realstateGeneralLocationV ,attributedV , serviceV , nearstStreatV ,howFasrRealstateRatingV ,
//            howFarRealstateCenterCityV , ViewsV , thePartyDirectedV , rateUserV ,ratePurposeV ,realStateTypeV ,realstateLocationV , ownerShipTypeV ,
//            rateV ,valueBasisV ,rateDateV ,previweDateV ,usedMethodsV ,methodsAndWaysV , valueHypothesisV , applicableEvaluationCriteriaV , ndependentConflictOfInterestV ,
//            referanceNumberV , rateTypeV ,rateDate1V ,rateVersionV , rateUsesV ,infoSourcesV , notesV ;

    // second section

    EditText north ,northTall , south, southTall, east, eastTall,
            west, westTall, in_range, attributed, building_condition,
            general_description, theNumberOfTurns,
            landRate, buildingRate, marketing_value,
            totalCost, realestate_comparing,
            conflict_interest, measurement, preview, notesV;
    String notes;

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
    ImageView imageView;
    List<String> imageURLs = new ArrayList<>();
    List<Bitmap> bitmaps = new ArrayList<>();
    RecyclerView imageRecycleView;
    ImageAdapter imageAdapter;

    Button addEstatePhoto;

    public PreviewerWriteReportsFragment() {
        // Required empty public constructor
    }


    public static PreviewerWriteReportsFragment newInstance(int id) {
        PreviewerWriteReportsFragment fragment = new PreviewerWriteReportsFragment();
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
        View v = inflater.inflate(R.layout.fragment_previewer_write_reports, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            PreviewerBusinessInProgressFragment mainFragment = new PreviewerBusinessInProgressFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mainFragment).commit();
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
//                            Uri uri = data.getData();
//                            pdfPath = getFilePathFromURI(uri);
//
//                            // When data is not equal to empty
//                            // Get PDf uri
////                            Uri sUri = data.getData();
////                            String sPath = sUri.getPath();
////                            Toast.makeText(getActivity() , sUri + "" , Toast.LENGTH_SHORT).show();
////                            Toast.makeText(getActivity() , sPath + "" , Toast.LENGTH_SHORT).show();

                            try {
                                uploadFile(data.getData());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    }
                });
        uploadFile1 = v.findViewById(R.id.uploadFile);
        lblFileName = v.findViewById(R.id.lblFileName);
        imageRecycleView = v.findViewById(R.id.imageRecycleView);
        addEstatePhoto = v.findViewById(R.id.addEstatePhoto);
        addEstatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallory();
            }
        });

        uploadFile1.setOnClickListener(new View.OnClickListener() {
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
                            new String[]{
                                    Manifest.permission
                                            .READ_EXTERNAL_STORAGE},
                            1);
                } else {
                    // When permission is granted
                    // Create method
                    selectPDF();
                }
            }
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        inflateViews(v);
//        Call<CitiesResponse> citiesResponseCall = jsonApi.getCities();
//        citiesResponseCall.enqueue(new Callback<CitiesResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<CitiesResponse> call, @NonNull Response<CitiesResponse> response) {
//                if (response.code() == 200) {
//                    cityAdapter = new CustomCityAdapter(response.body().getData().getCities());
//                    cityV.setAdapter(cityAdapter);
//                    cityV2.setAdapter(cityAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<CitiesResponse> call, @NonNull Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//        cityV.setAdapter(cityAdapter);
//        cityV2.setAdapter(cityAdapter);
//        cityV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                cityId = (int) cityAdapter.getItemId(i);
//                callRegionstype(cityId);
//                neighborhoodSpinnerV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        regionId = (int) regionAdapter.getItemId(i);
//                        Toast.makeText(getContext(), "" + regionId, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        cityV2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                cityId2 = (int) cityAdapter.getItemId(i);
//                callRegionstype(cityId2);
//                neighborhoodSpinnerV2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        regionId = (int) regionAdapter.getItemId(i);
//                        Toast.makeText(getContext(), "" + regionId, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        callRegionstype(cityId);callRegionstype(cityId2);

//        Button ratingDataBtn , dateBtn  ;
//        CheckBox yes , no ;

        ratingDataBtn.setOnClickListener(new View.OnClickListener() {
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
                                rateDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                ratingDataBtn.setText(rateDate + "");

                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                datePickerDialog.show();

            }
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



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beforCallAPI();

                List<MultipartBody.Part> imgs = new ArrayList<>();

                for (int i = 0; i < fileUris.size(); i++) {
                    File file = new File(getPath(fileUris.get(i)));
                    // create RequestBody instance from file
                    RequestBody requestFile =
                            RequestBody.create(
                                    MediaType.parse(getContext().getContentResolver().getType(fileUris.get(i))),
                                    file
                            );
//
//               // MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body1 = MultipartBody.Part.createFormData("images["+i+"]", file.getName(), requestFile);
                    imgs.add(body1);
                }




                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("info_id", RequestBody.create(MultipartBody.FORM , "" + id ));
                // first section
//                EditText realeStateCode , general_description_realestate
//                        ,description_location , instrument_number,
//                        realStateType;


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

//                EditText number ,piece_number ,
//                        source ,entry_type ,distance ;

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
                map.put("notes", RequestBody.create(MultipartBody.FORM , "" +  notes));



                PreviewerActivity.dialog.show();
                Call<PreviewerMakeReportResponse> call = jsonApi.makeReportPreviewer(LocaleHelper.getLanguage(getContext()), "Bearer " + PreviewerActivity.token , map, body , imgs);
                call.enqueue(new Callback<PreviewerMakeReportResponse>() {
                    @Override
                    public void onResponse(Call<PreviewerMakeReportResponse> call, Response<PreviewerMakeReportResponse> response) {
                        PreviewerActivity.dialog.dismiss();
                        PreviewerMakeReportResponse previewerReportsResponse = response.body();
                        if (previewerReportsResponse.getCode() == 200){
                            PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                            Toast.makeText(getContext() ,previewerReportsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            getActivity().recreate();
                        }
                        else {
                            PreviewerActivity.alert.creatDialog(response.body().getMessage() , getContext());
//                            Toast.makeText(getContext() ,previewerReportsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PreviewerMakeReportResponse> call, Throwable t) {
                        Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    public void beforCallAPI(){
        // olds
//        widgt =String.valueOf(widgtV.getText());
//        realstateLimits = String.valueOf(realstateLimitsV.getText()) ;
//        realstateGeneralLocation =String.valueOf(realstateGeneralLocationV.getText()) ;
//        attributed =(attributedV.getText().toString() + "");
//        service =String.valueOf(serviceV.getText()) ;
//        nearstStreat = String.valueOf(nearstStreatV.getText()) ;
//        howFasrRealstateRating =String.valueOf(howFasrRealstateRatingV.getText()) ;
//        howFarRealstateCenterCity =String.valueOf(howFarRealstateCenterCityV.getText()) ;
//        Views = String.valueOf(ViewsV.getText()) ;
//        thePartyDirected =String.valueOf(thePartyDirectedV.getText()) ;
//        rateUser =String.valueOf(rateUserV.getText()) ;
//        ratePurpose =String.valueOf(ratePurposeV.getText()) ;


//        realStateType =String.valueOf(realStateTypeV.getText()) ;
//        realstateLocation =String.valueOf(realstateLocationV.getText()) ;
//        ownerShipType =String.valueOf(ownerShipTypeV.getText()) ;
//        rate =String.valueOf(rateV.getText()) ;
//        valueBasis =String.valueOf(valueBasisV.getText()) ;
//        rateDate =String.valueOf(rateDateV.getText()) ;
//        previweDate=String.valueOf(previweDateV.getText()) ;
//        usedMethods =String.valueOf(usedMethodsV.getText()) ;
//        methodsAndWays =String.valueOf(methodsAndWaysV.getText()) ;
//        valueHypothesis =String.valueOf(valueHypothesisV.getText()) ;
//        applicableEvaluationCriteria =String.valueOf(applicableEvaluationCriteriaV.getText()) ;
//        ndependentConflictOfInterest =String.valueOf(ndependentConflictOfInterestV.getText()) ;
//        referanceNumber=String.valueOf(referanceNumberV.getText()) ;
//        rateType =String.valueOf(rateTypeV.getText()) ;
//        rateDate1 =String.valueOf(rateDate1V.getText()) ;
//        rateVersion=String.valueOf(rateVersionV.getText()) ;
//        rateUses =String.valueOf(rateUsesV.getText()) ;
//        infoSources =String.valueOf(infoSourcesV.getText()) ;


        notes =String.valueOf(notesV.getText()) ;


    }
    public void inflateViews (View v){
        // olds
//        widgtV = v.findViewById(R.id.EditProfileNameEditText) ;
//        cityV = v.findViewById(R.id.citySpinnerspinner) ;
//        neighborhoodSpinnerV = v.findViewById(R.id.neiborhood) ;
//        realstateLimitsV = v.findViewById(R.id.addRealStateLimits) ;
//        realstateGeneralLocationV = v.findViewById(R.id.generalRealStateLocation) ;
//        attributedV =  v.findViewById(R.id.addAttributed) ;
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


        // olds
//        ownerShipTypeV = v.findViewById(R.id.ownerShipType) ;
//        rateV = v.findViewById(R.id.valueBasis) ;


//        valueBasisV = v.findViewById(R.id.valueBasis) ;

        // olds
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

        // first section
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
//        neighborhoodSpinnerV2 = v.findViewById(R.id.neiborhood2);
//        cityV2 = v.findViewById(R.id.citySpinnerspinner2);
        sendRate = v.findViewById(R.id.sendReport);
    }
//    public void callRegionstype(int id){
//        Call<GetRegionResponse> getRegionResponseCall = jsonApi.getRegions(new RegionParams(id));
//        getRegionResponseCall.enqueue(new Callback<GetRegionResponse>() {
//            @Override
//            public void onResponse(Call<GetRegionResponse> call, Response<GetRegionResponse> response) {
//                GetRegionResponse regionResponse = response.body();
//                if (response.code() == 200) {
//                    regionAdapter = new CustomRegionAdapter(regionResponse.getData().getCities());
//                    neighborhoodSpinnerV.setAdapter(regionAdapter);
//                    neighborhoodSpinnerV2.setAdapter(regionAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetRegionResponse> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    public void uploadFile(Uri uri) throws IOException {
        if (uri != null) {
            File file =getFile(getActivity(),uri);
            //Create a file object using file path
            String fName = file.getName();
            lblFileName.setText(fName);
            body = MultipartBody.Part.createFormData("document", fName, RequestBody.create(MediaType.parse("*/*"), file));
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


    // select images
    public void openGallory(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            // when permission is nor granted
            // request permission
            ActivityCompat.requestPermissions(getActivity()
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);

        }
        else
        {
            // when permission
            // is granted
            // create method
            selectImage();
        }

    }
    private void selectImage() {
        // clear previous data
//        imageView.setImageBitmap(null);
        // Initialize intent
        Intent intent=new Intent(Intent.ACTION_PICK);
        // set type
        intent.setType("image/*");
        // start activity result
        startActivityForResult(Intent.createChooser(intent,getString(R.string.select_image)),100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check condition
        if (requestCode==100 && resultCode==RESULT_OK && data!=null)
        {
            // when result is ok
            // initialize uri
            Uri uri=data.getData();
            // Initialize bitmap
            try {
                fileUris.add(uri);// = uri;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                Toast.makeText(getContext() , bitmap + "" , Toast.LENGTH_LONG).show();
                // initialize byte stream
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                // compress Bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                // Initialize byte array
                byte[] bytes=stream.toByteArray();
                // get base64 encoded string
                String imageURL= Base64.encodeToString(bytes,Base64.DEFAULT);
                // set encoded text on textview
                bytes=Base64.decode(imageURL,Base64.DEFAULT);
                // Initialize bitmap
                bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                // set bitmap on imageView
//                .setImageBitmap(bitmap);
//                imageView
                bitmaps.add(bitmap);

                imageURLs.add(imageURL);
                imageAdapter = new ImageAdapter(bitmaps);
                LinearLayoutManager lm = new LinearLayoutManager(getContext() ,LinearLayoutManager.HORIZONTAL , true);
                imageRecycleView.setLayoutManager(lm);
                imageRecycleView.setHasFixedSize(true);
                imageRecycleView.setAdapter(imageAdapter);
//                imageAdapter.notifyDataSetChanged();

                // reload

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        MultipartBody.Part body ;
//        RequestBody model ;
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