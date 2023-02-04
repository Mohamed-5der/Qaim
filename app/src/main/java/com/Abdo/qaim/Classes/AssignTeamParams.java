package com.Abdo.qaim.Classes;

public class AssignTeamParams {
    int request_id ; int painter_id ; int reviewer_id ; int previewer_id ; String type ;

    public AssignTeamParams(int request_id, int painter_id, int reviewer_id, int previewer_id, String type) {
        this.request_id = request_id;
        this.painter_id = painter_id;
        this.reviewer_id = reviewer_id;
        this.previewer_id = previewer_id;
        this.type = type;
    }
}
