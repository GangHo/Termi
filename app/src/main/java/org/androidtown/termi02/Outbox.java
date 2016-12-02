package org.androidtown.termi02;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Gangho on 2016-11-27.
 */
public class Outbox extends Fragment{
    private final String url = "http://52.78.240.168";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private OutBoxRvAdapter adapter;
    private ArrayList<RvItem> outBoxRvItemArrayList;

    private String token;

    //public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.outbox,container,false);

        outBoxRvItemArrayList = new ArrayList<>();
        mRecyclerView= (RecyclerView)v.findViewById(R.id.outRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new OutBoxRvAdapter(outBoxRvItemArrayList);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);

        GetOutbox getOutbox = new GetOutbox();
        token = UserToken.getPreferences(getContext(),"token");

        try{
            getOutbox.doGetRequest(url+"/api/messageList/send");

        }catch (IOException e){
            e.printStackTrace();
        }
        return v;
    }


    private class GetOutbox{
        OkHttpClient client = new OkHttpClient();

        private String doGetRequest(String url) throws IOException {
            Request request = new Request.Builder()
                    .addHeader("authorization",token)
                    .url(url)
                    .build();
            client.newCall(request).enqueue(callbackAfterOutbox);
            return null;
        }

        private Callback callbackAfterOutbox = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strJsonOutput = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonOutput = new JSONObject(strJsonOutput);
                            JSONArray jsonArray = jsonOutput.getJSONArray("messageData");

                            for(int i=0; i<jsonArray.length();i++){
                                RvItem rvItem = new RvItem();
                                JSONObject obj = jsonArray.getJSONObject(i);

                                if(obj.getString("enMessageType").equals("B")){
                                    rvItem.setImage(R.drawable.agma_mail);
                                }else if(obj.getString("enMessageType").equals("G")){
                                    rvItem.setImage(R.drawable.uhri_mail);
                                }

                                rvItem.setText(obj.getString("txContent"));

                                outBoxRvItemArrayList.add(rvItem);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }
}
