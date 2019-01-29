package com.xwc.coolweather.ui.area.weight

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.xwc.coolweather.R
import com.xwc.coolweather.bean.City
import com.xwc.coolweather.bean.County
import com.xwc.coolweather.bean.Province
import com.xwc.coolweather.enums.LevelEnum
import com.xwc.coolweather.util.HttpUtil
import com.xwc.coolweather.util.Utility
import com.xwc.coolweather.weight.LoadingDialog
import okhttp3.Call
import okhttp3.Response
import org.litepal.crud.DataSupport
import timber.log.Timber
import java.io.IOException

/**
 * Description：选择地区
 * author：Smoker
 * 2019/1/29 14:02
 */
class ChooseAreaFragment : Fragment() {
    private var titleText: TextView? = null
    private var backButton: ImageView? = null
    private var listView: ListView? = null
    private var loadingDialog: LoadingDialog? = null

    private var adapter: ArrayAdapter<String>? = null
    private var dataList: MutableList<String> = ArrayList()        // 服务器拿到的数据
    private var provinceList: MutableList<Province> = ArrayList()  // 省列表
    private var cityList: MutableList<City> = ArrayList()          // 市列表
    private var countyList: MutableList<County> = ArrayList()      // 县列表

    private var selectedProvince = Province()               // 选中的省
    private var selectedCity = City()                       // 选中的市
    private var currentLevel = LevelEnum.LEVEL_PROVINCE     // 当前选中的级别

    private var mView: View? = null
    private var mContext: Context? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_choose_area, container, false)
        mContext = context
        initView()
        return mView
    }

    private fun initView() {
        titleText = mView?.findViewById(R.id.tv_title_text)
        backButton = mView?.findViewById(R.id.btn_back)
        listView = mView?.findViewById(R.id.list_view)
        loadingDialog = LoadingDialog(mContext!!)
        adapter = ArrayAdapter(mContext!!, android.R.layout.simple_list_item_1, dataList)
        listView?.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listView?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            if (currentLevel == LevelEnum.LEVEL_PROVINCE) {
                selectedProvince = provinceList[position]
                queryCities()
            } else if (currentLevel == LevelEnum.LEVEL_CITY) {
                selectedCity = cityList[position]
                queryCounties()
            }
        }
        backButton?.setOnClickListener{
            if (currentLevel == LevelEnum.LEVEL_COUNTY) {
                queryCities()
            } else if (currentLevel == LevelEnum.LEVEL_CITY) {
                queryProvinces()
            }
        }
        queryProvinces()
    }

    /**
     * 查询全国所有的省，先从数据库查，若没有再到服务器上查询
     * */
    private fun queryProvinces() {
        titleText?.text = "中国"
        backButton?.visibility = View.GONE
        provinceList = DataSupport.findAll(Province::class.java)
        if (provinceList.isNotEmpty()) {
            dataList.clear()
            for (province in provinceList) {
                dataList.add(province.provinceName)
            }
            adapter?.notifyDataSetChanged()
            listView?.setSelection(0)
            currentLevel = LevelEnum.LEVEL_PROVINCE
        } else {
            val address = "http://guolin.tech/api/china"
            queryFromServer(address, LevelEnum.LEVEL_PROVINCE)
        }
    }

    /**
     * 查询选中省的所有市，先从数据库查，若没有再到服务器上查询
     * */
    private fun queryCities() {
        titleText?.text = selectedProvince.provinceName
        backButton?.visibility = View.VISIBLE
        cityList = DataSupport.where("provinceid = ${selectedProvince.id}").find(City::class.java)
        Timber.e("cityList:$cityList")
        if (cityList.isNotEmpty()) {
            dataList.clear()
            for (city in cityList) {
                dataList.add(city.cityName)
            }
            adapter?.notifyDataSetChanged()
            listView?.setSelection(0)
            currentLevel = LevelEnum.LEVEL_CITY
        } else {
            val address = "http://guolin.tech/api/china/${selectedProvince.provinceCode}"
            queryFromServer(address, LevelEnum.LEVEL_CITY)
        }
    }

    /**
     * 查询选中市内所有县，先从数据库查，若没有再到服务器上查询
     * */
    private fun queryCounties() {
        titleText?.text = selectedCity.cityName
        backButton?.visibility = View.VISIBLE
        countyList = DataSupport.where("cityid = ${selectedCity.id}").find(County::class.java)
        if (countyList.isNotEmpty()) {
            dataList.clear()
            for (county in countyList) {
                dataList.add(county.countyName)
            }
            adapter?.notifyDataSetChanged()
            listView?.setSelection(0)
            currentLevel = LevelEnum.LEVEL_COUNTY
        } else {
            val address = "http://guolin.tech/api/china/${selectedProvince.provinceCode}/${selectedCity.cityCode}"
            queryFromServer(address, LevelEnum.LEVEL_COUNTY)
        }
    }

    /**
     * 从服务器上查询省市县数据
     *
     * @param address 服务器地址
     * @param type 0：省 1：市 2：县
     * */
    private fun queryFromServer(address: String, type: Int) {
        loadingDialog?.show()
        HttpUtil.sendOkHttpRequest(address, object : okhttp3.Callback{
            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body()?.string() ?: ""
                Timber.e("responseText:$responseText")
                val result = when (type) {
                    LevelEnum.LEVEL_PROVINCE -> Utility.handleProvinceResponse(responseText)
                    LevelEnum.LEVEL_CITY -> Utility.handleCityResponse(responseText, selectedProvince.id)
                    else -> Utility.handleCountyResponse(responseText, selectedCity.id)
                }
                Timber.e("$result")
                if (result) {
                    activity?.runOnUiThread {
                        kotlin.run {
                            loadingDialog?.dismiss()
                            when (type) {
                                LevelEnum.LEVEL_PROVINCE -> queryProvinces()
                                LevelEnum.LEVEL_CITY -> queryCities()
                                else -> queryCounties()
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                activity?.runOnUiThread {
                    kotlin.run {
                        loadingDialog?.dismiss()
                        Toast.makeText(mContext, "加载失败", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}