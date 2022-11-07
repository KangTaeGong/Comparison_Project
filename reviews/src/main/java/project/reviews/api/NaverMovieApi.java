package project.reviews.api;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
public class NaverMovieApi {

    final String baseUrl = "https://openapi.naver.com/v1/search/movie.json?query=";

    /*
    * 네이버 영화 검색 API를 통해서 검색어에 따른 결과 검색
    * */
    public String search(String clientId, String secret, String _url) {
        HttpURLConnection con = null;
        String result = "";

        try{
            // query 값을 받은 뒤 baseUrl과 연결
            URL url = new URL(baseUrl + _url);
            con = (HttpURLConnection) url.openConnection();

            // GET방식으로 가져오며 지급받은 id, secret을 넘겨준다.
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", secret);

            // 응답 코드가 200이면 값을 읽어오고, 아니라면 예외 발생
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
    * 검색 결과에 따라 결과 값을 읽어오는 메소드
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
    * 필요한 값만 매핑해서 반환해주는 메소드
    * readBody에서 받아온 값과 필요한 값만 필터링하기 위한 fields가 파라미터로 들어간다.
    * */
    public Map<String, Object> getResultMapping(String response, String[] fields) {

        Map<String, Object> rtnObj = new HashMap<>();

        try{
            JSONParser parser = new JSONParser();
            JSONObject result = (JSONObject) parser.parse(response);
            rtnObj.put("total", result.get("total"));

            // 검색된 items JSONArrays로 변환해서 값 저장
            JSONArray items = (JSONArray) result.get("items");
            List<Map<String, Object>> itemList = new ArrayList<>();

            for(int i = 0; i < items.size(); i++) {
                // JSONArrays에서 item값을 하나씩 꺼냄
                JSONObject item = (JSONObject) items.get(i);
                Map<String, Object> itemMap = new HashMap<>();

                // 넘겨준 field값을 통해서 item에서 원하는 값만 꺼내서 Map에 저장한다.
                for(String field : fields) {
                    itemMap.put(field, item.get(field));
                }
                itemList.add(itemMap);
            }
            // 최종적으로 매핑한 item 결과를 result라는 이름으로 Map에 저장
            rtnObj.put("result", itemList);
        } catch (ParseException e) {
            e.printStackTrace();
            log.info("getResult Error -> " + "Parsing Error, " + e.getMessage());
        }
        return rtnObj;
    }
}