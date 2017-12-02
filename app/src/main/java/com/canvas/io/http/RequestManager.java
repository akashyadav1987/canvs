package com.canvas.io.http;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
//import com.pulp.master.sthetho.OkHttpStack;
//import com.pulp.master.sthetho.StethoInterceptor;
//import com.squareup.okhttp.OkHttpClient;

/**
 * Request Manager Class for Managing the Network Requests
 */
public class RequestManager {
    private static RequestManager mRequestManager;
    /**
     * Queue which Manages the Network Requests :-)
     */
    private static RequestQueue mRequestQueue;
    // ImageLoader Instance
    private static ImageLoader mImageLoader;
    Bitmap bitmap = null;

    private RequestManager() {
    }

    public static RequestManager get(Context context) {
        if (mRequestManager == null)
            mRequestManager = new RequestManager();
        return mRequestManager;
    }

    /**
     * @param context application context
     */
    public static RequestQueue getnstance(Context context) {
        if (mRequestQueue == null) {

//            OkHttpClient okHttpClient = new OkHttpClient();
//            okHttpClient.networkInterceptors().add(new StethoInterceptor());
//            mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack(okHttpClient));
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

//    public static ImageLoader getImageLoader() {
//        if (mImageLoader == null)
//            mImageLoader = new ImageLoader(mRequestQueue, new ImageCache() {
//                private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(
//                        10);
//
//                public void putBitmap(String url, Bitmap bitmap) {
//                    mCache.put(url, bitmap);
//                }
//
//                public Bitmap getBitmap(String url) {
//                    return mCache.get(url);
//                }
//            });
//        return mImageLoader;
//    }
//	/**
//	 * 
//	 * @param context
//	 * @param imageView
//	 * @param imgUrl
//	 * @param imagerequest
//	 */
//
//	public Bitmap requestImage(Context context, final ImageView imageView,
//			final String imgUrl) {
//
//		imageView.setTag(imgUrl);
//
//		ImageRequest request = new ImageRequest(imgUrl, new Listener<Bitmap>() {
//
//			@Override
//			public void onResponse(Bitmap bm) {
//
//				imageView.setImageBitmap(RoundedCornerImage
//						.getRoundedCornerBitmap(bm));
//			}
//		},
//
//		0, 0, Config.ARGB_8888, new ErrorListener() {
//
//			public void onErrorResponse(VolleyError error) {
//				error.printStackTrace();
//			}
//
//		});
//		RequestManager.getnstance(context).add(request);
//		return bitmap;
//
//	}
//	public Bitmap requestCircularImage(Context context,
//			final FWCircularImageView imageView, final String imgUrl) {
//
//		imageView.setTag(imgUrl);
//
//		ImageRequest request = new ImageRequest(imgUrl, new Listener<Bitmap>() {
//
//			@Override
//			public void onResponse(Bitmap bm) {
//				Log.e("requestCircularImage", "Setting image");
//				imageView.setImageBitmap(bm);
//			}
//		},
//
//		0, 0, Config.ARGB_8888, new ErrorListener() {
//
//			public void onErrorResponse(VolleyError error) {
//				Log.e("requestCircularImage", "Error " + error.toString());
//				error.printStackTrace();
//			}
//
//		});
//		RequestManager.getnstance(context).add(request);
//		return bitmap;
//
//	}
}
