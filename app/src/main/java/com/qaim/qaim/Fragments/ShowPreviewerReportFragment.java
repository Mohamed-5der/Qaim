package com.qaim.qaim.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
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
import com.qaim.qaim.Activities.CompanyActivity;
import com.qaim.qaim.Classes.CustomAttributedAdapterReport;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Classes.StatusReportCompanyRejectParams;
import com.qaim.qaim.Classes.StatusReportParams;
import com.qaim.qaim.Models.AcceptPreviewerReport.AcceptPreviewerReportResponse;
import com.qaim.qaim.Models.Networks.JsonApi;
import com.qaim.qaim.Models.RefusedPreviewerReport.RefusedPreviewerReportResponse;
import com.qaim.qaim.Models.ShowCompanyPrevReport.AttributesItem;
import com.qaim.qaim.Models.ShowCompanyPrevReport.ShowCompanyPrevReportResponse;
import com.qaim.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ShowPreviewerReportFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private int id ;
    int reportId ;


    Retrofit retrofit;
    JsonApi jsonApi;
    CustomAttributedAdapterReport adapter;

    String widgt ,city , region ,realstateLimits ,realstateGeneralLocation ,attributed , service , nearstStreat ,howFasrRealstateRating ,
            howFarRealstateCenterCity , Views , thePartyDirected , rateUser ,ratePurpose ,realStateType ,realstateLocation , ownerShipType ,
            rate ,valueBasis ,rateDate ,previweDate ,usedMethods ,methodsAndWays , valueHypothesis , applicableEvaluationCriteria , ndependentConflictOfInterest ,
            referanceNumber , rateType ,rateDate1 ,rateVersion , rateUses ,infoSources , notes , doc , docName ;

//
//    TextView widgtV ,cityV , regionV ,realstateLimitsV ,realstateGeneralLocationV  , serviceV , nearstStreatV ,howFasrRealstateRatingV ,
//            howFarRealstateCenterCityV , ViewsV , thePartyDirectedV , rateUserV ,ratePurposeV ,realStateTypeV ,realstateLocationV , ownerShipTypeV ,
//            rateV ,valueBasisV ,rateDateV ,previweDateV ,usedMethodsV ,methodsAndWaysV , valueHypothesisV , applicableEvaluationCriteriaV , ndependentConflictOfInterestV ,
//            referanceNumberV , rateTypeV ,rateDate1V, attributedtxt  , rateUsesV ,infoSourcesV , notesV ;

    Button accept , reject ;
    RelativeLayout rateVersionV;
    String accepted ;
    RecyclerView attributedV ;
    TextView tvDocName ;
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
        tvDocName = v.findViewById(R.id.tvDocName);
        imageSlider = v.findViewById(R.id.image_slider);
        arrayList = new ArrayList<>();

    }



    public ShowPreviewerReportFragment() {
        // Required empty public constructor
    }

    public static ShowPreviewerReportFragment newInstance(int id) {
        ShowPreviewerReportFragment fragment = new ShowPreviewerReportFragment();
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
        View v = inflater.inflate(R.layout.fragment_show_previewer_report, container, false);
        ImageButton imageButton = v.findViewById(R.id.imageBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyTeamReportsFragment chooseTeamFragment = CompanyTeamReportsFragment.newInstance(id);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout , chooseTeamFragment).commit();
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
                .baseUrl("https://qaimha.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        inflateViews(v);
        callReportAPI();
        return v;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompanyActivity.dialog.show();
                Call<AcceptPreviewerReportResponse> acceptPreviewerReportResponseCall = jsonApi.acceptPreviewerReport("Bearer " + CompanyActivity.token , new StatusReportParams(reportId));
                acceptPreviewerReportResponseCall.enqueue(new Callback<AcceptPreviewerReportResponse>() {
                    @Override
                    public void onResponse(Call<AcceptPreviewerReportResponse> call, Response<AcceptPreviewerReportResponse> response) {

                        CompanyActivity.dialog.dismiss();
                        AcceptPreviewerReportResponse reportResponse = response.body();
                        if (reportResponse.getCode() == 200){
                            getActivity().recreate();
                            Toast.makeText(getActivity(),reportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(),reportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AcceptPreviewerReportResponse> call, Throwable t) {
                        Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an EditText to enter the rejection reason
                final EditText reasonInput = new EditText(view.getContext());

                // Create a dialog to show the EditText
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.rejection_reason);
                builder.setMessage(R.string.enter_rejection_reason);

                // Add the EditText to the dialog
                builder.setView(reasonInput);

                // Add action buttons
                builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the entered text from the EditText
                        String rejectionReason = reasonInput.getText().toString().trim();

                        // Handle the rejection reason
                        if (!rejectionReason.isEmpty()) {
                            // Do something with the rejection reason
                            // For example, save it or display it in a TextView
                            rejectReport(rejectionReason);
                        } else {
                            // Handle case where no input was provided
                            Toast.makeText(view.getContext(), getString(R.string.plz_enter_rejection_reason), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel(); // Close the dialog
                    }
                });

                // Show the dialog
                builder.show();
            }
        });
        rateVersionV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rateVersion));
                startActivity(browserIntent);
            }
        });
    }

    public void rejectReport(String reason) {
        CompanyActivity.dialog.show();
        Call<RefusedPreviewerReportResponse> acceptPreviewerReportResponseCall = jsonApi.rejectPreviewerReport("Bearer " + CompanyActivity.token , new StatusReportCompanyRejectParams(reportId, reason));
        acceptPreviewerReportResponseCall.enqueue(new Callback<RefusedPreviewerReportResponse>() {
            @Override
            public void onResponse(Call<RefusedPreviewerReportResponse> call, Response<RefusedPreviewerReportResponse> response) {

                CompanyActivity.dialog.dismiss();
                RefusedPreviewerReportResponse reportResponse = response.body();
                if (reportResponse.getCode() == 200){
                    getActivity().recreate();
                    Toast.makeText(getActivity(),reportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),reportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RefusedPreviewerReportResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void callReportAPI(){
        CompanyActivity.dialog.show();
        Call<ShowCompanyPrevReportResponse> call = jsonApi.showPreviewerReport("Bearer " + CompanyActivity.token , new OrderListItemParams(id));
        call.enqueue(new Callback<ShowCompanyPrevReportResponse>() {
            @Override
            public void onResponse(Call<ShowCompanyPrevReportResponse> call, Response<ShowCompanyPrevReportResponse> response) {
                CompanyActivity.dialog.dismiss();
                ShowCompanyPrevReportResponse userReportResponse = response.body();
                if (userReportResponse.getCode() == 200 && userReportResponse.getData().getRow() != null) {

//                    userReportResponse.getData().getRow().getAttributed();
                    reportId = userReportResponse.getData().getRow().getId();
                    accepted = userReportResponse.getData().getRow().getIsAccepted();
//
//                    widgtV.setText(String.valueOf(userReportResponse.getData().getRow().getLandLines()));
//
//                    cityV.setText(String.valueOf(userReportResponse.getData().getRow().getCity().getName()));
//
//                    regionV.setText(String.valueOf(userReportResponse.getData().getRow().getRegion().getName()));
//
//                    realstateLimitsV.setText(String.valueOf(userReportResponse.getData().getRow().getBoundries()));

//                    realstateGeneralLocationV.setText(String.valueOf(userReportResponse.getData().getRow().getLocationTxt()));
                    if (userReportResponse.getData().getRow().getAttributes() != null && !userReportResponse.getData().getRow().getAttributes().isEmpty()) {
                        List<AttributesItem> attributesItem = userReportResponse.getData().getRow().getAttributes();
                        adapter = new CustomAttributedAdapterReport(attributesItem);
                        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext());
                        attributedV.setLayoutManager(lm);
                        attributedV.setHasFixedSize(true);
                        attributedV.setAdapter(adapter);
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


//                    attributedtxt.setText(userReportResponse.getData().getRow().getAttributed());

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
                        rateVersion = userReportResponse.getData().getRow().getRateFinalFile();
//                    rateUsesV.setText(String.valueOf(userReportResponse.getData().getRow().getRateFinalFileName()));
//                    infoSourcesV.setText(String.valueOf(userReportResponse.getData().getRow().getRateTerms()));
//                    notesV.setText(String.valueOf(userReportResponse.getData().getRow().getNotes()));
                        doc = userReportResponse.getData().getRow().getDocument();
                        docName = userReportResponse.getData().getRow().getDocumentName();
                        tvDocName.setText(docName);
                        if (docName.equals("") || docName == null) {
                            tvDocName.setText("عرض الملف");
                        } else {
                            tvDocName.setText(docName);
                        }
                        if (!accepted.isEmpty()) {
                            accept.setVisibility(View.GONE);
                            reject.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        Toast.makeText(getContext(), userReportResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ShowCompanyPrevReportResponse> call, Throwable t) {

            }
        });
    }

}