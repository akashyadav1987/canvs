package com.canvas.io.http;//package com.pulp.catalog.io.http;
//
//import com.pulp.catalog.utils.UrlConstants;
//import com.squareup.okhttp.OkHttpClient;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.lang.reflect.Type;
//import java.net.URI;
//import java.util.Collections;
//
//import retrofit.RestAdapter;
//import retrofit.client.Client;
//import retrofit.client.OkClient;
//import retrofit.client.Request;
//import retrofit.client.Response;
//import retrofit.converter.ConversionException;
//import retrofit.converter.Converter;
//import retrofit.mime.TypedByteArray;
//import retrofit.mime.TypedInput;
//import retrofit.mime.TypedOutput;
//
///**
// * Created by Ripansharma on 18/06/15.
// */
//public class HttpRestClient {
//    private static HttpAPI REST_CLIENT;
//    private static String ROOT = UrlConstants.Catalog_BASE_URL;
//
//    static {
//        setupRestClient();
//    }
//
//    private HttpRestClient() {
//    }
//
//    public static HttpAPI get() {
//        return REST_CLIENT;
//    }
//
//    private static void setupRestClient() {
//        RestAdapter.Builder builder = new RestAdapter.Builder()
//                .setConverter(new StringConverter())
//
//                .setClient(new OkClient(new OkHttpClient()))
//                .setLogLevel(RestAdapter.LogLevel.FULL);
//
//        RestAdapter restAdapter = builder.build();
//        REST_CLIENT = restAdapter.create(HttpAPI.class);
//    }
//
//    static class StringConverter implements Converter {
//
//        public static String fromStream(InputStream in) throws IOException {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder out = new StringBuilder();
//            String newLine = System.getProperty("line.separator");
//            String line;
//            while ((line = reader.readLine()) != null) {
//                out.append(line);
//                out.append(newLine);
//            }
//            return out.toString();
//        }
//
//        @Override
//        public Object fromBody(TypedInput typedInput, Type type) throws ConversionException {
//            String text = null;
//            try {
//                text = fromStream(typedInput.in());
//            } catch (IOException ignored) {/*NOP*/ }
//
//            return text;
//        }
//
//        @Override
//        public TypedOutput toBody(Object o) {
//            return null;
//        }
//    }
//
//    public static class MockClient implements Client {
//        @Override
//        public Response execute(Request request) throws IOException {
//            URI uri = URI.create(request.getUrl());
//            String responseString = "";
//
//            if (uri.getPath().equals("/api/")) {
//                responseString = "{result:\"ok\"}";
//            } else {
//                responseString = "{result:\"error\"}";
//            }
//
//            return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST,
//                    new TypedByteArray("application/json", responseString.getBytes()));
//        }
//    }
//}
//
