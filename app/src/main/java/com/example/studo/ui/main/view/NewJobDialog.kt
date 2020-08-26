package com.example.studo.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.studo.R
import com.example.studo.data.model.Job
import com.example.studo.ui.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.new_job_dialog.*

class NewJobDialog : DialogFragment() {
    private lateinit var jobsViewModel: MainViewModel
    var hasError = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.new_job_dialog,container,false)
    }
    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.GONE
        tv_progress.visibility = View.GONE
        setUpUi()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        this.jobsViewModel = ViewModelProviders.of(this.activity!!).get(MainViewModel::class.java)
    }

    private fun setUpUi() {
        populateDropDowns()
        btn_publishJob.setOnClickListener {
            clearErrors()
            val job = getJobData()
            checkInputs(job)
            Log.d("New Job", job.toString())
            if(!hasError){
                jobsViewModel.postNewJob(job)
                dismiss()
            }else{
                scrollView.fullScroll(View.FOCUS_UP);
                scrollView.scrollTo(0,0)
                Toast.makeText(context,"Provjerite podatke!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearErrors() {
        ly_requirements.error = null
        ly_city.error = null
        ly_category.error = null
        ly_title.error = null
        ly_description.error = null
        ly_employer.error = null
        ly_wage.error = null
    }

    private fun checkInputs(job: Job) {
        hasError = false
        if (job.title.isEmpty()){
            ly_title.error= "Obavezno polje!"
            hasError = true
        }
        if(job.description.isEmpty()){
            ly_description.error= "Obavezno polje!"
            hasError = true
        }
        if (job.category.isEmpty()){
            ly_category.error= "Obavezno polje!"
            hasError = true
        }
        if(job.city.isEmpty()){
            ly_city.error= "Obavezno polje!"
            hasError = true
        }
        if (job.wage.isNaN()){
            ly_wage.error= "Obavezno polje!"
            hasError = true
        }else if (job.wage<25.38){
            ly_wage.error = "Minimalna satnica je 25.38 kn!"
        }
        if (job.requirements.isEmpty()){
            ly_requirements.error= "Obavezno polje!"
            hasError = true
        }
        if(job.employer.isEmpty()){
            ly_employer.error= "Obavezno polje!"
            hasError = true
        }
    }

    private fun getJobData(): Job {
        val title = et_title.text.toString()
        val description = et_description.text.toString()
        val category = et_category.text.toString()
        val city = et_city.text.toString()
        Log.d("wage", et_wage.text!!.toString())
        val wage : Double = if (et_wage.text.toString().isEmpty()) 0.0 else et_wage.text.toString().toDouble()
        val requirements = et_requirements.text.toString()
        val employer = et_employer.text.toString()
        return Job(-1,title,description,category,city,wage,requirements,employer,"",null,-1)
    }

    private fun populateDropDowns(){
        val caterogries = listOf("Turizam", "Fizički posao", "Ljepota i njega", "Ostalo")
        val cities = listOf("Osijek", "Zagreb", "Split", "Pula")
        var adapter = ArrayAdapter(requireContext(), R.layout.category_item,caterogries)
        (ly_category.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        adapter = ArrayAdapter(requireContext(), R.layout.category_item, cities)
        (ly_city.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

}