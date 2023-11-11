package com.qaim.qaim.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.qaim.qaim.Activities.MainActivity;
import com.qaim.qaim.Classes.CompanyRateParams;
import com.qaim.qaim.Classes.CustomCityAdapter;
import com.qaim.qaim.Classes.CustomUserAttributesAdapter;
import com.qaim.qaim.Classes.PreviewerRateParams;
import com.qaim.qaim.Classes.ShowRealstateUserParams;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RateResponse.RateResponse;
import com.qaim.qaim.Models.UserReportResponse.UserReportResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ReportsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private int id , hasReport , hasCompleted ;
    Retrofit retrofit ;
    JsonApi jsonApi ;
    CustomCityAdapter cityAdapter;
    int companyId ;
    int previwerId ;
     SharedPreferences rating ;
     SharedPreferences.Editor ratinEditor ;
    RatingBar companyRating , previewerRating ;
    String widgt ,city , region ,realstateLimits ,realstateGeneralLocation ,attributed , service , nearstStreat ,howFasrRealstateRating ,
    howFarRealstateCenterCity , Views , thePartyDirected , rateUser ,ratePurpose ,realStateType ,realstateLocation , ownerShipType ,
    rate ,valueBasis ,rateDate ,previweDate ,usedMethods ,methodsAndWays , valueHypothesis , applicableEvaluationCriteria , ndependentConflictOfInterest ,
    referanceNumber , rateType ,rateDate1 ,rateVersion , rateUses ,infoSources , notes , doc , docName ;

    RecyclerView rvAttributes ;
    CustomUserAttributesAdapter adapter ;
    TextView tvdocName;

//    TextView widgtV ,cityV , regionV ,realstateLimitsV ,realstateGeneralLocationV ,attributedV , serviceV , nearstStreatV ,howFasrRealstateRatingV ,
//            howFarRealstateCenterCityV , ViewsV , thePartyDirectedV , rateUserV ,ratePurposeV ,realStateTypeV ,realstateLocationV , ownerShipTypeV ,
//            rateV ,valueBasisV ,rateDateV ,previweDateV ,usedMethodsV ,methodsAndWaysV , valueHypothesisV , applicableEvaluationCriteriaV , ndependentConflictOfInterestV ,
//            referanceNumberV , rateTypeV ,rateDate1V  , rateUsesV ,infoSourcesV , notesV ;

    Button printRateBtn ;
    RelativeLayout rateVersionV;
    ImageSlider imageSlider ;
    ArrayList<SlideModel> arrayList ;
    public void inflateViews (View v){
//        widgtV = v.findViewById(R.id.EditProfileNameEditText) ;
//        cityV = v.findViewById(R.id.citySpinnerspinner) ;
//        regionV = v.findViewById(R.id.neiborhood) ;
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
        rateVersionV= v.findViewById(R.id.reportVersion) ;
//        rateUsesV = v.findViewById(R.id.ReportUseAndPublicationAndDistributionRestrictions) ;
//        infoSourcesV= v.findViewById(R.id.infoSourcesV) ;
//        notesV = v.findViewById(R.id.addNotes) ;
        printRateBtn = v.findViewById(R.id.printRateBtn) ;
        rvAttributes = v.findViewById(R.id.rvAttributes);
        tvdocName = v.findViewById(R.id.docName);
        rating = getActivity().getSharedPreferences("rate" , MODE_PRIVATE);
        ratinEditor = rating.edit();
        imageSlider = v.findViewById(R.id.image_slider);
        arrayList = new ArrayList<>();


    }

    public ReportsFragment() {
        // Required empty public constructor
    }

    public static ReportsFragment newInstance(int id , int hasReport , int hasCompleted ) {
        ReportsFragment fragment = new ReportsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        args.putInt(ARG_PARAM2, hasReport);
        args.putInt(ARG_PARAM3, hasCompleted);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
            hasReport = getArguments().getInt(ARG_PARAM2);
            hasCompleted = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reports, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailsAcceptableFragment fragment = OrderDetailsAcceptableFragment.newInstance(id , hasReport , hasCompleted);
                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        replace(R.id.frameLayout , fragment).commit();
            }
        });
        Locale.setDefault(Locale.ENGLISH);
        Resources res = getContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        float rating = pref.getFloat("numStars", 0f);



        inflateViews(v);
        callReportAPI();


        companyRating = v.findViewById(R.id.simpleRatingBarComp);
        previewerRating = v.findViewById(R.id.simpleRatingBarPrev);
        rateVersionV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rateVersion));
                startActivity(browserIntent);
            }
        });
        printRateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doc != null){

//                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ docName);
//                    Intent target = new Intent(Intent.ACTION_VIEW);
//                    target.setDataAndType(Uri.fromFile(file),"application/pdf");
//                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//                    Intent intent = Intent.createChooser(target, "Open File");
//                    try {
//                        startActivity(intent);
//                    } catch (ActivityNotFoundException e) {
//                        // Instruct the user to install a PDF reader here, or something
//                    }

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(doc));
                    startActivity(browserIntent);
                }
            }
        });

//        previewerRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                Call<RateResponse> call = jsonApi.previewerRate("Bearer " + MainActivity.token , new PreviewerRateParams(id ,previwerId ,ratingBar.getRating() ));
//                call.enqueue(new Callback<RateResponse>() {
//                    @Override
//                    public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
//                        RateResponse rateResponse = response.body();
//                        if (rateResponse.getCode() == 200){
//                            Toast.makeText(getContext() , rateResponse.getMessage() , Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(getContext() , rateResponse.getMessage() , Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<RateResponse> call, Throwable t) {
//                        Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        return v ;
    }

    public void callReportAPI(){
        MainActivity.dialog.show();
        Call<UserReportResponse> call = jsonApi.report("Bearer " + MainActivity.token , new ShowRealstateUserParams(id));
        call.enqueue(new Callback<UserReportResponse>() {
            @Override
            public void onResponse(Call<UserReportResponse> call, Response<UserReportResponse> response) {
                MainActivity.dialog.dismiss();
                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                UserReportResponse userReportResponse = response.body();
                if (userReportResponse.getCode() == 200) {

                    companyId = userReportResponse.getData().getRow().getCompany().getId();
                    previwerId = userReportResponse.getData().getRow().getPreviewer().getId();
                    if (userReportResponse.getData().getRow().getPreviewer_has_Rate() == 1){
                        previewerRating.setRating(userReportResponse.getData().getRow().getPreviewerRate());
                    }
                    if (userReportResponse.getData().getRow().getCompany_has_Rate() == 1){
                        companyRating.setRating(userReportResponse.getData().getRow().getCompanyRate());
                    }


                    if (!userReportResponse.getData().getRow().getAttributes().isEmpty() && userReportResponse.getData().getRow().getAttributes() != null) {
                        adapter = new CustomUserAttributesAdapter(userReportResponse.getData().getRow().getAttributes());
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        rvAttributes.setLayoutManager(lm);
                        rvAttributes.setHasFixedSize(true);
                        rvAttributes.setAdapter(adapter);
                    }


                    if (!userReportResponse.getData().getRow().getInfo().getRealEstate().getFiles().isEmpty() && userReportResponse.getData().getRow().getInfo().getRealEstate().getFiles() != null) {

                        for (int i = 0; i < userReportResponse.getData().getRow().getImages().size(); i++) {
                            String file = userReportResponse.getData().getRow().getImages().get(0).getFile();
                            arrayList.add(new SlideModel(file, ScaleTypes.FIT));
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
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.getWindow().getAttributes().windowAnimations = R.style.ImageDialogAnimation;
                                dialog.getWindow().setGravity(Gravity.BOTTOM);

                            }
                        });
//                        userReportResponse.getData().getRow().get

//                    widgtV.setText(String.valueOf(userReportResponse.getData().getRow().getLandLines()));
//
//                    cityV.setText(String.valueOf(userReportResponse.getData().getRow().getCity().getName()));
//
//                    regionV.setText(String.valueOf(userReportResponse.getData().getRow().getRegion().getName()));
                        rateVersion = userReportResponse.getData().getRow().getRateFinalFile();
//                    realstateLimitsV.setText(String.valueOf(userReportResponse.getData().getRow().getBoundries()));
//
//                    realstateGeneralLocationV.setText(String.valueOf(userReportResponse.getData().getRow().getLocationTxt()));
//
//                    attributedV.setText(String.valueOf(userReportResponse.getData().getRow().getAttributed()));
//
//                    serviceV.setText(String.valueOf(userReportResponse.getData().getRow().getServices()));
//
//                    nearstStreatV.setText(String.valueOf(userReportResponse.getData().getRow().getNerestCommerialStreet()));
//
//                    howFasrRealstateRatingV.setText(String.valueOf(userReportResponse.getData().getRow().getNerestCommerialStreetDistance()));
//
//                    howFarRealstateCenterCityV.setText(String.valueOf(userReportResponse.getData().getRow().getDistanceToCityCenter()));
//
//                    ViewsV.setText(String.valueOf(userReportResponse.getData().getRow().getInterfaces()));
//                    thePartyDirectedV.setText(String.valueOf(userReportResponse.getData().getRow().getClientName()));
//                    rateUserV.setText(String.valueOf(userReportResponse.getData().getRow().getClientUsername()));
//                    ratePurposeV.setText(String.valueOf(userReportResponse.getData().getRow().getClientReasons()));
////                    realStateTypeV.setText(String.valueOf(userReportResponse.getData().getRow().getClientType().getName()));
////                    realstateLocationV.setText(String.valueOf(userReportResponse.getData().getRow().getClientCity().getName()));
//                    ownerShipTypeV.setText(String.valueOf(userReportResponse.getData().getRow().getClientOwnerType()));
//
//                    rateV.setText(String.valueOf(userReportResponse.getData().getRow().getRateAmount()));
//                    rateDateV.setText(String.valueOf(userReportResponse.getData().getRow().getRateDayDate()));
//                    previweDateV.setText(String.valueOf(userReportResponse.getData().getRow().getRatePreviewDate()));
//                    usedMethodsV.setText(String.valueOf(userReportResponse.getData().getRow().getRateType()));
//                    methodsAndWaysV.setText(String.valueOf(userReportResponse.getData().getRow().getRateTypeDone()));
//                    valueHypothesisV.setText(String.valueOf(userReportResponse.getData().getRow().getRateAmountUsage()));
//                    applicableEvaluationCriteriaV.setText(String.valueOf(userReportResponse.getData().getRow().getRateSteps()));
//                    ndependentConflictOfInterestV.setText(String.valueOf(userReportResponse.getData().getRow().getRateIndependantTxt()));
//                    referanceNumberV.setText(String.valueOf( userReportResponse.getData().getRow().getRateRefNo()));
//                    rateTypeV.setText(String.valueOf(userReportResponse.getData().getRow().getRateReportType()));
//                    rateDate1V.setText(String.valueOf(userReportResponse.getData().getRow().getRateReportDate()));
//                    rateUsesV.setText(String.valueOf(userReportResponse.getData().getRow().getRateFinalFileName()));
//                    infoSourcesV.setText(String.valueOf(userReportResponse.getData().getRow().getRateTerms()));
//                    notesV.setText(String.valueOf(userReportResponse.getData().getRow().getNotes()));
                        doc = userReportResponse.getData().getRow().getDocument();
                        docName = userReportResponse.getData().getRow().getDocumentName();

                        if (docName.equals("") || docName == null) {
                            tvdocName.setText("عرض الملف");
                        } else {
                            tvdocName.setText(docName);
                        }
                        companyRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                MainActivity.dialog.show();
                                Call<RateResponse> call = jsonApi.companyRate("Bearer " + MainActivity.token, new CompanyRateParams(id, companyId, companyRating.getRating()));
                                call.enqueue(new Callback<RateResponse>() {
                                    @Override
                                    public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                                        MainActivity.dialog.dismiss();
                                        RateResponse rateResponse = response.body();
                                        if (rateResponse.getCode() == 200) {
                                            Toast.makeText(getContext(), rateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), rateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        MainActivity.dialog.dismiss();

                                    }

                                    @Override
                                    public void onFailure(Call<RateResponse> call, Throwable t) {
                                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                        previewerRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                MainActivity.dialog.show();
                                Call<RateResponse> call = jsonApi.previewerRate("Bearer " + MainActivity.token, new PreviewerRateParams(id, previwerId,  previewerRating.getRating()));
                                call.enqueue(new Callback<RateResponse>() {
                                    @Override
                                    public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                                        MainActivity.dialog.dismiss();
                                        RateResponse rateResponse = response.body();
                                        if (rateResponse.getCode() == 200) {
                                            Toast.makeText(getContext(), rateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), rateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        MainActivity.dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure(Call<RateResponse> call, Throwable t) {
                                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });


                    }

                }
            }

            @Override
            public void onFailure(Call<UserReportResponse> call, Throwable t) {

            }
        });
    }
}