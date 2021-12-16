package com.sgnr.sgnrclasses.Api;

import com.sgnr.sgnrclasses.Model.CourseModelResponse;
import com.sgnr.sgnrclasses.Model.DemoModelResponse;
import com.sgnr.sgnrclasses.Model.LoginDetailsModel;
import com.sgnr.sgnrclasses.Model.McqModelResponse;
import com.sgnr.sgnrclasses.Model.PaidModelResponse;
import com.sgnr.sgnrclasses.Model.ProfileResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("demoLessons.php")
    Call<DemoModelResponse> getResponse();

    @GET("courses.php")
    Call<CourseModelResponse> getPaidCourses();

    @FormUrlEncoded
    @POST("paidlessons.php")
    Call<PaidModelResponse> getPaidCoursesDetails(@Field("course_Name") String course_Name);


    @FormUrlEncoded
    @POST("RegisterUser.php")
    Call<LoginDetailsModel> registerUser(@Field("UserName") String username,
                                         @Field("UserEmail") String email,
                                         @Field("UserMobile") String mobile_no,
                                         @Field("UserPassword") String password);

    @FormUrlEncoded
    @POST("LoginUser.php")
    Call<LoginDetailsModel> loginUser(@Field("Username") String username,
                                      @Field("Userpassword") String password);


    @FormUrlEncoded
    @POST("Profile.php")
    Call<ProfileResponseModel> getUserDetails(@Field("UserName") String username);

    @FormUrlEncoded
    @POST("Feedback.php")
    Call<LoginDetailsModel> sendFeedback(@Field("UserName") String username,
                                            @Field("Message") String message);

    @FormUrlEncoded
    @POST("MyCourses.php")
    Call<CourseModelResponse> getMyCourses(@Field("userName") String username);

    @FormUrlEncoded
    @POST("EditProfile.php")
    Call<LoginDetailsModel> editUserProfile(@Field("UserEmail") String email,
                                         @Field("UserMobile") String mobile_no,
                                         @Field("UserID") int id);

    @FormUrlEncoded
    @POST("checkUserPaymentStatus.php")
    Call<LoginDetailsModel> checkPaymentStatus(@Field("UserName") String username,
                                                  @Field("CourseName") String course);

    @FormUrlEncoded
    @POST("SendPayment.php")
    Call<LoginDetailsModel> sendPayment(@Field("UserName") String username,
                                           @Field("CourseName") String course,
                                           @Field("UserMobile") String mobile,
                                           @Field("PaymentStatus") String paymentStatus,
                                           @Field("Amount") String amount
                                           );

    @FormUrlEncoded
    @POST("mcq.php")
    Call<McqModelResponse> getMcqQuestion(@Field("CourseName") String course_Name);


    @FormUrlEncoded
    @POST("mcqSell.php")
    Call<LoginDetailsModel> sendMcqPayment(@Field("Student_Name") String username,
                                        @Field("Course_Name") String course,
                                        @Field("Student_Email") String email,
                                        @Field("Amount") String amount
    );

    @FormUrlEncoded
    @POST("mcqPaymentStatus.php")
    Call<LoginDetailsModel> checkMcqPaymentStatus(@Field("UserName") String username,
                                                  @Field("CourseName") String course);
}
