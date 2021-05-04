package com.fatihhernn.kotlinulkeleruygulamasi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fatihhernn.kotlinulkeleruygulamasi.R
import com.fatihhernn.kotlinulkeleruygulamasi.databinding.FragmentCountryBinding
import com.fatihhernn.kotlinulkeleruygulamasi.util.downloadFromUrl
import com.fatihhernn.kotlinulkeleruygulamasi.util.placeHolderProgressBar
import com.fatihhernn.kotlinulkeleruygulamasi.viewModel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*
import kotlinx.android.synthetic.main.fragment_feed.*


class CountryFragment : Fragment() {

    private var countryUuid=0
    private  lateinit var viewModel:CountryViewModel

    private lateinit var dataBinding:FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_country,container,false)
        return dataBinding.root;
        //return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUuid=CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel=ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)


         observerLiveData()
    }

    private fun observerLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
            country?.let {

                dataBinding.selectedCountry=it

                /*
                countryName.text=country.countryName
                countryCapital.text=country.countryCapital
                countryCurrency.text=country.countryCurrency
                countryLanguage.text=country.countryLanguage
                countryRegion.text=country.countryRegion
                context?.let {
                    countryFlagImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))
                }

                 */

            }
        })
    }


}