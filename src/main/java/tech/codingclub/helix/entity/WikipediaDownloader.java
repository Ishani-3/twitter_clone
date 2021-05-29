package tech.codingclub.helix.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tech.codingclub.helix.global.HttpUrlConnectionExample;

import java.util.Date;

public class WikipediaDownloader {

    private String keyWord;
    private String response;

    public WikipediaDownloader()
    {

    }
    public WikipediaDownloader(String keyWord) {
        this.keyWord = keyWord;
    }
    public WikiResult getResult() {
        //step 1:clean keyword
            if(this.keyWord==null || this.keyWord.length()==0)
               return null;
             this.keyWord=this.keyWord.replaceAll("[ ]+","_");
             //step2: get  wikipedia url
        String wikiUrl=getWikipediaUrlForQuery(this.keyWord);
          //step3: wikipedia response
        String response="";
        String imageUrl = null;
        try {
            String wikipediaResponseHTML= HttpUrlConnectionExample.sendGet(wikiUrl);
           // System.out.println(wikipediaResponseHTML);

            //step4:
            Document document= Jsoup.parse(   wikipediaResponseHTML ,"https://en.wikipedia.org");
            Elements childElements=document.body().select(".mw-parser-output > *");
            int state=0;



            for(Element childElement:childElements)
            {
                if(state==0) {
                    if (childElement.tagName().equals("table")) {
                        state = 1;
                    }

                }
                else if(state==1) {
                    if (childElement.tagName().equals("p")) {
                        state = 2;
                        response = childElement.text();
                        break;
                    }
                }
            }
            try {
                imageUrl=document.body().select(".infobox img").get(0).attr("src");
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        WikiResult wikiResult=new WikiResult(this.keyWord,response,imageUrl);
        return wikiResult;


    }

    private String getWikipediaUrlForQuery(String cleanKeyWord) {
        return "https://en.wikipedia.org/wiki/"+cleanKeyWord;
    }


    }



