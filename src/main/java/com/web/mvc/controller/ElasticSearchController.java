package com.web.mvc.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.springframework.http.HttpHeaders;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mysql.jdbc.StringUtils;
import com.web.mvc.model.Product;

//@Controller
//@RequestMapping("/elastic-seaarch")
@RestController
public class ElasticSearchController {

	  //@RequestMapping(method = RequestMethod.GET)
	/*@RequestMapping(value = "/elastic-seaarch/", method = RequestMethod.GET)
	public String displayElasticSerachPage() {
	     return "ElasticSearch";
	}*/
	
	@RequestMapping(value = "/products/", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts() throws Exception {
        /*List<Employee> users = userService.findAllEmployees();
        if(users.isEmpty()){
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }*/
		/*List<Product> products = new ArrayList<>();
		for(int i=1; i<=4; i++) {
			Product product = new Product();
			product.setId(i);
			product.setTitle("Title"+i);
			product.setCategory("Category"+i);
			product.setAuthor("Author"+i);
			product.setPrice(Double.valueOf(i));
			products.add(product);
					
		}*/
		//getProductsDetails();
        return new ResponseEntity<List<Product>>(getProductsDetails(), HttpStatus.OK);
    }
	
	private String connectToElaasticSeaarch(String elasticUrl, String method, String body, String id) throws Exception {
		if(!StringUtils.isNullOrEmpty(id)) {
			elasticUrl = elasticUrl+"?q=_id:"+id;
		}
		StringBuilder sb = new StringBuilder();
    	URL url = new URL(elasticUrl);
    	URLConnection urlCon = url.openConnection();
    	urlCon.setConnectTimeout(60000);
    	urlCon.setReadTimeout(60000);
    	((HttpURLConnection)urlCon).setRequestMethod(method);
    	
    	urlCon.setDoOutput(true);
    	urlCon.setDoInput(true);
    	/*if(!StringUtils.isNullOrEmpty(body)) {
    		OutputStream os = urlCon.getOutputStream();
            os.write(body.getBytes("UTF-8"));
            os.close();
		}*/
    	if(!"DELETE".equalsIgnoreCase(method)) {
	    	DataOutputStream dos = new DataOutputStream(urlCon.getOutputStream());
	    	if(!StringUtils.isNullOrEmpty(body)) {
	    		//OutputStream os = urlCon.getOutputStream();
	    		dos.write(body.getBytes("UTF-8"));
			}
	    	
	    	dos.flush();
	    	dos.close();
    	}
    	InputStream is = urlCon.getInputStream();
    	BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
    	String line = null;
    	while((line = br.readLine()) != null) {
    		sb.append(line);
    	}
    	br.close();
		
		return sb.toString();
	}
	
	private List<Product> getProductsDetails() throws Exception {
		List<Product> products = new ArrayList<>();
    	/*StringBuilder sb = new StringBuilder();
    	URL url = new URL("http://localhost:9200/vp/posts/_search");
    	URLConnection urlCon = url.openConnection();
    	urlCon.setConnectTimeout(60000);
    	urlCon.setReadTimeout(60000);
    	((HttpURLConnection)urlCon).setRequestMethod("GET");
    	
    	urlCon.setDoOutput(true);
    	DataOutputStream dos = new DataOutputStream(urlCon.getOutputStream());
    	dos.flush();
    	dos.close();
    	InputStream is = urlCon.getInputStream();
    	BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
    	String line = null;
    	while((line = br.readLine()) != null) {
    		sb.append(line);
    	}
    	br.close();
    	String jsonRes = sb.toString();*/
    	
    	
    	
    	String jsonRes = connectToElaasticSeaarch("http://localhost:9200/vp/posts/_search", "GET", "", "");
    	System.out.println("Response...."+jsonRes);
    	
    	JSONObject json = new JSONObject(jsonRes);
    	JSONObject hitsObj = json.getJSONObject("hits");
    	JSONArray hitsArr = hitsObj.getJSONArray("hits");
    	
    	//JSONObject first = hitsArr.getJSONObject(0); // assumes 1 entry in hits array
    	//JSONObject source = first.getJSONObject("_source");
    	Iterator it = hitsArr.iterator();
    	List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    	
    	while(it.hasNext()) {
    		HashMap<String, String> map = new HashMap<String, String>();
    		JSONObject obj = (JSONObject)it.next();
    		JSONObject source = obj.getJSONObject("_source");
    		
    		Product product = new Product();
			product.setId(obj.getLong("_id"));
			product.setTitle(source.getString("title"));
			product.setCategory(source.getString("category"));
			product.setAuthor(source.getString("author"));
			product.setPrice(source.getDouble("price"));
			products.add(product);
    		
    		/*map.put("title", source.getString("title"));
    		map.put("category", source.getString("category"));
    		map.put("published_date", source.getString("published_date"));
    		map.put("author", source.getString("author"));
    		
    		data.add(map);*/
    		
    	}
    	System.out.println("data..."+products.size());
    	System.out.println("data..."+products);
    	/*String title = source.getString("title");
    	String category = source.getString("category");
    	String published_date = source.getString("published_date");
    	String author = source.getString("author");
    	
    	System.out.println("Details..."+title + ","+category+","+published_date+","+author);*/
    	
    	return products;
    	
    }
	
	
	//-------------------Retrieve Single User--------------------------------------------------------
    
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) throws Exception {
        System.out.println("Fetching Product with id " + id);
        
        /*String jsonRes = connectToElaasticSeaarch("http://localhost:9200/vp/posts/_search", "GET", "", String.valueOf(id));
        
        JSONObject json = new JSONObject(jsonRes);
    	JSONObject hitsObj = json.getJSONObject("hits");
    	JSONArray hitsArr = hitsObj.getJSONArray("hits");
    	
    	JSONObject first = hitsArr.getJSONObject(0); // assumes 1 entry in hits array
    	JSONObject source = first.getJSONObject("_source");
        
    	Product product = new Product();
    	product.setId(id);
		product.setTitle(source.getString("title"));
		product.setCategory(source.getString("category"));
		product.setAuthor(source.getString("author"));
		product.setPrice(source.getDouble("price"));*/
		
        return new ResponseEntity<Product>(findProductByid(id), HttpStatus.OK);
    }
    
    private Product findProductByid(Long id) throws Exception {
    	String jsonRes = connectToElaasticSeaarch("http://localhost:9200/vp/posts/_search", "GET", "", String.valueOf(id));
        
        JSONObject json = new JSONObject(jsonRes);
    	JSONObject hitsObj = json.getJSONObject("hits");
    	JSONArray hitsArr = hitsObj.getJSONArray("hits");
    	
    	Product product = null;
    	if(hitsArr.length() > 0 && hitsArr.getJSONObject(0) != null) {
	    	JSONObject first = hitsArr.getJSONObject(0); // assumes 1 entry in hits array
	    	JSONObject source = first.getJSONObject("_source");
	        
	    	product = new Product();
	    	product.setId(id);
			product.setTitle(source.getString("title"));
			product.setCategory(source.getString("category"));
			product.setAuthor(source.getString("author"));
			product.setPrice(source.getDouble("price"));
    	}
		
		return product;
    }
 
     
     
    //-------------------Create a User--------------------------------------------------------
     
    @RequestMapping(value = "/products/", method = RequestMethod.POST)
    public ResponseEntity<Product> createProduct( @RequestBody Product product) throws Exception {
        
        System.out.println("Product ..."+product.toString());
        
        Product existProduct = findProductByid(product.getId());
        
        if (existProduct != null) {
            System.out.println("Product with id " + product.getId() + " alreday Exist");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        
 
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        
        sb.append("\"title\": \""+product.getTitle()+"\",");
        sb.append("\"category\": \""+product.getCategory()+"\",");
        sb.append("\"author\": \""+product.getAuthor()+"\",");
        sb.append("\"price\": \""+product.getPrice()+"\"");
        
        sb.append("}");
        
        System.out.println("JsonBody..."+sb.toString());
        
        connectToElaasticSeaarch("http://localhost:9200/vp/posts/"+product.getId(), "POST", sb.toString(), "");
        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }
 
    
     
    //------------------- Update a User --------------------------------------------------------
     
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) throws Exception {
        System.out.println("Updating Product " + id);
        System.out.println("Product ..."+product.toString());
        
        Product currentProduct = findProductByid(id);
         
        if (currentProduct==null) {
            System.out.println("Product with id " + id + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
 
        
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        
        sb.append("\"title\": \""+product.getTitle()+"\",");
        sb.append("\"category\": \""+product.getCategory()+"\",");
        sb.append("\"author\": \""+product.getAuthor()+"\",");
        sb.append("\"price\": \""+product.getPrice()+"\"");
        
        sb.append("}");
        
        System.out.println("JssonBody..."+sb.toString());
        
        connectToElaasticSeaarch("http://localhost:9200/vp/posts/"+id, "PUT", sb.toString(), "");
        /*currentUser.setUsername(user.getUsername());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
         
        userService.updateEmployee(currentUser);*/
        currentProduct.setTitle(product.getTitle());
        currentProduct.setCategory(product.getCategory());
        currentProduct.setAuthor(product.getAuthor());
        currentProduct.setPrice(product.getPrice());
        
        return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
    }
 
    
    
    //------------------- Delete a User --------------------------------------------------------
     
    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") long id) throws Exception {
        System.out.println("Fetching & Deleting User with id " + id);
 
        Product currentProduct = findProductByid(id);
        
        if (currentProduct==null) {
        	System.out.println("Unable to delete. Product with id " + id + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
 
        connectToElaasticSeaarch("http://localhost:9200/vp/posts/"+id, "DELETE", "", "");
        
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
 
     
    
    //------------------- Delete All Users --------------------------------------------------------
     /*
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteAllUsers() {
        System.out.println("Deleting All Users");
 
        userService.deleteAllEmployees();
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }*/

}
