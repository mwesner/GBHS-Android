package com.grandblanchs.gbhs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Admin extends Fragment {

    private OnFragmentInteractionListener mListener;

    ImageView img_hammond;
    public static Admin newInstance() {
        Admin fragment = new Admin();
        return fragment;
    }

    public Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.admin, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onStart() {
        super.onStart();
        new AdminScrape().execute();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    public class AdminScrape extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("http://grandblanc.high.schoolfusion.us/modules/groups/homepagefiles/cms/133739/Image/Administrators/Hammond%20J.jpg");
                HttpGet httpRequest = null;
                httpRequest = new HttpGet(url.toURI());
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);

                HttpEntity entity = response.getEntity();
                BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                InputStream input = b_entity.getContent();

                final Bitmap bitmap = BitmapFactory.decodeStream(input);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img_hammond = (ImageView) getView().findViewById(R.id.img_hammond);
                        img_hammond.setImageBitmap(bitmap);
                    }
                });




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }












            /*
            try {
                Document doc = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=299293&sessionid=12dcf41eb0da3e6744803422860188de&sessionid=12dcf41eb0da3e6744803422860188de").get();
                Elements img = doc.getElementsByTag("img");
                for (Element el : img) {
                    //for each element get the srs url
                    String src = el.absUrl("src");
                    getImages(src);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            */

            return null;
        }

    }

    /*private static void getImages(String src) throws IOException {
        String folder = null;

        int indexname = src.lastIndexOf("/");
        if (indexname == src.length()){
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname, src.length());

        URL url = new URL(src);
        InputStream in = url.openStream();
        OutputStream out = new BufferedOutputStream(new FileOutputStream("F:/My Pictures"));

        for (int b; (b = in.read()) != -1;) {
            out.write(b);
        }
        out.close();
        in.close();

    }
*/

    }
