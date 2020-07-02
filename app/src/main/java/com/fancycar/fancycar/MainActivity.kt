package com.fancycar.fancycar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var carListModel: CarList
    private lateinit var sortAdapter: ArrayAdapter<CharSequence>
    private val adapter: CarListAdapter = CarListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ArrayAdapter.createFromResource(
            this,
            R.array.car_name_sort_options,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            sortAdapter = it
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            nameSort.adapter = sortAdapter
        }
        nameSort.onItemSelectedListener = this

        ArrayAdapter.createFromResource(
            this,
            R.array.filter,
            android.R.layout.simple_spinner_dropdown_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            inDealerSort.adapter = it
        }
        inDealerSort.onItemSelectedListener = this

        carsListView.adapter = adapter
        carsListView.layoutManager = LinearLayoutManager(this)
        carListModel = ViewModelProvider(this).get(CarList::class.java)
        carListModel.getCarList().observe(this, Observer<List<CarDetail>>{ list ->
            loading.visibility = View.GONE
            if (list != null) {
                adapter.updateList(list)
                mainList.visibility = View.VISIBLE
            } else {
                errorGroup.visibility = View.VISIBLE
            }

        })
        reload.setOnClickListener { loadData() }

        loadData()
    }


    private fun loadData() {
        loading.visibility = View.VISIBLE
        errorGroup.visibility = View.GONE
        carListModel.loadCarList()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent == nameSort) {
            if (position > 0) {
                carListModel.sortName(position == 1)
            } else {
                carListModel.clearSort()
            }
        } else {
            carListModel.filter(position == 1)
        }
    }


}