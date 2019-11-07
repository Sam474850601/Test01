package example.sam.test01.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;


/**
 * Retrofit 管理器
 *
 * @author Sam
 */

public class RetrofitManager {

    private static volatile RetrofitManager instance;


    private RetrofitManager() {

    }

    private Retrofit mRetrofit;


    public static RetrofitManager getInstance() {
        if (null == instance) {
            synchronized (RetrofitManager.class) {
                if (null == instance) {
                    return instance = new RetrofitManager();

                }
            }
        }
        return instance;
    }


    OkHttpClient okHttpClient;


    /**
     * 获取一个okhttp居于https请求的Retrofit
     *
     * @param baseUrl 服务器根地址
     */
    public void buildHttpsRetrofit(String baseUrl
                                   ) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        okHttpClient = getSimpleHttpsOkHttpClientBuilder().build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


    }




    private OkHttpClient.Builder getSimpleHttpsOkHttpClientBuilder() {

        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                //如果返回空，怎创建实例失败
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(DO_NOT_VERIFY);
        return builder;

    }

    private Retrofit _getRetrofit() {
        if (null == mRetrofit) {
            throw new RuntimeException("you must be build a  instance of Retrofit to SDKRetrofitManager firstly!");
        }
        return mRetrofit;
    }


    public static <T> T create(Class<? extends T> apiClass) {
        return RetrofitManager.getInstance()._getRetrofit().create(apiClass);
    }


}

