package com.huawei.solarsafe.view.stationmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.City;
import com.huawei.solarsafe.bean.DataConstants;
import com.huawei.solarsafe.bean.PositionEntity;
import com.huawei.solarsafe.utils.SearchHelper;
import com.huawei.solarsafe.utils.customview.QuickSideBarTipsView;
import com.huawei.solarsafe.utils.customview.QuickSideBarView;
import com.huawei.solarsafe.view.BaseActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by p00507
 * on 2017/9/21.
 */

public class SelectCityActivity extends BaseActivity implements QuickSideBarView.OnQuickSideBarTouchListener, View.OnClickListener, AdapterView.OnItemClickListener{
    public static final String TAG = "SelectCityActivity";
    private ListView listView;
    private HashMap<String, Integer> letters = new HashMap<>();
    private QuickSideBarView quickSideBarView;
    private QuickSideBarTipsView quickSideBarTipsView;
    private SearchView mSearchView;
    private List<City> cityList;
    private CityListAdapter cityListAdapter;
    private SearchHelper searchHelper;
    private TextView nowCity;
    private List<City> tempCitys;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.cityname_listview);
        listView.setOnItemClickListener(this);
        cityListAdapter = new CityListAdapter();
        quickSideBarView = (QuickSideBarView) findViewById(R.id.select_quickSideBarView);
        quickSideBarTipsView = (QuickSideBarTipsView) findViewById(R.id.select_quickSideBarTipsView);
        quickSideBarView.setOnQuickSideBarTouchListener(this);
        mSearchView = (SearchView) findViewById(R.id.city_seleter_search);
        initSearchView();
        cityList = new ArrayList<>();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (cityList == null || cityList.size() == 0) {
                    return false;
                }
                String searchCharacter = newText.trim();
                tempCitys = new ArrayList<>();
                for (City city : cityList) {
                    if (searchHelper.searchResult(city.getCityName(), searchCharacter)) {
                        tempCitys.add(city);
                    }
                }
                cityListAdapter.setCityList(tempCitys);
                cityListAdapter.notifyDataSetChanged();
                return true;
            }
        });
        searchHelper = SearchHelper.getInstance();
        nowCity = (TextView) findViewById(R.id.tv_now_at_city);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                PositionEntity positionEntity = (PositionEntity) intent.getSerializableExtra("locationPositionEntity");
                //【解DTS单】 DTS2018012301891 修改人：江东
                if (positionEntity!=null&&positionEntity.city != null) {
                    String replace = positionEntity.city.replace(getResources().getString(R.string.city), "");
                    nowCity.setText(replace);
                } else {
                    nowCity.setText(getResources().getString(R.string.no_location_to));
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        if (tempCitys != null) {
            intent.putExtra("cityname", tempCitys.get(position).getCityName());
        } else {
            intent.putExtra("cityname", cityList.get(position).getCityName());
        }
        setResult(200, intent);
        SelectCityActivity.this.finish();
    }

    class CityListAdapter extends BaseAdapter {
        private List<City> cityList;

        public void setCityList(List<City> cityList) {
            this.cityList = cityList;
        }

        @Override
        public int getCount() {
            return cityList == null ? 0 : cityList.size();
        }

        @Override
        public Object getItem(int position) {
            return cityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(SelectCityActivity.this).inflate(R.layout.city_view_item, null);
            TextView head = (TextView) convertView.findViewById(R.id.head);
            TextView cityName = (TextView) convertView.findViewById(R.id.city_name);
            final City city = cityList.get(position);
            if (city.isHead()) {
                head.setVisibility(View.VISIBLE);
                head.setText(city.getFirstLetter());
                cityName.setText(city.getCityName());
            } else {
                head.setVisibility(View.GONE);
                cityName.setText(city.getCityName());
            }
            return convertView;
        }
    }

    @Override
    public void onLetterChanged(String letter, int position, float y) {
        quickSideBarTipsView.setText(letter, position, y);
        //有此key则获取位置并滚动到该位置
        if (letters.containsKey(letter)) {
            listView.setSelection(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
        quickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.INVISIBLE);
    }
    /**
     * 初始化searchView控件数据
     */
    private void initSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setIconified(true);
        mSearchView.onActionViewExpanded();
        //获取搜索框的TextView进行设置
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (EditText) mSearchView.findViewById(id);

        textView.setTextColor(getResources().getColor(R.color.textview_text_group_homepage_item_tvcolor));
        textView.setTextSize(15);
        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(200)});
        textView.setHintTextColor(getResources().getColor(R.color.COLOR_737373));
        int search_mag_icon_id = mSearchView.getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView mSearchViewIcon = (ImageView) mSearchView.findViewById(search_mag_icon_id);// 获取搜索图标
        mSearchViewIcon.setImageResource(R.drawable.search_icon);
        mSearchView.setIconifiedByDefault(false);
        //通过反射获取SearchView的属性，进行设置
        try {
            Class<?> argClass = mSearchView.getClass();
            // 指定某个私有属性
            // 所以不能用BitmapDrawable
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            // setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
            ownField.setAccessible(true);
            LinearLayout mView = (LinearLayout) ownField.get(mSearchView);
            LinearLayout.LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(-15, 0, 0, 0);
            mView.setLayoutParams(params);
            mView.setBackground(getResources().getDrawable(R.drawable.search_bordershape));
            Field mCloseButton = argClass.getDeclaredField("mCloseButton");
            mCloseButton.setAccessible(true);
            ImageView backView = (ImageView) mCloseButton.get(mSearchView);
            backView.setImageResource(R.drawable.icon_clear);
        } catch (Exception e) {
            Log.e(TAG, "set searchview param", e);
        }
        mSearchView.setFocusable(false);
        mSearchView.clearFocus();
    }
    /**
     * 初始化城市数据源
     */
    private void initData() {
        //GSON解释出来
        Type listType = new TypeToken<LinkedList<City>>() {
        }.getType();
        Gson gson = new Gson();
        LinkedList<City> cities = gson.fromJson(DataConstants.cityDataList, listType);
        ArrayList<String> customLetters = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            String letter = cities.get(i).getFirstLetter();
            //如果没有这个key则加入并把位置也加入
            if (!letters.containsKey(letter)) {
                letters.put(letter, i);
                customLetters.add(letter);
                City city = cities.get(i);
                city.setIsHead(true);
                cityList.add(city);
            } else {
                cityList.add(cities.get(i));
            }
        }
        //不自定义则默认26个字母
        quickSideBarView.setLetters(customLetters);
        cityListAdapter.setCityList(cityList);
        listView.setAdapter(cityListAdapter);
    }
}
