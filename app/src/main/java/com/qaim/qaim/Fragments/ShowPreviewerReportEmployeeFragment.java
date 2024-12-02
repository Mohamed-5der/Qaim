package com.qaim.qaim.Fragments;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.qaim.qaim.Activities.EmployeeActivity;
import com.qaim.qaim.Classes.CustomAttributedAdapter;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Classes.StatusReportParams;
import com.qaim.qaim.Models.AcceptPrevReportEmp.AcceptPrevReportEmpResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RefusedPrevReport.RefusePrevReportEmpResponse;
import com.qaim.qaim.Models.ShowPrevReportEmp.AttributesItem;
import com.qaim.qaim.Models.ShowPrevReportEmp.ShowPrevReportEmpResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowPreviewerReportEmployeeFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";

    private int id;
    int reportId ;


    Retrofit retrofit;
    JsonApi jsonApi;

    String widgt ,city , region ,realstateLimits ,realstateGeneralLocation ,attributed , service , nearstStreat ,howFasrRealstateRating ,
            howFarRealstateCenterCity , Views , thePartyDirected , rateUser ,ratePurpose ,realStateType ,realstateLocation , ownerShipType ,
            rate ,valueBasis ,rateDate ,previweDate ,usedMethods ,methodsAndWays , valueHypothesis , applicableEvaluationCriteria , ndependentConflictOfInterest ,
            referanceNumber , rateType ,rateDate1 ,rateVersion , rateUses ,infoSources , notes , doc , docName ;


//    TextView widgtV ,cityV , regionV ,realstateLimitsV ,realstateGeneralLocationV  , serviceV , nearstStreatV ,howFasrRealstateRatingV ,
//            howFarRealstateCenterCityV , ViewsV , thePartyDirectedV , rateUserV ,ratePurposeV ,realStateTypeV ,realstateLocationV , ownerShipTypeV ,
//            rateV ,valueBasisV ,rateDateV ,previweDateV ,usedMethodsV ,methodsAndWaysV , valueHypothesisV , applicableEvaluationCriteriaV , ndependentConflictOfInterestV ,
//            referanceNumberV , rateTypeV ,rateDate1V  ,attributedtxt  , rateUsesV ,infoSourcesV , notesV ;

    Button accept , reject ;
    RelativeLayout rateVersionV;
    String accepted ;
    RecyclerView attributedV ;
    CustomAttributedAdapter adapter;
    TextView txDocName;
    ImageSlider imageSlider ;
    ArrayList<SlideModel> arrayList ;
    public void inflateViews (View v){
//        widgtV = v.findViewById(R.id.EditProfileNameEditText) ;
//        cityV = v.findViewById(R.id.citySpinnerspinner) ;
//        regionV = v.findViewById(R.id.neiborhood) ;
//        realstateLimitsV = v.findViewById(R.id.addRealStateLimits) ;
//        realstateGeneralLocationV = v.findViewById(R.id.generalRealStateLocation) ;
        attributedV = v.findViewById(R.id.addAttributed) ;
//        attributedtxt = v.findViewById(R.id.attributed) ;
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
        accept = v.findViewById(R.id.accept) ;
        reject = v.findViewById(R.id.reject) ;
        txDocName = v.findViewById(R.id.txDocName);
        imageSlider = v.findViewById(R.id.image_slider);
        arrayList = new ArrayList<>();

    }



    public ShowPreviewerReportEmployeeFragment() {
        // Required empty public constructor
    }


    public static ShowPreviewerReportEmployeeFragment newInstance(int id) {
        ShowPreviewerReportEmployeeFragment fragment = new ShowPreviewerReportEmployeeFragment();
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
        View v = inflater.inflate(R.layout.fragment_show_previewer_report_employee, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(view -> {
            EmployeeMainFragment fragment = new EmployeeMainFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout , fragment).commit();
        });

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        inflateViews(v);
        callReportAPI();
        return v ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accept.setOnClickListener(view1 -> {
            EmployeeActivity.dialog.show();
            Call<AcceptPrevReportEmpResponse> acceptPreviewerReportResponseCall = jsonApi.acceptPreviewerEmpReport("Bearer " + EmployeeActivity.token , new StatusReportParams(reportId));
            acceptPreviewerReportResponseCall.enqueue(new Callback<AcceptPrevReportEmpResponse>() {
                @Override
                public void onResponse(Call<AcceptPrevReportEmpResponse> call, Response<AcceptPrevReportEmpResponse> response) {

                    EmployeeActivity.dialog.dismiss();
                    AcceptPrevReportEmpResponse reportResponse = response.body();
                    if (reportResponse.getCode() == 200){
                        getActivity().recreate();
                        Toast.makeText(getActivity(),reportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),reportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AcceptPrevReportEmpResponse> call, Throwable t) {
                    Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });

        reject.setOnClickListener(view12 -> {
            EmployeeActivity.dialog.show();
            Call<RefusePrevReportEmpResponse> acceptPreviewerReportResponseCall = jsonApi.rejectPreviewerEmpReport("Bearer " + EmployeeActivity.token , new StatusReportParams(reportId));
            acceptPreviewerReportResponseCall.enqueue(new Callback<RefusePrevReportEmpResponse>() {
                @Override
                public void onResponse(Call<RefusePrevReportEmpResponse> call, Response<RefusePrevReportEmpResponse> response) {

                    EmployeeActivity.dialog.dismiss();
                    RefusePrevReportEmpResponse reportResponse = response.body();
                    if (reportResponse.getCode() == 200){
                        getActivity().recreate();
                        Toast.makeText(getActivity(),reportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),reportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RefusePrevReportEmpResponse> call, Throwable t) {
                    Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        });
        rateVersionV.setOnClickListener(view13 -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rateVersion));
            startActivity(browserIntent);
        });
    }
    public void callReportAPI(){
        EmployeeActivity.dialog.show();
        Call<ShowPrevReportEmpResponse> call = jsonApi.showPreviewerEmpReport("Bearer " + EmployeeActivity.token , new OrderListItemParams(id));
        call.enqueue(new Callback<ShowPrevReportEmpResponse>() {
            @Override
            public void onResponse(Call<ShowPrevReportEmpResponse> call, Response<ShowPrevReportEmpResponse> response) {
                EmployeeActivity.dialog.dismiss();
                ShowPrevReportEmpResponse showPrevReportEmpResponse = response.body();
                if (showPrevReportEmpResponse.getCode() == 200) {
                    reportId = showPrevReportEmpResponse.getData().getRow().getId();
                    accepted = showPrevReportEmpResponse.getData().getRow().getIsAccepted();
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                    widgtV.setText(String.valueOf(userReportResponse.getData().getRow().getLandLines()));
//
//                    cityV.setText(String.valueOf(userReportResponse.getData().getRow().getCity().getName()));
//
//                    regionV.setText(String.valueOf(userReportResponse.getData().getRow().getRegion().getName()));
//
//                    realstateLimitsV.setText(String.valueOf(userReportResponse.getData().getRow().getBoundries()));
//
//                    realstateGeneralLocationV.setText(String.valueOf(userReportResponse.getData().getRow().getLocationTxt()));

                    if (showPrevReportEmpResponse.getData().getRow().getAttributes() != null && !showPrevReportEmpResponse.getData().getRow().getAttributes().isEmpty()) {
                        List<AttributesItem> attributesItem = showPrevReportEmpResponse.getData().getRow().getAttributes();
                        adapter = new CustomAttributedAdapter(attributesItem);
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        attributedV.setLayoutManager(lm);
                        attributedV.setHasFixedSize(true);
                        attributedV.setAdapter(adapter);
                    }


                    if (!showPrevReportEmpResponse.getData().getRow().getInfo().getRealEstate().getFiles().isEmpty() && showPrevReportEmpResponse.getData().getRow().getInfo().getRealEstate().getFiles() != null) {

                        for (int i = 0; i < showPrevReportEmpResponse.getData().getRow().getImages().size(); i++) {
                            String file = showPrevReportEmpResponse.getData().getRow().getImages().get(0).getFile();
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


//                    attributedtxt.setText(userReportResponse.getData().getRow().getAttributed());
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
//                    realStateTypeV.setText(String.valueOf(userReportResponse.getData().getRow().getClientType().getName()));
//
//                   realstateLocationV.setText(String.valueOf(userReportResponse.getData().getRow().getClientCity().getName()));
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
                        rateVersion = showPrevReportEmpResponse.getData().getRow().getRateFinalFile();
//                    rateUsesV.setText(String.valueOf(userReportResponse.getData().getRow().getRateFinalFileName()));
//                    infoSourcesV.setText(String.valueOf(userReportResponse.getData().getRow().getRateTerms()));
//                    notesV.setText(String.valueOf(userReportResponse.getData().getRow().getNotes()));
                        doc = showPrevReportEmpResponse.getData().getRow().getDocument();
                        docName = showPrevReportEmpResponse.getData().getRow().getDocumentName();
                        if (docName.equals("") || docName == null) {
                            txDocName.setText(R.string.show_file);
                        } else {
                            txDocName.setText(docName);
                        }
                        if (!accepted.isEmpty()) {
                            accept.setVisibility(View.GONE);
                            reject.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ShowPrevReportEmpResponse> call, Throwable t) {

            }
        });
    }


}