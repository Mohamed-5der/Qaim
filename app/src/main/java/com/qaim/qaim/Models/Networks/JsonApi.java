package com.qaim.qaim.Models.Networks;

import com.qaim.qaim.Classes.AddComentsPreviewerParams;
import com.qaim.qaim.Classes.AddListCommentsParams;
import com.qaim.qaim.Classes.AddListEmployeeCommentsParams;
import com.qaim.qaim.Classes.AddNotesAndReportsParams;
import com.qaim.qaim.Classes.AddNotesAndReportsPreviewerParams;
import com.qaim.qaim.Classes.AssignTeamParams;
import com.qaim.qaim.Classes.AssignTeamParamsAll;
import com.qaim.qaim.Classes.AssignTeamParamsPrev;
import com.qaim.qaim.Classes.AssignTeamParamsRev;
import com.qaim.qaim.Classes.CompanyRateParams;
import com.qaim.qaim.Classes.CompanyRealstateIdParams;
import com.qaim.qaim.Classes.DeleteRealstateUserParams;
import com.qaim.qaim.Classes.FileIdParams;
import com.qaim.qaim.Classes.InfoParams;
import com.qaim.qaim.Classes.LoginParams;
import com.qaim.qaim.Classes.OTPParams;
import com.qaim.qaim.Classes.OrderListItemParams;
import com.qaim.qaim.Classes.PaymentParams;
import com.qaim.qaim.Classes.PrevListParams;
import com.qaim.qaim.Classes.PreviewerRateParams;
import com.qaim.qaim.Classes.RegionParams;
import com.qaim.qaim.Classes.ResetPasswordParams;
import com.qaim.qaim.Classes.ShowRealstateUserParams;
import com.qaim.qaim.Classes.StatusReportCompanyRejectParams;
import com.qaim.qaim.Classes.StatusReportParams;
import com.qaim.qaim.Classes.StoreUserRealstateParams;
import com.qaim.qaim.Classes.TeamAddParams;
import com.qaim.qaim.Classes.TeamParams;
import com.qaim.qaim.Classes.TeamUpdateParams;
import com.qaim.qaim.Classes.UserRegisterParms;
import com.qaim.qaim.Models.AcceptCompanyReport.AcceptCompanyReportResponse;
import com.qaim.qaim.Models.AcceptPrevReportEmp.AcceptPrevReportEmpResponse;
import com.qaim.qaim.Models.AcceptPreviewerReport.AcceptPreviewerReportResponse;
import com.qaim.qaim.Models.AcceptedOrderUserResponse.AcceptedOrderUserResponse;
import com.qaim.qaim.Models.AddCommentsPreviewer.MyListPrevCommentsAddResponse;
import com.qaim.qaim.Models.AddListComments.AddCommentsListResponse;
import com.qaim.qaim.Models.AddNotesCompany.AddNotesAndReportsCompanyResponse;
import com.qaim.qaim.Models.AddNotesPreviwer.PreviewerAddNotesResponse;
import com.qaim.qaim.Models.AddTeamResponse.AddTeamResponse;
import com.qaim.qaim.Models.AllCommentsResponse.AllCommentsResponse;
import com.qaim.qaim.Models.AppInfo.AppInfoResponse;
import com.qaim.qaim.Models.AprovedList.ApprovedListResponse;
import com.qaim.qaim.Models.AssignTeam.AssingTeamResponse;
import com.qaim.qaim.Models.CitiesResponse.CitiesResponse;
import com.qaim.qaim.Models.CompanyFinishOrder.CompanyFinishOrderResponse;
import com.qaim.qaim.Models.CompanyMakeReport.CompanyMakeReportResponse;
import com.qaim.qaim.Models.CompanyPaymentsResponse.CompanyPaymentsResponse;
import com.qaim.qaim.Models.CompanyProfile.CompanyProfileResponse;
import com.qaim.qaim.Models.CompanyRealstate.CompanyRealstateResponse;
import com.qaim.qaim.Models.CompanyRegister.CompanyRegisterResponse;
import com.qaim.qaim.Models.CompanyUserRegisterResponse.CompanyUserRegisterResponse;
import com.qaim.qaim.Models.Delete.DeleteFileResponse;
import com.qaim.qaim.Models.DeleteTeamResponse.DeleteTeamResponse;
import com.qaim.qaim.Models.EmployeeAddComments.EmployeeAddCommentsResponse;
import com.qaim.qaim.Models.EmployeeComments.EmployeeCommentsResponse;
import com.qaim.qaim.Models.EmployeeProfile.EmpolyeeProfileResponse;
import com.qaim.qaim.Models.EmpolyeeAddNotes.EmployeeAddNotesResponse;
import com.qaim.qaim.Models.GetAcceptedOrderPrev.AcceptedOrderPrevResponse;
import com.qaim.qaim.Models.GetBalance.GetBalanceResponse;
import com.qaim.qaim.Models.GetOrdersPreviewer.OrderPrevResponse;
import com.qaim.qaim.Models.GetPreviewerBalance.GetPreviewerBalanceResponse;
import com.qaim.qaim.Models.GetProviewerProfile.PreviewerProfileResponse;
import com.qaim.qaim.Models.GetRefusedOrders.RefusedOrderPrevResponse;
import com.qaim.qaim.Models.LoginResponse.LoginResponse;
import com.qaim.qaim.Models.LogoutRespone.LogOutResponse;
import com.qaim.qaim.Models.MtListPreviewer.MyListPrevResponse;
import com.qaim.qaim.Models.MyListCommentsPreviewer.MyListPrevCommentsResponse;
import com.qaim.qaim.Models.MyListDetails.MyListDetailsResponse;
import com.qaim.qaim.Models.MyListEmployee.MyListEmployeeResponse;
import com.qaim.qaim.Models.MyListEmployeeDetails.MyListEmployeeDetailsResponse;
import com.qaim.qaim.Models.MyListPrevDetails.MyListPrevDetailsResponse;
import com.qaim.qaim.Models.MyListTeamReports.MyListTeamReportResponse;
import com.qaim.qaim.Models.MyRealstateCompanyList.MyRealstateCompanyResponse;
import com.qaim.qaim.Models.Notification.NotiResponse;
import com.qaim.qaim.Models.OrderListUserResponse.OrderListUserResponse;
import com.qaim.qaim.Models.OtpResponse.OtpResponse;
import com.qaim.qaim.Models.PreviewerMakeReport.PreviewerMakeReportResponse;
import com.qaim.qaim.Models.PreviewerRegisterResponse.PreviewerRegisterResponse;
import com.qaim.qaim.Models.PreviwerPayments.PreviewerPaymentsResponse;
import com.qaim.qaim.Models.PullBalance.PullBalanceResponse;
import com.qaim.qaim.Models.PullPreviewerBalance.PullPreviewerBalanceResponse;
import com.qaim.qaim.Models.RateResponse.RateResponse;
import com.qaim.qaim.Models.RealstateDeleteUserResponse.RealstateDeleteUserResponse;
import com.qaim.qaim.Models.RealstateShowUserResponse.RealstateShowUserResponse;
import com.qaim.qaim.Models.RealstateStoreUserResponse.RealstateStoreUserResponse;
import com.qaim.qaim.Models.RealstateUpdateUserResponse.RealstateUpdateUserResponse;
import com.qaim.qaim.Models.RealsteatListUserResponse.RealstateListUserResponse;
import com.qaim.qaim.Models.RefusedCompanyReport.RefusedCompanyReportResponse;
import com.qaim.qaim.Models.RefusedOrderUserResponse.RefusedOrderUserResponse;
import com.qaim.qaim.Models.RefusedPrevReport.RefusePrevReportEmpResponse;
import com.qaim.qaim.Models.RefusedPreviewerReport.RefusedPreviewerReportResponse;
import com.qaim.qaim.Models.RegionsResponse.GetRegionResponse;
import com.qaim.qaim.Models.ReportCompleted.ReportCompletedtResponse;
import com.qaim.qaim.Models.ReportCompleted.sendFeedBack.FeedBackResponse;
import com.qaim.qaim.Models.ReportCompleted.sendFeedBack.FeedbackRequest;
import com.qaim.qaim.Models.ResetPassword.ResetPasswordResponse;
import com.qaim.qaim.Models.SendOfferResponse.SendOfferResponse;
import com.qaim.qaim.Models.ShowCompanyPrevReport.ShowCompanyPrevReportResponse;
import com.qaim.qaim.Models.ShowCompanyRealstate.ShowCompanyRealstateResponse;
import com.qaim.qaim.Models.ShowCompanyReport.ShowCompanyReportResponse;
import com.qaim.qaim.Models.ShowOrderUserResponse.ShowOrderUserResponse;
import com.qaim.qaim.Models.ShowPrevRealstate.ShowPrevRealstateResponse;
import com.qaim.qaim.Models.ShowPrevReportEmp.ShowPrevReportEmpResponse;
import com.qaim.qaim.Models.TeamList.GetTeamListResponse;
import com.qaim.qaim.Models.TeamPreviewerList.TeamPreviewerListResponse;
import com.qaim.qaim.Models.TeamSendInfo.TeamSendInfoResponse;
import com.qaim.qaim.Models.TypesResponse.GetTypeResponse;
import com.qaim.qaim.Models.UpdateCompanyProfile.UpdateCompanyProfileResponse;
import com.qaim.qaim.Models.UpdateEmployeeProfile.UpdateEmpolyeeProfileResponse;
import com.qaim.qaim.Models.UpdatePreviewerProfile.UpdatePreviewerProfileResponse;
import com.qaim.qaim.Models.UpdateTeamResponse.UpdateTeamResponse;
import com.qaim.qaim.Models.UploadFileResponse.UploadFileResponse;
import com.qaim.qaim.Models.UserCompaniesResponse.UserCompaniesResponse;
import com.qaim.qaim.Models.UserPaymentsResponse.UserPaymentsResponse;
import com.qaim.qaim.Models.UserProfileResponse.UserProfileResponse;
import com.qaim.qaim.Models.UserRegisterResponse.UserRegisterResponse;
import com.qaim.qaim.Models.UserReportResponse.UserReportResponse;
import com.qaim.qaim.Models.UserUpdateProfile.UserUpdateProfileResponse;
import com.qaim.qaim.Models.vip.VipRequestResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface  JsonApi {
    //realstate
    @POST("/api/user/realestate-list")
    Call<RealstateListUserResponse> getAllRealstateList(@Header("Authorization") String token);
    @POST("/api/user/realestate-list-paid")
    Call<UserPaymentsResponse> userPayments(@Header("Authorization") String token);
    @POST("/api/user/realestate-report-markas-completed")
    Call<ReportCompletedtResponse> completeProject(@Header("Authorization") String token , @Body ShowRealstateUserParams params);
    @POST("/api/user/realestate-report-rate")
    Call<RateResponse> companyRate(@Header("Authorization") String token , @Body CompanyRateParams params);
    @POST("/api/user/realestate-report-rate")
    Call<RateResponse> previewerRate(@Header("Authorization") String token , @Body PreviewerRateParams params);
    @POST("/api/user/realestate-show")
    Call<RealstateShowUserResponse> ShowRealstate(@Header("Authorization") String token , @Body ShowRealstateUserParams params);
    @POST("/api/user/realestate-delete")
    Call<RealstateDeleteUserResponse> deleteRealstate(@Header("Authorization") String token , @Body DeleteRealstateUserParams params); // not work
    @Multipart
    @POST("/api/user/realestate-store")
    Call<RealstateStoreUserResponse> storeRealstate(
            @Header("Authorization") String token,
            @PartMap() Map<String, RequestBody> partMap,
            @Part List<MultipartBody.Part> files);




@POST("/api/user/realestate-store")
Call<RealstateStoreUserResponse> storeRealstate(@Header("Authorization") String token,@Body StoreUserRealstateParams storeUserRealstateParams);
@Multipart
@POST("/api/user/realestate-update")
    Call<RealstateUpdateUserResponse> updateRealstate(
            @Header("Authorization") String token,
            @PartMap() Map<String, RequestBody> partMap,
            @Part List<MultipartBody.Part> files);

    // orders
    @POST("/api/user/requests-list")
    Call<OrderListUserResponse> getAllOrdersList(@Header("Authorization") String token);
    @POST("/api/user/requests-accept")
    Call<AcceptedOrderUserResponse> getAcceptedOrdersList(@Header("Authorization") String token ,@Body PaymentParams params);
    @POST("/api/user/requests-refused")
    Call<RefusedOrderUserResponse> getRefusedOrders(@Header("Authorization") String token ,@Body OrderListItemParams params);
    @POST("/api/user/requests-show")
    Call<ShowOrderUserResponse> showOrders(@Header("Authorization") String token ,@Body OrderListItemParams params);
    @POST("/api/user/requests-show")
    Call<ShowOrderUserResponse> showApprovedOrders(@Header("Authorization") String token ,@Body ShowRealstateUserParams params);
    @POST("/api/user/realestate-list-approved")
    Call<ApprovedListResponse> aprovdedList(@Header("Authorization") String token);
    @POST("/api/user/realestate-report")
    Call<UserReportResponse> report(@Header("Authorization") String token , @Body ShowRealstateUserParams params);


    // offer
    @Multipart
    @POST("/api/company/send-request")
    Call<SendOfferResponse> sendOffer(@Header("Authorization") String token  , @PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part document);
    @POST("/api/company/assign-team")
    Call<AssingTeamResponse> assignTeam(@Header("Authorization") String token , @Body AssignTeamParamsAll params);
    @POST("/api/company/assign-team")
    Call<AssingTeamResponse> assignTeam(@Header("Authorization") String token , @Body AssignTeamParams params);
    @POST("/api/company/assign-team")
    Call<AssingTeamResponse> assignTeam(@Header("Authorization") String token , @Body AssignTeamParamsPrev params);
    @POST("/api/company/assign-team")
    Call<AssingTeamResponse> assignTeam(@Header("Authorization") String token , @Body AssignTeamParamsRev params);
    @POST("/api/company/finish-order")
    Call<CompanyFinishOrderResponse> finishOrder(@Header("Authorization") String token ,@Body OrderListItemParams params);
    @POST("/api/company/request-add-info")
    Call<AddNotesAndReportsCompanyResponse> addNotesAndReports(@Header("Authorization") String token ,@Body AddNotesAndReportsParams addNotesAndReportsParams);

    // team
    @POST("/api/company/team-add")
    Call<AddTeamResponse> addTeam(@Header("Authorization") String token ,@Body TeamAddParams params);
    @POST("/api/company/team-update")
    Call<UpdateTeamResponse> updateTeam(@Header("Authorization") String token , @Body TeamUpdateParams params); // not work
    @POST("/api/company/team-delete")
    Call<DeleteTeamResponse> deleteTeam(@Header("Authorization") String token , @Body TeamParams params); // not work
    @POST("/api/company/team-send")
    Call<TeamSendInfoResponse> sendInfo(@Header("Authorization") String token ,@Body TeamParams params);
    @POST("/api/company/team-previewers")
    Call<TeamPreviewerListResponse> getTeamPreviewersList(@Header("Authorization") String token ,@Body PrevListParams params);
    @POST("/api/company/team-list")
    Call<GetTeamListResponse> getTeamList(@Header("Authorization") String token);
    @POST("/api/company/get-balance")
    Call<GetBalanceResponse> getBalance(@Header("Authorization") String token);
    @POST("/api/company/pull-balance")
    Call<PullBalanceResponse> pullBalance(@Header("Authorization") String token);
    @Multipart
    @POST("/api/company/make-report")
    Call<CompanyMakeReportResponse> makeReport(@Header("Authorization") String token, @PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part document , @Part MultipartBody.Part finalDocument , @Part List<MultipartBody.Part> files);

    @POST("/api/upload-file")
    Call<UploadFileResponse> uplodeFile(@Body String file);
    // company


    @POST("/api/previewer/company-report-view")
    Call<ShowCompanyReportResponse> showCompanyReport(@Header("Authorization") String token , @Body OrderListItemParams params);
    @POST("/api/previewer/company-report-accept")
    Call<AcceptCompanyReportResponse> acceptCompanyReport(@Header("Authorization") String token  , @Body StatusReportParams params );
    @POST("/api/previewer/company-report-refuse")
    Call<RefusedCompanyReportResponse> rejectCompanyReport(@Header("Authorization") String token  , @Body StatusReportParams params );


    @POST("/api/company/profile")
    Call<CompanyProfileResponse> getCompanyProfile(@Header("Authorization") String token);
    @POST("/api/company/my-list-paid")
    Call<CompanyPaymentsResponse> companyPayments(@Header("Authorization") String token);
    @Multipart
    @POST("/api/company/update")
    Call<UpdateCompanyProfileResponse> updateCompanyProfile(
            @Header("Authorization") String token,
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part image);
    @POST("/api/company/realestate-list")
    Call<CompanyRealstateResponse> getAllCompanyRealstateList(@Header("Authorization") String token );
    @POST("/api/company/realestate-show")
    Call<ShowCompanyRealstateResponse> showCompanyRealstateList(@Header("Authorization") String token ,@Body CompanyRealstateIdParams params);
    @POST("/api/company/my-list")
    Call<MyRealstateCompanyResponse> myRealstateCompanyList(@Header("Authorization") String token);
    @POST("/api/company/requests-show")
    Call<MyListDetailsResponse> myListDetails(@Header("Authorization") String token , @Body OrderListItemParams params); // not work
    @POST("/api/company/team-reports")
    Call<MyListTeamReportResponse> myListTeamReports(@Header("Authorization") String token , @Body OrderListItemParams params); // not work
    @POST("/api/company/com-comments")
    Call<AllCommentsResponse> myListComments(@Header("Authorization") String token , @Body OrderListItemParams params);
    @POST("/api/company/com-comments-add")
    Call<AddCommentsListResponse> addMyListComments(@Header("Authorization") String token ,@Body AddListCommentsParams params);
    @Multipart
    @POST("/api/company/com-comments-add")
    Call<AddCommentsListResponse> addMyListComments(@Header("Authorization") String token , @PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part file);
    @Multipart
    @POST("/api/company/register")
    Call<CompanyRegisterResponse> registerCompany(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part image);

    // previewer
    @POST("/api/company/previewer-report-view")
    Call<ShowCompanyPrevReportResponse> showPreviewerReport(@Header("Authorization") String token  , @Body OrderListItemParams params );
    @POST("/api/company/previewer-report-accept")
    Call<AcceptPreviewerReportResponse> acceptPreviewerReport(@Header("Authorization") String token  , @Body StatusReportParams params );
    @POST("/api/company/previewer-report-refuse")
    Call<RefusedPreviewerReportResponse> rejectPreviewerReport(@Header("Authorization") String token  , @Body StatusReportCompanyRejectParams params );
    @Multipart
    @POST("/api/previewer/register")
    Call<PreviewerRegisterResponse> registerPreviewer(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part image);
    @POST("/api/previewer/my-list-paid")
    Call<PreviewerPaymentsResponse> previwerPayments(@Header("Authorization") String token);
    @POST("/api/previewer/profile")
    Call<PreviewerProfileResponse> getPreviewerProfile(@Header("Authorization") String token);
    @POST("/api/previewer/show-realestate")
    Call<ShowPrevRealstateResponse> showPreviewerRealstate(@Header("Authorization") String token , @Body OrderListItemParams params );
    @Multipart
    @POST("/api/previewer/request-add-info")
    Call<PreviewerAddNotesResponse> addNotes(@Header("Authorization") String token  , @PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part document);

    @Multipart
    @POST("/api/previewer/update")
    Call<UpdatePreviewerProfileResponse> updatePreviewerProfile(
                                                                @Header("Authorization") String token,
                                                                @PartMap() Map<String, RequestBody> partMap,
                                                                @Part MultipartBody.Part image);
    @POST("/api/previewer/show-list")
    Call<MyListPrevDetailsResponse> myListPreviewerDetails(@Header("Authorization") String token , @Body OrderListItemParams params);  // not work
    @POST("/api/previewer/my-list")
    Call<MyListPrevResponse> myListPreviewer(@Header("Authorization") String token);
    @POST("/api/previewer/pre-comments")
    Call<MyListPrevCommentsResponse> myListCommentsPreviewer(@Header("Authorization") String token , @Body InfoParams params);
    @POST("/api/previewer/pre-comments-add")
    Call<MyListPrevCommentsAddResponse> myListCommentsAddPreviewer(@Header("Authorization") String token ,@Body AddComentsPreviewerParams params);
    @Multipart
    @POST("/api/previewer/pre-comments-add")
    Call<MyListPrevCommentsAddResponse> myListCommentsAddPreviewer(@Header("Authorization") String token , @PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part file);
    @POST("/api/previewer/get-balance")
    Call<GetPreviewerBalanceResponse> getBalancePreviewer(@Header("Authorization") String token);
    @POST("/api/previewer/pull-balance")
    Call<PullPreviewerBalanceResponse> pullBalancePreviewer(@Header("Authorization") String token);
    @Multipart
    @POST("/api/previewer/make-report")
    Call<PreviewerMakeReportResponse> makeReportPreviewer(@Header("Authorization") String token , @PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part document , @Part List<MultipartBody.Part> files);

//    @Body SendReportParams params);
    @POST("/api/upload-file")
    Call<UploadFileResponse>uploadFilePreviewer(@Body String file);
    @POST("/api/previewer/request-add-info")
    Call<PreviewerAddNotesResponse> addNotesAndReportsOrderPreviewer(@Header("Authorization") String token ,@Body AddNotesAndReportsPreviewerParams params);// not work
    @POST("/api/previewer/orders")
    Call<OrderPrevResponse> getOrdersPreviewer(@Header("Authorization") String token);
    @POST("/api/previewer/accept-order")
    Call<AcceptedOrderPrevResponse> accedptedOrdersPreviewer(@Header("Authorization") String token , @Body OrderListItemParams params);// not work
    @POST("/api/previewer/refused-order")
    Call<RefusedOrderPrevResponse> refusedOrdersPreviewer(@Header("Authorization") String token , @Body OrderListItemParams params);// not work


    // painter AND reviewer
    @POST("/api/employee/profile")
    Call<EmpolyeeProfileResponse> getEmployeeProfile(@Header("Authorization") String token);
    @Multipart
    @POST("/api/employee/update")
    Call<UpdateEmpolyeeProfileResponse> updateEmployeeProfile(
                                                              @Header("Authorization") String token,
                                                              @PartMap() Map<String, RequestBody> partMap,
                                                              @Part MultipartBody.Part image);
    @POST("/api/employee/show-list")
    Call<MyListEmployeeDetailsResponse> myListEmployeeDetails(@Header("Authorization") String token , @Body OrderListItemParams params); // not work
    @POST("/api/employee/my-list")
    Call<MyListEmployeeResponse> myListEmployee(@Header("Authorization") String token);
    @POST("/api/employee/emp-comments")
    Call<EmployeeCommentsResponse> myListEmployeeComments(@Header("Authorization") String token , @Body InfoParams params);
    @POST("/api/employee/emp-comments-add")
    Call<EmployeeAddCommentsResponse> myListEmployeeAddComments(@Header("Authorization") String token , @Body AddListEmployeeCommentsParams params);
    @Multipart
    @POST("/api/employee/emp-comments-add")
    Call<EmployeeAddCommentsResponse> myListEmployeeAddComments(@Header("Authorization") String token , @PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part file);
    @Multipart
    @POST("/api/employee/request-add-info")
    Call<EmployeeAddNotesResponse> employeeAddNotesAndReports(@Header("Authorization") String token ,@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part document);
    @POST("/api/employee/previewer-report-view")
    Call<ShowPrevReportEmpResponse> showPreviewerEmpReport(@Header("Authorization") String token  , @Body OrderListItemParams params );
    @POST("/api/employee/previewer-report-accept")
    Call<AcceptPrevReportEmpResponse> acceptPreviewerEmpReport(@Header("Authorization") String token  , @Body StatusReportParams params );
    @POST("/api/employee/previewer-report-refuse")
    Call<RefusePrevReportEmpResponse> rejectPreviewerEmpReport(@Header("Authorization") String token  , @Body StatusReportParams params );


    // profile
    @POST("/api/user/profile")
    Call<UserProfileResponse> getUserProfile(@Header("Authorization") String token);
    @Multipart
    @POST("/api/user/update")
    Call<UserUpdateProfileResponse> updateUserProfile(
            @Header("Authorization") String token,
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part image);
    @POST("/api/user/register")
    Call<UserRegisterResponse> registerUser(@Body UserRegisterParms parms);

    @Multipart
    @POST("/api/user/register")
    Call<CompanyUserRegisterResponse> registerCompanyUser(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part image );

    @POST("/api/v1/logout")
    Call<LogOutResponse> logOut(@Header("Authorization") String token , @Body String player_id);

    // cities
    @POST("/api/cities")
    Call<CitiesResponse> getCities();
    @POST("/api/delete-file")
    Call<DeleteFileResponse> deleteFile(@Body FileIdParams parms);
    @POST("/api/types")
    Call<GetTypeResponse> getTypes();
    @POST("/api/regions")
    Call<GetRegionResponse> getRegions(@Body RegionParams params);
    @POST("/api/app-info")
    Call<AppInfoResponse> getAppInfo();
    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginParams params);
    @POST("/api/reset_pass")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordParams params);
    @POST("/api/notifications")
    Call<NotiResponse> notifications(@Header("Authorization") String token);

    @POST("/api/user/verify-account")
    Call<OtpResponse> verifyUser(@Header("Authorization") String token, @Body OTPParams params);
    @POST("/api/company/verify-account")
    Call<OtpResponse> verifyCompany(@Header("Authorization") String token, @Body OTPParams params);
    @POST("/api/previewer/verify-account")
    Call<OtpResponse> verifyPreviewer(@Header("Authorization") String token, @Body OTPParams params);
    @POST("/api/send-feedback")
    Call<FeedBackResponse> sendFeedBack(@Header("Authorization") String token, @Body FeedbackRequest feedbackRequest);
    @POST("/api/company/request-vip")
    Call<VipRequestResponse> sendVipRequest(@Header("Authorization") String token);
    @POST("/api/user/vip-companies")
    Call<UserCompaniesResponse> userCompanies(@Header("Authorization") String token);
}
