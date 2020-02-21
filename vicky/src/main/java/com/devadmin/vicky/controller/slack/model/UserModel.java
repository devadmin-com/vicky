package com.devadmin.vicky.controller.slack.model;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import lombok.Data;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Data
public class UserModel {
    private String token;
    private String teamId;
    private String teamDomain;
    private String enterpriseId;
    private String channelId;
    private String channelName;
    private String userId;
    private String userName;
    private String command;
    private String text;
    private String responseUrl;
    private String triggerId;

    @RequestMapping(value = "/get", method= RequestMethod.GET)
    public Map getParameters(HttpServletRequest request){
        Enumeration enumeration = request.getParameterNames();
        Map<String, Object> modelMap = new HashMap<>();
        while(enumeration.hasMoreElements()){
            String parameterName = enumeration.nextElement().toString();
            modelMap.put(parameterName, request.getParameter(parameterName));
        }
        return modelMap;
    }

    public UserModel paramsToUserModel(Map map){
        UserModel userModel = new UserModel();
        userModel.setToken((String) map.get("token"));
        userModel.setTeamId((String) map.get("team_id"));
        userModel.setTeamDomain((String) map.get("team_domain"));
        userModel.setEnterpriseId((String) map.get("enterprise_id"));
        userModel.setChannelId((String) map.get("channel_id"));
        userModel.setChannelName((String) map.get("channel_name"));
        userModel.setUserId((String) map.get("user_id"));
        userModel.setUserName((String) map.get("user_name"));
        userModel.setCommand((String) map.get("command"));
        userModel.setText((String) map.get("text"));
        userModel.setResponseUrl((String) map.get("response_url"));
        userModel.setTriggerId((String) map.get("trigger_id"));
        return userModel;
    }
}
