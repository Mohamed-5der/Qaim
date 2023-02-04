package com.Abdo.qaim.Classes;

public class TeamUpdateParams {
    int team_id ; String name ; String email ; String password ; String type ;

    public TeamUpdateParams(int team_id, String name, String email, String password, String type) {
        this.team_id = team_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
