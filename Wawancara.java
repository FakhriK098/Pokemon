import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import org.json.*;
import java.net.*;
import java.nio.charset.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Wawancara {

    public static void main(String[] args) {
        ArrayList listName = new ArrayList();
        ArrayList listUrl = new ArrayList();
        ArrayList listType = new ArrayList();
        ArrayList listImg = new ArrayList();
        ArrayList listColor = new ArrayList();
        
        try{
            String url = "https://pokeapi.co/api/v2/pokemon?limit=10"; 
            JSONObject json = (JSONObject) readJsonFromUrl(url);
            JSONArray ja = (JSONArray) json.get("results");
            for (int i = 0; i < ja.length(); i++) {
                json = (JSONObject) ja.get(i);
                listName.add(json.get("name"));
                listUrl.add(json.get("url"));
                
                json = (JSONObject) readJsonFromUrl(json.get("url").toString());
                JSONObject jImg = (JSONObject) json.get("sprites");
                JSONObject jImg_ = (JSONObject) jImg.get("other");
                jImg = (JSONObject) jImg_.get("official-artwork");
                listImg.add(jImg.get("front_default"));
                
                JSONArray jTypes = (JSONArray) json.get("types");
                listType.add(jTypes);
                
                JSONObject urlSpecies = (JSONObject) json.get("species");
                JSONObject jColor = (JSONObject) readJsonFromUrl(urlSpecies.get("url").toString());
                JSONObject color = (JSONObject) jColor.get("color");
                listColor.add(color.get("name"));
            }
            
            
            JSONArray jResult_ = new JSONArray();
            for (int i = 0; i < listName.size(); i++) {
                JSONObject jResult = new JSONObject();
                jResult.put("color", listColor.get(i));
                jResult.put("image", listImg.get(i));
                jResult.put("name", listName.get(i));
                jResult.put("types", listType.get(i));
                jResult_.put(jResult);
            }
            
            
            
            
            for (int i = 0; i < jResult_.length(); i++) {
                System.out.println(jResult_.get(i));
            }
            
        }catch(Exception e){
        
        }
        
        
    }
    
    public static JSONObject readJsonFromUrl(String link) throws IOException, JSONException {
        InputStream input = new URL(link).openStream();
        try { 
            BufferedReader re = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8"))); 
            String Text = Read(re); 
            JSONObject json = new JSONObject(Text);  
            return json;   
        } catch (Exception e) {
            return null;
        } finally {
            input.close();
        }
    }
    public static String Read(Reader re) throws IOException {   
        StringBuilder str = new StringBuilder();     
        int temp;
        do {
            temp = re.read();       
            str.append((char) temp);

        } while (temp != -1);        

        return str.toString();

    }
    
}
