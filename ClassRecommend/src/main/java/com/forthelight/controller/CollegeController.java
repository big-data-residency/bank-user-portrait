package com.forthelight.controller;

import com.forthelight.biz.CollegeBiz;
import com.forthelight.biz.MajorBiz;
import com.forthelight.domain.Major;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CollegeController {
    @Autowired
    private CollegeBiz collegeBiz;
    @Autowired
    private MajorBiz majorBiz;

    @RequestMapping(value = "/getMajors", produces = "text/json; charset=utf-8")
    @ResponseBody
    public String getMajors(@RequestParam("collegeId")String collegeId){
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject data = new JsonObject();
        JsonObject rsp = new JsonObject();

        List<Major> majorList = majorBiz.findByCollegeId(Integer.parseInt(collegeId));

        JsonElement majors = new JsonArray();
        for(Major major: majorList){
            majors.getAsJsonArray().add(gson.toJsonTree(major, Major.class));
        }
        data.add("majors", majors);

        rsp.add("data", data);
        rsp.addProperty("success", true);
        String result = gson.toJson(rsp);
        return result;
    }
}
