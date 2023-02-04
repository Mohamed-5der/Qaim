package com.Abdo.qaim.Retrofit;

import com.Abdo.qaim.Classes.AddComentsPreviewerParams;
import com.Abdo.qaim.Classes.AddListCommentsParams;
import com.Abdo.qaim.Classes.AddListEmployeeCommentsParams;
import com.Abdo.qaim.Classes.AddNotesAndReportsEmployeeParams;
import com.Abdo.qaim.Classes.AddNotesAndReportsParams;
import com.Abdo.qaim.Classes.AddNotesAndReportsPreviewerParams;
import com.Abdo.qaim.Classes.AppInfoParams;
import com.Abdo.qaim.Classes.AssignTeamParams;
import com.Abdo.qaim.Classes.CompanyRateParams;
import com.Abdo.qaim.Classes.CompanyRealstateIdParams;
import com.Abdo.qaim.Classes.CompanyUserRegisterParms;
import com.Abdo.qaim.Classes.InfoParams;
import com.Abdo.qaim.Classes.LoginParams;
import com.Abdo.qaim.Classes.OrderListItemParams;
import com.Abdo.qaim.Classes.PreviewerRateParams;
import com.Abdo.qaim.Classes.PreviewerRegisterParms;
import com.Abdo.qaim.Classes.RegionParams;
import com.Abdo.qaim.Classes.ResetPasswordParams;
import com.Abdo.qaim.Classes.SendOfferParams;
import com.Abdo.qaim.Classes.SendReportParams;
import com.Abdo.qaim.Classes.ShowRealstateUserParams;
import com.Abdo.qaim.Classes.TeamAddParams;
import com.Abdo.qaim.Classes.TeamParams;
import com.Abdo.qaim.Classes.TeamUpdateParams;
import com.Abdo.qaim.Classes.UpdateRealstateUserParams;
import com.Abdo.qaim.Classes.UserRegisterParms;
import com.Abdo.qaim.Models.AcceptedOrderUserResponse.AcceptedOrderUserResponse;
import com.Abdo.qaim.Models.AddCommentsPreviewer.MyListPrevCommentsAddResponse;
import com.Abdo.qaim.Models.AddListComments.AddCommentsListResponse;
import com.Abdo.qaim.Models.AddNotesCompany.AddNotesAndReportsCompanyResponse;
import com.Abdo.qaim.Models.AddTeamResponse.AddTeamResponse;
import com.Abdo.qaim.Models.AllCommentsResponse.AllCommentsResponse;
import com.Abdo.qaim.Models.AppInfo.AppInfoResponse;
import com.Abdo.qaim.Models.AprovedList.ApprovedListResponse;
import com.Abdo.qaim.Models.AssignTeam.AssignTeamResponse;
import com.Abdo.qaim.Models.CitiesResponse.CitiesResponse;
import com.Abdo.qaim.Models.CompanyFinishOrder.CompanyFinishOrderResponse;
import com.Abdo.qaim.Models.CompanyMakeReport.CompanyMakeReportResponse;
import com.Abdo.qaim.Models.CompanyPaymentsResponse;
import com.Abdo.qaim.Models.CompanyProfile.CompanyProfileResponse;
import com.Abdo.qaim.Models.CompanyRealstate.CompanyRealstateResponse;
import com.Abdo.qaim.Models.CompanyRegister.CompanyRegisterResponse;
import com.Abdo.qaim.Models.CompanyUserRegisterResponse.CompanyUserRegisterResponse;
import com.Abdo.qaim.Models.DeleteTeamResponse.DeleteTeamResponse;
import com.Abdo.qaim.Models.EmployeeAddComments.EmployeeAddCommentsResponse;
import com.Abdo.qaim.Models.EmployeeComments.EmployeeCommentsResponse;
import com.Abdo.qaim.Models.EmployeeProfile.EmpolyeeProfileResponse;
import com.Abdo.qaim.Models.EmpolyeeAddNotes.EmployeeAddNotesResponse;
import com.Abdo.qaim.Models.GetAcceptedOrderPrev.AcceptedOrderPrevResponse;
import com.Abdo.qaim.Models.GetBalance.GetBalanceResponse;
import com.Abdo.qaim.Models.GetOrdersPreviewer.OrderPrevResponse;
import com.Abdo.qaim.Models.GetPreviewerBalance.GetPreviewerBalanceResponse;
import com.Abdo.qaim.Models.GetProviewerProfile.PreviewerProfileResponse;
import com.Abdo.qaim.Models.GetRefusedOrders.RefusedOrderPrevResponse;
import com.Abdo.qaim.Models.LoginResponse.LoginResponse;
import com.Abdo.qaim.Models.MtListPreviewer.MyListPrevResponse;
import com.Abdo.qaim.Models.MyListCommentsPreviewer.MyListPrevCommentsResponse;
import com.Abdo.qaim.Models.MyListDetails.MyListDetailsResponse;
import com.Abdo.qaim.Models.MyListEmployee.MyListEmployeeResponse;
import com.Abdo.qaim.Models.MyListEmployeeDetails.MyListEmployeeDetailsResponse;
import com.Abdo.qaim.Models.MyListPrevDetails.MyListPrevDetailsResponse;
import com.Abdo.qaim.Models.MyListTeamReports.MyListTeamReportsResponse;
import com.Abdo.qaim.Models.MyRealstateCompanyList.MyRealstateCompanyResponse;
import com.Abdo.qaim.Models.Notification.NotiResponse;
import com.Abdo.qaim.Models.OrderListUserResponse.OrderListUserResponse;
import com.Abdo.qaim.Models.PreviewerRegisterResponse.PreviewerRegisterResponse;
import com.Abdo.qaim.Models.PreviwerPayments.PreviewerPaymentsResponse;
import com.Abdo.qaim.Models.PullBalance.PullBalanceResponse;
import com.Abdo.qaim.Models.PullPreviewerBalance.PullPreviewerBalanceResponse;
import com.Abdo.qaim.Models.RateResponse.RateResponse;
import com.Abdo.qaim.Models.RealstateDeleteUserResponse.RealstateDeleteUserResponse;
import com.Abdo.qaim.Models.RealstateShowUserResponse.RealstateShowUserResponse;
import com.Abdo.qaim.Models.RealstateStoreUserResponse.RealstateStoreUserResponse;
import com.Abdo.qaim.Models.RealstateUpdateUserResponse.RealstateUpdateUserResponse;
import com.Abdo.qaim.Models.RealsteatListUserResponse.RealstateListUserResponse;
import com.Abdo.qaim.Models.RefusedOrderUserResponse.RefusedOrderUserResponse;
import com.Abdo.qaim.Models.RegionsResponse.GetRegionResponse;
import com.Abdo.qaim.Models.ResetPassword.ResetPasswordResponse;
import com.Abdo.qaim.Models.SendOfferResponse.SendOfferResponse;
import com.Abdo.qaim.Models.ShowCompanyRealstate.ShowCompanyRealstateResponse;
import com.Abdo.qaim.Models.ShowOrderUserResponse.ShowOrderUserResponse;
import com.Abdo.qaim.Models.TeamList.GetTeamListResponse;
import com.Abdo.qaim.Models.TeamPreviewerList.TeamPreviwerListResponse;
import com.Abdo.qaim.Models.TeamSendInfo.TeamSendInfoResponse;
import com.Abdo.qaim.Models.TypesResponse.GetTypeResponse;
import com.Abdo.qaim.Models.UpdateCompanyProfile.UpdateCompanyProfileResponse;
import com.Abdo.qaim.Models.UpdateEmployeeProfile.UpdateEmpolyeeProfileResponse;
import com.Abdo.qaim.Models.UpdatePreviewerProfile.UpdatePreviewerProfileResponse;
import com.Abdo.qaim.Models.UpdateTeamResponse.UpdateTeamResponse;
import com.Abdo.qaim.Models.UploadFileResponse.UploadFileResponse;
import com.Abdo.qaim.Models.UserPaymentsResponse.UserPaymentsResponse;
import com.Abdo.qaim.Models.UserProfileResponse.UserProfileResponse;
import com.Abdo.qaim.Models.UserRegisterResponse.UserRegisterResponse;
import com.Abdo.qaim.Models.UserReportResponse.UserReportResponse;
import com.Abdo.qaim.Models.UserUpdateProfile.UserUpdateProfileResponse;

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

public interface API {

    //realstate
    @POST("/api/user/realestate-list")
    Call<RealstateListUserResponse> getAllRealstateList(@Header("Authorization") String token);
    @POST("/api/user/realestate-list-paid")
    Call<UserPaymentsResponse> userPayments(@Header("Authorization") String token);
    @POST("/api/user/realestate-report-rate")
    Call<RateResponse> companyRate(@Header("Authorization") String token , @Body CompanyRateParams params);
    @POST("/api/user/realestate-list-paid")
    Call<RateResponse> previewerRate(@Header("Authorization") String token , @Body PreviewerRateParams params);
    @POST("/api/user/realestate-show")
    Call<RealstateShowUserResponse> ShowRealstate(@Header("Authorization") String token , @Body ShowRealstateUserParams params);
    @POST("/api/user/realestate-delete")
    Call<RealstateDeleteUserResponse> deleteRealstate(@Header("Authorization") String token , @Body ShowRealstateUserParams params); // not work
    @Multipart
    @POST("/api/user/realestate-store")
    Call<RealstateStoreUserResponse> storeRealstate(
            @Header("Authorization") String token,
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part image);
    @POST("/api/user/realestate-update")
    Call<RealstateUpdateUserResponse> updateRealstate(@Header("Authorization") String token , @Body UpdateRealstateUserParams updateRealstateUserParams);

    // orders
    @POST("/api/user/requests-list")
    Call<OrderListUserResponse> getAllOrdersList(@Header("Authorization") String token);
    @POST("/api/user/requests-accept")
    Call<AcceptedOrderUserResponse> getAcceptedOrdersList(@Header("Authorization") String token , @Body OrderListItemParams params);
    @POST("/api/user/requests-refused")
    Call<RefusedOrderUserResponse> getRefusedOrders(@Header("Authorization") String token , @Body OrderListItemParams params);
    @POST("/api/user/requests-show")
    Call<ShowOrderUserResponse> showOrders(@Header("Authorization") String token , @Body OrderListItemParams params);
    @POST("/api/user/requests-show")
    Call<ShowOrderUserResponse> showApprovedOrders(@Header("Authorization") String token ,@Body ShowRealstateUserParams params);
    @POST("/api/user/realestate-list-approved")
    Call<ApprovedListResponse> aprovdedList(@Header("Authorization") String token);
    @POST("/api/user/realestate-report")
    Call<UserReportResponse> report(@Header("Authorization") String token , @Body ShowRealstateUserParams params);


    // offer
    @POST("/api/company/send-request")
    Call<SendOfferResponse> sendOffer(@Header("Authorization") String token , @Body SendOfferParams params);
    @POST("/api/company/assign-team")
    Call<AssignTeamResponse> assignTeam(@Header("Authorization") String token , @Body AssignTeamParams params);
    @POST("/api/company/finish-order")
    Call<CompanyFinishOrderResponse> finishOrder(@Header("Authorization") String token , @Body OrderListItemParams params);
    @POST("/api/company/request-add-info")
    Call<AddNotesAndReportsCompanyResponse> addNotesAndReports(@Header("Authorization") String token , @Body AddNotesAndReportsParams addNotesAndReportsParams);

    // team
    @POST("/api/company/team-add")
    Call<AddTeamResponse> addTeam(@Header("Authorization") String token , @Body TeamAddParams params);
    @POST("/api/company/team-update")
    Call<UpdateTeamResponse> updateTeam(@Header("Authorization") String token , @Body TeamUpdateParams params); // not work
    @POST("/api/company/team-delete")
    Call<DeleteTeamResponse> deleteTeam(@Header("Authorization") String token , @Body TeamParams params); // not work
    @POST("/api/company/team-send")
    Call<TeamSendInfoResponse> sendInfo(@Header("Authorization") String token , @Body TeamParams params);
    @POST("/api/company/team-previewers")
    Call<TeamPreviwerListResponse> getTeamPreviewersList(@Header("Authorization") String token);
    @POST("/api/company/team-list")
    Call<GetTeamListResponse> getTeamList(@Header("Authorization") String token);
    @POST("/api/company/get-balance")
    Call<GetBalanceResponse> getBalance(@Header("Authorization") String token);
    @POST("/api/company/pull-balance")
    Call<PullBalanceResponse> pullBalance(@Header("Authorization") String token);
    @POST("/api/company/make-report")
    Call<CompanyMakeReportResponse> makeReport(@Header("Authorization") String token , @Body SendReportParams params);
    @POST("/api/upload-file")
    Call<UploadFileResponse> uplodeFile(@Body String file);
    // company
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
    Call<ShowCompanyRealstateResponse> showCompanyRealstateList(@Header("Authorization") String token , @Body CompanyRealstateIdParams params);
    @POST("/api/company/my-list")
    Call<MyRealstateCompanyResponse> myRealstateCompanyList(@Header("Authorization") String token);
    @POST("/api/company/requests-show")
    Call<MyListDetailsResponse> myListDetails(@Header("Authorization") String token , @Body OrderListItemParams params); // not work
    @POST("/api/company/team-reports")
    Call<MyListTeamReportsResponse> myListTeamReports(@Header("Authorization") String token , @Body OrderListItemParams params); // not work
    @POST("/api/company/com-comments")
    Call<AllCommentsResponse> myListComments(@Header("Authorization") String token , @Body OrderListItemParams params);
    @POST("/api/company/com-comments-add")
    Call<AddCommentsListResponse> addMyListComments(@Header("Authorization") String token , @Body AddListCommentsParams params);
    @POST("/api/company/register")
    Call<CompanyRegisterResponse> registerCompany(@Body CompanyUserRegisterParms parms );

    // previewer
    @POST("/api/previewer/register")
    Call<PreviewerRegisterResponse> registerPreviewer(@Body PreviewerRegisterParms parms );
    @POST("/api/previewer/my-list-paid")
    Call<PreviewerPaymentsResponse> previwerPayments(@Header("Authorization") String token);
    @POST("/api/previewer/profile")
    Call<PreviewerProfileResponse> getPreviewerProfile(@Header("Authorization") String token);
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
    Call<MyListPrevCommentsAddResponse> myListCommentsAddPreviewer(@Header("Authorization") String token , @Body AddComentsPreviewerParams params);
    @POST("/api/previewer/get-balance")
    Call<GetPreviewerBalanceResponse> getBalancePreviewer(@Header("Authorization") String token);
    @POST("/api/previewer/pull-balance")
    Call<PullPreviewerBalanceResponse> pullBalancePreviewer(@Header("Authorization") String token);
    @POST("/api/previewer/make-report")
    Call<PullPreviewerBalanceResponse> makeReportPreviewer(); // not work
    @POST("/api/upload-file")
    Call<UploadFileResponse>uploadFilePreviewer(@Body String file);
    @POST("/api/previewer/request-add-info")
    Call<RefusedOrderPrevResponse> addNotesAndReportsOrderPreviewer(@Body AddNotesAndReportsPreviewerParams params);// not work
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
    @POST("/api/employee/request-add-info")
    Call<EmployeeAddNotesResponse> employeeAddNotesAndReports(@Header("Authorization") String token , @Body AddNotesAndReportsEmployeeParams params);



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
    @POST("/api/user/register")
    Call<CompanyUserRegisterResponse> registerCompanyUser(@Body CompanyUserRegisterParms parms );
    @POST("/v1/user/logout")
    Call<CitiesResponse> logOut(@Body int player_id); // not work

    // cities
    @POST("/api/cities")
    Call<CitiesResponse> getCities();
    @POST("/api/types")
    Call<GetTypeResponse> getTypes();
    @POST("/api/regions")
    Call<GetRegionResponse> getRegions(@Body RegionParams params);
    @POST("/api/app-info")
    Call<AppInfoResponse> getAppInfo(@Body AppInfoParams params); // data type
    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginParams params);
    @POST("/api/reset_pass")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordParams params);
    @POST("/api/notifications")
    Call<NotiResponse> notifications(@Header("Authorization") String token);

}
