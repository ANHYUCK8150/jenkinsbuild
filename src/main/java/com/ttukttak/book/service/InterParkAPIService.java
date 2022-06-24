package com.ttukttak.book.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ttukttak.book.dto.BookInfoDto;

@Service
public class InterParkAPIService {
	@Value("${interpark.book.key}")
	private String key;

	public List<BookInfoDto> search(String query) {

		StringBuffer sb = new StringBuffer();
		List<BookInfoDto> bookList = new ArrayList<BookInfoDto>();

		try {
			URL url = new URL("https://book.interpark.com/api/"
				+ "search.api?query="
				+ URLEncoder.encode(query, "UTF-8")
				+ "&key=" + key
				+ "&maxResults=50"
				+ "&output=json");
			HttpsURLConnection http = (HttpsURLConnection)url.openConnection();
			http.setRequestProperty("Content-Type", "application/json");
			http.setRequestMethod("GET");
			http.connect();

			InputStreamReader in = new InputStreamReader(http.getInputStream(), "utf-8");
			BufferedReader br = new BufferedReader(in);

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}

			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject)parser.parse(sb.toString());
			JSONArray jsonItems = (JSONArray)jsonObject.get("item");

			//종합 결과
			//			result.setTotalResults(Integer.parseInt(jsonObject.get("totalResults").toString()));
			//			result.setReturnMessage(jsonObject.get("returnMessage").toString());
			//			result.setReturnCode(jsonObject.get("returnCode").toString());

			//도서 리스트
			for (int i = 0; i < jsonItems.size(); i++) {
				JSONObject item = (JSONObject)jsonItems.get(i);
				BookInfoDto book = new BookInfoDto();

				book.setName(item.get("title").toString());
				book.setDescription(item.get("description").toString());
				book.setPublishedDate(item.get("pubDate").toString());
				book.setPrice(Integer.parseInt(item.get("priceStandard").toString()));
				book.setImage(item.get("coverLargeUrl").toString());
				//사용자가 직접 카테고리를 지정하기 때문에 주석처리.
				//book.setCategoryId(item.get("categoryId").toString());
				book.setPublisher(item.get("publisher").toString());
				book.setAuthor(item.get("author").toString());
				book.setIsbn(item.get("isbn").toString());

				bookList.add(book);

			}

			br.close();
			in.close();
			http.disconnect();

		} catch (IOException e) {} catch (ParseException e) {}

		return bookList;
	}
}
