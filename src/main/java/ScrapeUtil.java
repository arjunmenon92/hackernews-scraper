import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class ScrapeUtil {
    private static String url = "https://news.ycombinator.com/";
    /*private static String heading;
    private static String content;
    private static String rank;
    private static String date;*/


    public static void main(String[] args) throws JsonProcessingException {

        Client respclient = Client.create();
//      System.out.println("Request sent : " + objectMapper.writeValueAsString(request));
        WebResource webResource = respclient.resource("http://localhost:8080/top-news");


        ObjectMapper mapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date datenew = new Date();
        String date = dateFormat.format(datenew).toString();

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page=null;
        try {
            page = client.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<HtmlElement> itemslist =  (List<HtmlElement>) page.getByXPath("//tr[@class='athing']");

        if (itemslist.isEmpty()){
            System.out.println("No items found !");
        }else{
            for(HtmlElement item : itemslist){
                List<HtmlElement> tdlist = (List<HtmlElement>) item.getByXPath(".//td[@class='title']");
                if (tdlist.isEmpty()){
                    System.out.println("No TD found");
                }else {
                    News news = new News();
                    news.setDate(date);
                    for (HtmlElement td : tdlist) {
                        List spanlist = td.getByXPath(".//span[@class='rank']");
                        if (spanlist != null && !spanlist.isEmpty()) {
                            HtmlElement spanRank = ((HtmlElement) spanlist.get(0));
                            if (spanRank != null) {
                                String rank = spanRank.getTextContent();
                                rank=rank.substring(0,rank.length()-1);
                                news.setRank(rank);
                            }
                        }
                        List anchorlist = td.getByXPath(".//a[@class='storylink']");
                        if (anchorlist != null && !anchorlist.isEmpty()) {
                            HtmlAnchor itemAnchor = ((HtmlAnchor) anchorlist.get(0));
                            if (itemAnchor != null) {
                                news.setHeadline(itemAnchor.getTextContent());
                                news.setContent(itemAnchor.getHrefAttribute());
                            }
                        }
                    }
                    String out = mapper.writeValueAsString(news);
                    System.out.println(out);
                    ClientResponse response = webResource.type("application/json").post(ClientResponse.class, out);
                }
            }
        }
    }
}
