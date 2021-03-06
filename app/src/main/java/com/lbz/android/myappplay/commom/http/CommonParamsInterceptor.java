package com.lbz.android.myappplay.commom.http;

import android.content.Context;

import com.google.gson.Gson;
import com.lbz.android.myappplay.commom.Constant;
import com.lbz.android.myappplay.commom.util.DensityUtil;
import com.lbz.android.myappplay.commom.util.DeviceUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lbz on 2017/7/25.
 */
public class CommonParamsInterceptor implements Interceptor {


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Gson mGson;
    private Context mContext;

    public CommonParamsInterceptor(Context context, Gson gson) {
        this.mGson = gson;
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        String method = request.method();

        if (method.equals("GET")) {

            HttpUrl oldBaseUrl = request.url();

            HttpUrl rurl = oldBaseUrl.newBuilder() //请求尾部链接
                    .addQueryParameter(Constant.CHANNEL, "market_100_1_android")
                    .addQueryParameter(Constant.CLIENT_ID, "be8597169281e5379632c34ab20ed13c")
                    .addQueryParameter(Constant.CO, "CN")
                    .addQueryParameter(Constant.DENSITY_SCALE_FACTOR, mContext.getResources().getDisplayMetrics().density + "")
//                    .addQueryParameter(Constant.IMEI, DeviceUtils.getIMEI(mContext))
                    .addQueryParameter(Constant.IMEI, "48667b0737e9817b56728704448d0729")
                    .addQueryParameter(Constant.LANGUAGE, DeviceUtils.getLanguage())
                    .addQueryParameter(Constant.MARKET_VERSION, "147")
                    .addQueryParameter(Constant.MODEL, DeviceUtils.getModel())
                    .addQueryParameter(Constant.os, DeviceUtils.getBuildVersionIncremental())
                    .addQueryParameter(Constant.RESOLUTION, DensityUtil.getScreenW(mContext) + "*" + DensityUtil.getScreenH(mContext))
                    .addQueryParameter(Constant.SDK, DeviceUtils.getBuildVersionSDK() + "")
                    .addQueryParameter(Constant.STAMP, 0 + "")
                    .build();

            request = request.newBuilder().url(rurl).build();
        }


        return chain.proceed(request);

    }


//    @Override
//    public Response intercept(Chain chain) throws IOException {
//
//        Request request = chain.request();
//
//        List<String> headerValues = request.headers(ApiService.REQUEST_HEADER_FLAG);
//
//        /**
//         * 小米的url
//         */
//        if (headerValues != null && headerValues.size() > 0) {
//
//
//
//        } else {
//            /**
//             * 另一个url
//             */
//            try {
//                String method = request.method();
//
//                HashMap<String, Object> commomParamsMap = new HashMap<>();
//
//                commomParamsMap.put(Constant.IMEI, DeviceUtils.getIMEI(mContext));
//                commomParamsMap.put(Constant.MODEL, DeviceUtils.getModel());
//                commomParamsMap.put(Constant.LANGUAGE, DeviceUtils.getLanguage());
//                commomParamsMap.put(Constant.os, DeviceUtils.getBuildVersionIncremental());
//                commomParamsMap.put(Constant.RESOLUTION, DensityUtil.getScreenW(mContext) + "*" + DensityUtil.getScreenH(mContext));
//                commomParamsMap.put(Constant.SDK, DeviceUtils.getBuildVersionSDK() + "");
//                commomParamsMap.put(Constant.DENSITY_SCALE_FACTOR, mContext.getResources().getDisplayMetrics().density + "");
//
//                String token = ACache.get(mContext).getAsString(Constant.TOKEN);
//                commomParamsMap.put(Constant.TOKEN, token == null ? "" : token);
//
//                if (method.equals("GET")) {
//                    HttpUrl httpUrl = request.url();
//
//                    Set<String> paramNames = httpUrl.queryParameterNames();
//                    HashMap<String, Object> rootMap = new HashMap<>();
//
//                    for (String key : paramNames) {
//
//                        if (key.equals(Constant.PARAM)) {
//                            String oldParam = httpUrl.queryParameter(Constant.PARAM);
//
//                            if (oldParam != null) {
//                                HashMap<String, Object> p = mGson.fromJson(oldParam, HashMap.class);//原始 参数
//
//                                if (p != null) {
//                                    for (Map.Entry<String, Object> entry : p.entrySet()) {
//                                        rootMap.put(entry.getKey(), entry.getValue());
//                                    }
//                                }
//                            }
//
//
//                        } else {
//                            rootMap.put(key, httpUrl.queryParameter(key));
//                        }
//
//                    }
//
//
//                    rootMap.put("publicParams", commomParamsMap);//把公共参数加进来重新组装
//
//                    String newJsonParams = mGson.toJson(rootMap);
//
//                    String url = httpUrl.toString();
//
//                    int index = url.indexOf("?");
//                    if (index > 0) {
//                        url = url.substring(0, index);
//                    }
//
//                    url = url + "?" + Constant.PARAM + "=" + newJsonParams;
//
//                    request = request.newBuilder().url(url).build();
//                } else if (method.equals("POST")) {
//
//
//                    RequestBody body = request.body();
//
//                    HashMap<String, Object> rootMap = new HashMap<>();
//
//                    if (body instanceof FormBody) {
//
//                        for (int i = 0; i < ((FormBody) body).size(); i++) {
//                            rootMap.put(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
//                        }
//
//                    } else {
//                        Buffer buffer = new Buffer();
//
//                        body.writeTo(buffer);
//
//                        String oldJsonParams = buffer.readUtf8();
//
//                        if (!TextUtils.isEmpty(oldJsonParams)) {
//
//                            rootMap = mGson.fromJson(oldJsonParams, HashMap.class); // 原始参数
//
//
//                            if (rootMap != null) {
//
//                                rootMap.put("publicParams", commomParamsMap); // 重新组装
//
//                                String newJsonParams = mGson.toJson(rootMap); // {"page":0,"publicParams":{"imei":'xxxxx',"sdk":14,.....}}
//
//                                request = request.newBuilder().post(RequestBody.create(JSON, newJsonParams)).build();
//                            }
//
//
//                        }
//
//
//                    }
//
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return chain.proceed(request);
//
//        }
//
//
//    }

}
