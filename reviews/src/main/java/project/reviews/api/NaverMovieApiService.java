package project.reviews.api;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 2022-11-04
* 네이버 영화 검색 API
* */
@Slf4j
@Service
public class NaverMovieApiService {

    final String baseUrl = "https://openapi.naver.com/v1/search/movie.json?query=";

    // 네이버 영화 검색 API를 통해서 검색어에 따른 결과 검색
    public String searchNaverMovie(String _url) {
        HttpURLConnection con = null;
        String result = "";
        int display = 100; // 한번에 표시할 검색 결과의 수
        
        try{
            URL url = new URL(baseUrl + _url + "&display=" + display);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", NaverApiClientInfo.client_id);
            con.setRequestProperty("X-Naver-Client-Secret", NaverApiClientInfo.client_secret);

            int responseCode = con.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK)
                result = readBody(con.getInputStream());
            else
                result = readBody(con.getErrorStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /*
    * 검색 결과 값을 읽어오는 메소드
    * */
    private String readBody(InputStream inputStream) {

        try(BufferedReader lineReader = new BufferedReader(new InputStreamReader(inputStream))){
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = lineReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
    
    /*
    * 필요한 필드만(제목, 평점 등) 매핑해서 반환해주는 메소드
    * readBody에서 받아온 값과 필요한 값만 필터링하기 위한 fields가 파라미터로 들어간다.
    * */
    public Map<String, Object> getResultMapping(String response, String[] fields) {

        Map<String, Object> rtnObj = new HashMap<>();

        try{
            JSONParser parser = new JSONParser();
            JSONObject result = (JSONObject) parser.parse(response);
            rtnObj.put("total", result.get("total"));

            JSONArray items = (JSONArray) result.get("items");
            List<Map<String, Object>> itemList = new ArrayList<>();

            for(int i = 0; i < items.size(); i++) {
                JSONObject item = (JSONObject) items.get(i);
                Map<String, Object> itemMap = new HashMap<>();

                for(String field : fields) {
                    itemMap.put(field, item.get(field));
                }
                itemList.add(itemMap);
            }
            rtnObj.put("result", itemList);
        } catch (ParseException e) {
            e.printStackTrace();
            log.info("getResult Error -> " + "Parsing Error, " + e.getMessage());
        }
        return rtnObj;
    }
}