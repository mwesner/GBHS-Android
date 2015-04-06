package com.grandblanchs.gbhs;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class Facebook extends Fragment {

    public interface OnFragmentInteractionListener{}

    TextView hello;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.facebook, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hello = (TextView) view.findViewById(R.id.hello);
        Session.openActiveSession(getActivity(), true, new Session.StatusCallback(){
            @SuppressWarnings("deprecation")
            @Override
            public void call(Session session, SessionState state, Exception exception){
                if (session.isOpened()){
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser User, Response response) {
                            if (User != null){
                                hello.setText("Hello " + User.getName() + "!");
                            }
                        }
                    });
                }
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(getActivity(), requestCode, resultCode, data);
    }

    @SuppressWarnings("unused")
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            // Respond to session state changes, ex: updating the view
        }
    }
}
