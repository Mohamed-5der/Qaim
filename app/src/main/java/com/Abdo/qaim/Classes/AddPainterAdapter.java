package com.Abdo.qaim.Classes;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.Abdo.qaim.Activities.CompanyActivity;
import com.Abdo.qaim.Fragments.CompanySetTeamFragment;
import com.Abdo.qaim.Models.DeleteTeamResponse.DeleteTeamResponse;
import com.Abdo.qaim.Models.Networks.JsonApi;
import com.Abdo.qaim.Models.TeamSendInfo.TeamSendInfoResponse;
import com.Abdo.qaim.Models.UpdateTeamResponse.UpdateTeamResponse;
import com.Abdo.qaim.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPainterAdapter extends RecyclerView.Adapter<AddPainterAdapter.ViewHolder> {

    List<com.Abdo.qaim.Models.MyListTeamReports.Painter> painters ;
    AppCompatActivity activity ;
    Retrofit retrofit ;
    JsonApi jsonApi ;


    public AddPainterAdapter(List<com.Abdo.qaim.Models.MyListTeamReports.Painter> painters) {
        this.painters = painters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_painter_layout , null , false);
        activity = (AppCompatActivity) parent.getContext();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://qaim.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonApi = retrofit.create(JsonApi.class);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.Abdo.qaim.Models.MyListTeamReports.Painter painter = painters.get(position);
        try
        {
            Picasso.with(activity).load(painter.getImage()).fit().error(activity.getDrawable(R.drawable.rectangle_4491)).into(holder.imageView);
        }
        catch(NullPointerException e){
            Log.e(TAG, e.toString());
        }
        holder.name.setText(painter.getName());
        holder.description.setText(painter.getNotes());

        // buttons click
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<DeleteTeamResponse> call = jsonApi.deleteTeam("Bearer " + CompanyActivity.token  ,new TeamParams(painter.getId()));
                call.enqueue(new Callback<DeleteTeamResponse>() {
                    @Override
                    public void onResponse(Call<DeleteTeamResponse> call, Response<DeleteTeamResponse> response) {
                        DeleteTeamResponse deleteTeamResponse = response.body();
                        if (deleteTeamResponse.getCode() == 200){
                            Toast.makeText(activity.getBaseContext() , "Painter has been deleted" , Toast.LENGTH_SHORT).show();
                            CompanySetTeamFragment fragment = new CompanySetTeamFragment();
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frameLayout , fragment)
                                    .commit();
                        }


                    }

                    @Override
                    public void onFailure(Call<DeleteTeamResponse> call, Throwable t) {
                        Toast.makeText(activity.getBaseContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.update_employee);
                EditText email = dialog.findViewById(R.id.emailEditText);
                EditText userName = dialog.findViewById(R.id.profileEditText);
                EditText password = dialog.findViewById(R.id.passwordEditText);
                Button comnfirm = dialog.findViewById(R.id.signUp);
                comnfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailV = String.valueOf(email.getText());
                        String nameV = String.valueOf(userName.getText());
                        String passwordV = String.valueOf(password.getText());
                        if (emailV.isEmpty() || nameV.isEmpty() || passwordV.isEmpty()){
                            Toast.makeText(activity , "Enter All Fields Please" , Toast.LENGTH_SHORT).show();
                        }else {
                            Call<UpdateTeamResponse> call = jsonApi.updateTeam("Bearer " + CompanyActivity.token  ,new TeamUpdateParams(painter.getId() ,
                                    String.valueOf(userName.getText()) , String.valueOf(email.getText()) , String.valueOf(password.getText())
                                    ,"painter"
                                    ));
                            call.enqueue(new Callback<UpdateTeamResponse>() {
                                @Override
                                public void onResponse(Call<UpdateTeamResponse> call, Response<UpdateTeamResponse> response) {
                                    UpdateTeamResponse updateTeamResponse = response.body();
                                    if (updateTeamResponse.getCode() == 200){
                                        dialog.dismiss();
                                        Toast.makeText(activity.getBaseContext() , "Edit Success" , Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<UpdateTeamResponse> call, Throwable t) {
                                    Toast.makeText(activity.getBaseContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation ;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
        holder.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.send_file);
                EditText email = dialog.findViewById(R.id.emailEditText);
                Button sendBtn = dialog.findViewById(R.id.signUp);
                sendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailV = String.valueOf(email.getText());
                        if (emailV.isEmpty()){
                            Toast.makeText(activity , "Enter E-Mail Please" , Toast.LENGTH_SHORT).show();
                        }else {
                            Call<TeamSendInfoResponse> call = jsonApi.sendInfo("Bearer " + CompanyActivity.token  ,new TeamParams(painter.getId()));
                            call.enqueue(new Callback<TeamSendInfoResponse>() {
                                @Override
                                public void onResponse(Call<TeamSendInfoResponse> call, Response<TeamSendInfoResponse> response) {
                                    TeamSendInfoResponse teamSendInfoResponse = response.body();
                                    if (teamSendInfoResponse.getCode() == 200){
                                        dialog.dismiss();
                                        Toast.makeText(activity.getBaseContext() , "Send Success" , Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<TeamSendInfoResponse> call, Throwable t) {
                                    Toast.makeText(activity.getBaseContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation ;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
    }

    @Override
    public int getItemCount() {
        return painters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView name , description ;
        Button deleteBtn  , editBtn , sendBtn ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.artistImage);
            name = itemView.findViewById(R.id.artistName);
            description = itemView.findViewById(R.id.artistDescription);
            deleteBtn = itemView.findViewById(R.id.deletBtn);
            editBtn = itemView.findViewById(R.id.editBtn);
            sendBtn = itemView.findViewById(R.id.sendDataBtn);

        }
    }
}
