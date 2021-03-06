package org.ole.planet.myplanet.courses;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.ole.planet.myplanet.Data.realm_courseProgress;
import org.ole.planet.myplanet.Data.realm_myCourses;
import org.ole.planet.myplanet.Data.realm_rating;
import org.ole.planet.myplanet.R;
import org.ole.planet.myplanet.base.BaseRecyclerFragment;
import org.ole.planet.myplanet.callback.OnCourseItemSelected;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class MyCourseFragment extends BaseRecyclerFragment<realm_myCourses> implements OnCourseItemSelected {

    TextView tvAddToLib;

    EditText etSearch;
    ImageView imgSearch;
    AdapterCourses adapterCourses;

    public MyCourseFragment() {
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_my_course;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        HashMap<String, JsonObject> map = realm_rating.getRatings(mRealm, "course");
        HashMap<String, JsonObject> progressMap = realm_courseProgress.getCourseProgress(mRealm,model.getId());
        adapterCourses = new AdapterCourses(getActivity(), getList(realm_myCourses.class), map);
        adapterCourses.setProgressMap(progressMap);
        adapterCourses.setListener(this);
        return adapterCourses;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvAddToLib = getView().findViewById(R.id.tv_add_to_course);
        tvAddToLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToMyList();
            }
        });
        etSearch = getView().findViewById(R.id.et_search);
        getView().findViewById(R.id.tl_tags).setVisibility(View.GONE);
        imgSearch = getView().findViewById(R.id.img_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterCourses.setCourseList(search(etSearch.getText().toString(), realm_myCourses.class));
            }
        });
    }

    @Override
    public void onSelectedListChange(List<realm_myCourses> list) {
        this.selectedItems = list;
        changeButtonStatus();
    }

    private void changeButtonStatus() {
        tvAddToLib.setEnabled(selectedItems.size() > 0);
    }
}