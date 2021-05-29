package tech.codingclub.helix.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tech.codingclub.helix.database.GenericDB;
import tech.codingclub.helix.entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * User: rishabh
 */
@Controller
@RequestMapping("/")
public class MainController extends BaseController {

    private static Logger logger = Logger.getLogger(MainController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/helloworld")
    public String getQuiz(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "hello";
    }



    @RequestMapping(method = RequestMethod.GET, value = "/signup")
    public String signUp(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        return "signUp";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public
    @ResponseBody
    SignupResponse signUpData(@RequestBody Member member, HttpServletRequest request, HttpServletResponse response) {
       String message="";
       boolean user_created=false;
       if(GenericDB.getCount(tech.codingclub.helix.tables.Members.MEMBERS,tech.codingclub.helix.tables.Members.MEMBERS.EMAIL.eq(member.email))>0)
       {
           message="user already exists with this mail id!";
       }
       else {
           member.role="cm";
           new GenericDB<Member>().addRow(tech.codingclub.helix.tables.Members.MEMBERS, member);
       message="user created!";
       user_created=true;
       }
        return new SignupResponse(message,user_created);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/time")
    public @ResponseBody String getTime(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request) {
        TimeApi timeApi=new TimeApi(new Date().toString(),new Date().getTime());
        return new Gson().toJson(timeApi);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/wiki")
    public @ResponseBody String getWikiResult(ModelMap modelMap,@RequestParam("country") String country, HttpServletResponse response, HttpServletRequest request) {
       WikipediaDownloader wikipediaDownloader=new WikipediaDownloader(country);
       WikiResult x = wikipediaDownloader.getResult();
       return new Gson().toJson(x);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/api/wiki/html")
    public  String getWikiResultHtml(ModelMap modelMap,@RequestParam("country") String country, HttpServletResponse response, HttpServletRequest request) {
        WikipediaDownloader wikipediaDownloader=new WikipediaDownloader(country);
        WikiResult x = wikipediaDownloader.getResult();
        modelMap.addAttribute("IMAGE",x.getImage_url());
        modelMap.addAttribute("DESCRIPTION",x.getText_result());


        return "wikiApi";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/handle")
    public @ResponseBody String handleEncrypt(@RequestBody String data, HttpServletResponse response, HttpServletRequest request) {
        return "ok";
    }

}