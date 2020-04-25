package com.example.healthapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthapp.adapter.RecyclerViewAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapAndDoctorFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private RecyclerView recyclerView;
    private FrameLayout map;
    private ImageView img_List, img_Map;
    private RecyclerViewAdapter mAdapter;

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

        img_List.setOnClickListener(this);
        img_Map.setOnClickListener(this);
        setAdapterList();
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
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
    }

    @Override
    public void onClick(View v) {
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
        }
    }

    private void setAdapterList() {
        mAdapter = new RecyclerViewAdapter(getActivity(), R.layout.item_doctor_list) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//                final SafeJSONObject object = getItem(i);
//                Logger.debug("object status " + object);
//                AdapterViewHolder vh = (AdapterViewHolder) viewHolder;
//                vh.getTextView(R.id.lblAlerts).setText(object.getString("alarm"));
//                vh.getTextView(R.id.lblAlertTime).setText(object.getString("alertTime"));
//                vh.getTextView(R.id.lblAlertDate).setText(object.getString("alertDate"));
//                vh.getTextView(R.id.lblCarNumber).setText(carNumber);
//                vh.getTextView(R.id.lblModelNumber).setText(object.getString("vehicle"));
//                vh.getConstraintLayout(R.id.layoutItem).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        startActivity(new Intent(getActivity(), AlertsDetailMapActivity.class).putExtra("object", object.toString()));
//                    }
//                });
            }

//            @Override
//            public int getItemViewType(int position) {
//                return position;
//            }

            @Override
            public int getItemCount() {
                return 5;
            }
        };
        recyclerView.setAdapter(mAdapter);
//        mAdapter.setItemsData(new SafeJSONArray());
    }

}