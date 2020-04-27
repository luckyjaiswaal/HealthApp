package com.example.healthapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.healthapp.adapter.RecyclerViewAdapter;
import com.example.healthapp.model.DSModel;
import com.example.healthapp.util.PopupUtil;
import com.example.healthapp.util.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class MapAndDoctorFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    ArrayList<DSModel> models = new ArrayList<>();
    private RecyclerView recyclerView;
    private FrameLayout map;
    private ImageView img_List, img_Map;
    TextView txt_one, txt_four;
    private RecyclerViewAdapter mAdapter;
    ViewGroup pickups, actDeliveries, inactDeliveries;
    ImageView pickupsImg, actDeliveriesImg, inactDeliveriesImg;
    TextView pickupsTxt, actDeliveriesTxt, inactDeliveriesTxt;
    private Marker[] markers;
    private MarkerOptions[] markerOptions;

    public MapAndDoctorFragment() {
    }

    GoogleMap mMap;
    SupportMapFragment supportMapFragment;
    private FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_map_doctor, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        map = rootView.findViewById(R.id.map);
        img_List = rootView.findViewById(R.id.img_List);
        img_Map = rootView.findViewById(R.id.img_Map);
        txt_one = rootView.findViewById(R.id.txt_one);
        txt_four = rootView.findViewById(R.id.txt_four);

        pickups = rootView.findViewById(R.id.chkPickUp);
        actDeliveries = rootView.findViewById(R.id.chkActvie);
        inactDeliveries = rootView.findViewById(R.id.chkInActvie);
        pickupsImg = rootView.findViewById(R.id.chkPickUpImg);
        actDeliveriesImg = rootView.findViewById(R.id.chkActvieImg);
        inactDeliveriesImg = rootView.findViewById(R.id.chkInActvieImg);
        pickupsTxt = rootView.findViewById(R.id.chkPickUpTxt);
        actDeliveriesTxt = rootView.findViewById(R.id.chkActvieTxt);
        inactDeliveriesTxt = rootView.findViewById(R.id.chkInActvieTxt);

        img_List.setOnClickListener(this);
        img_Map.setOnClickListener(this);
        txt_one.setOnClickListener(this);
        txt_four.setOnClickListener(this);
        pickups.setOnClickListener(this);
        actDeliveries.setOnClickListener(this);
        inactDeliveries.setOnClickListener(this);

        setAdapterList();
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        loadMapPointers();
        InfoWndowAdapter markerInfoWindowAdapter = new InfoWndowAdapter(getActivity());
        mMap.setInfoWindowAdapter(markerInfoWindowAdapter);
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                PopupUtil.showAlertPopup(getActivity(), "Place Call", "Do you want to call?", new String[]{"Yes","Cancel"}, new PopupUtil.AlertPopup() {
                    @Override
                    public void positive(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                    @Override
                    public void negative(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public class InfoWndowAdapter implements GoogleMap.InfoWindowAdapter {
        private Context context;
        public InfoWndowAdapter(Context context) {
            this.context = context.getApplicationContext();
        }
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
        @Override
        public View getInfoContents(Marker marker) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view =  inflater.inflate(R.layout.info_window, null);
            ImageView avatar = view.findViewById(R.id.img_avatar);
            ImageView call = view.findViewById(R.id.img_call);
            TextView title = view.findViewById(R.id.txtTitle);
            DSModel tagModel = (DSModel) marker.getTag();
            if(tagModel != null){
                if(tagModel.getType() == 1) {
                    avatar.setImageResource(R.drawable.patient);
                    title.setText("Patient");
                }
                else {
                    avatar.setImageResource(R.drawable.pharmacy);
                    title.setText("Pharmacy");
                }
            }
            else{
                Log.e("tag", "tagModel null");
            }

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.clickEffect(v);
                }
            });

            return view;
        }
    }

    private void loadMapPointers() {
        if (markers != null) {
            for (int pm = 0; pm < markers.length; pm++) {
                if (markers[pm] != null)
                    markers[pm].remove();
            }
        }
        markers = new Marker[models.size()];
        //marker options for each place returned
        markerOptions = new MarkerOptions[models.size()];
        //loop through markerOptions
        for (int p = 0; p < models.size(); p++) {
            markerOptions[p] = new MarkerOptions()
                    .position(new LatLng(models.get(p).getLat(), models.get(p).getLng()))
                    .title("title")
                    .snippet("address").icon(BitmapDescriptorFactory.fromResource(models.get(p).getType()==1? R.drawable.patient1 : R.drawable.pharmacy1));
        }
        if (markerOptions != null && markers != null) {
            for (int p = 0; p < markerOptions.length && p < markers.length; p++) {
                //will be null if a value was missing
                if (markerOptions[p] != null) {
                    Log.e("tag", "marker");
                    Marker marker = mMap.addMarker(markerOptions[p]);
                    marker.setTag(models.get(p));
                    markers[p] = marker;
                }
            }
        }
        if (markers.length > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
//            if(userMarker != null) {
//                builder.include(userMarker.getPosition());
//            }

            //Setting the width and height of your screen
            LatLngBounds bounds = builder.build();
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
            mMap.animateCamera((CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)));

            /*if (userMarker != null)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMarker.getPosition(), zoomLevel));
            else
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markers1[0].getPosition(), zoomLevel));*/
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        if (supportMapFragment == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().add(R.id.map, supportMapFragment).commit();
        }
        supportMapFragment.getMapAsync(this);
        setSelected(0);
    }

    @Override
    public void onClick(View v) {
        Utils.clickEffect(v);
        switch (v.getId()){
            case R.id.img_List:
                map.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                img_List.setImageResource(R.drawable.ic_list_white);
                img_List.setBackgroundResource(R.drawable.blue_right_selected);
                img_Map.setImageResource(R.drawable.ic_map_black);
                img_Map.setBackgroundResource(R.drawable.blue_left_unselected);
                break;
            case R.id.img_Map:
                map.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                img_List.setImageResource(R.drawable.ic_list_black);
                img_List.setBackgroundResource(R.drawable.blue_right_unselected);
                img_Map.setImageResource(R.drawable.ic_map_white);
                img_Map.setBackgroundResource(R.drawable.blue_left_selected);
                break;
            case R.id.txt_one:
                txt_one.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_four.setTextColor(getActivity().getResources().getColor(R.color.black));
                txt_one.setBackgroundResource(R.drawable.blue_right_selected);
                txt_four.setBackgroundResource(R.drawable.blue_left_unselected);
                break;
            case R.id.txt_four:
                txt_one.setTextColor(getActivity().getResources().getColor(R.color.black));
                txt_four.setTextColor(getActivity().getResources().getColor(R.color.white));
                txt_one.setBackgroundResource(R.drawable.blue_right_unselected);
                txt_four.setBackgroundResource(R.drawable.blue_left_selected);
                break;
            case R.id.chkPickUp:
                setSelected(0);
                break;
            case R.id.chkActvie:
                setSelected(1);
                break;
            case R.id.chkInActvie:
                setSelected(2);
                break;
        }
    }

    private void setSelected(int tab) {
        pickupsImg.setImageResource(R.drawable.ic_check_circle_grey);
        pickupsTxt.setTextColor(getResources().getColor(R.color.gray));

        actDeliveriesImg.setImageResource(R.drawable.ic_check_circle_grey);
        actDeliveriesTxt.setTextColor(getResources().getColor(R.color.gray));

        inactDeliveriesImg.setImageResource(R.drawable.ic_check_circle_grey);
        inactDeliveriesTxt.setTextColor(getResources().getColor(R.color.gray));

        if(tab == 0){
            pickupsImg.setImageResource(R.drawable.ic_check_circle_blue);
            pickupsTxt.setTextColor(getResources().getColor(R.color.gradStop));
        }
        else if(tab == 1){
            actDeliveriesImg.setImageResource(R.drawable.ic_check_circle_blue);
            actDeliveriesTxt.setTextColor(getResources().getColor(R.color.gradStop));
        }
        else if(tab == 2){
            inactDeliveriesImg.setImageResource(R.drawable.ic_check_circle_blue);
            inactDeliveriesTxt.setTextColor(getResources().getColor(R.color.gradStop));
        }
    }

    private void setAdapterList() {
        fillDummy();
        mAdapter = new RecyclerViewAdapter(getActivity(), models);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void fillDummy() {
        models.add(new DSModel(1, 30.9623586,70.8052618));
        models.add(new DSModel(2,30.9037176,70.894563));
        models.add(new DSModel(1, 30.9893016,70.986225));
        models.add(new DSModel(2, 30.927917, 70.998542));
        models.add(new DSModel(1, 30.991808, 70.961114));
    }

}