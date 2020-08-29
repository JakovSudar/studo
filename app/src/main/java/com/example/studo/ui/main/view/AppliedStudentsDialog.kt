package com.example.studo.ui.main.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.iterator
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studo.R
import com.example.studo.data.model.Job
import com.example.studo.data.model.response.ApplicationResponse
import com.example.studo.ui.main.adapter.ApplicationsAdapter
import com.example.studo.ui.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.applied_students_dialog.*


class AppliedStudentsDialog(val job: Job): DialogFragment() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var adapter : ApplicationsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.applied_students_dialog,container,false)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpRecycler()
        renderList(job.applications!!)
        populateViews()
        setUpUi()

    }

    private fun setUpUi() {
        btn_mail.setOnClickListener {
            var mails = getCheckedMails()
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse(mails)
            }
            startActivity(Intent.createChooser(emailIntent, "Send feedback"))
        }
        btn_choseAll.setOnClickListener {
            if(rv_appliedStudents.children.first().findViewById<CheckBox>(R.id.checkbox).isChecked){
                for (application in rv_appliedStudents.children){
                    application.findViewById<CheckBox>(R.id.checkbox).isChecked = false
                    application.setBackgroundResource(R.drawable.border)
                }
            }else{
                for (application in rv_appliedStudents.children){
                    application.findViewById<CheckBox>(R.id.checkbox).isChecked = true
                    application.setBackgroundResource(R.drawable.border_choosen)
                }
            }
        }
    }

    private fun getCheckedMails(): String {
        var emails: String = "mailto:"
        for (application in rv_appliedStudents.children){
            if (application.findViewById<CheckBox>(R.id.checkbox).isChecked){
                emails = emails + application.findViewById<TextView>(R.id.tv_email).text +","
            }
        }
        return emails
    }

    private fun populateViews() {
        tv_jobTitle.text = "Posao: " + job.title
        tv_numberOfApplications.text = "Broj prijavljenih: " + job.applications?.size.toString()
    }

    private fun setUpRecycler() {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider)!!)
        rv_appliedStudents.addItemDecoration(itemDecorator)
        rv_appliedStudents.layoutManager = LinearLayoutManager(context)
        adapter = ApplicationsAdapter()
        rv_appliedStudents.adapter = adapter
    }
    private fun setUpViewModel() {
        mainViewModel = ViewModelProviders.of(
            this.activity!!
        ).get(MainViewModel::class.java)
    }

    private fun renderList(applications: List<ApplicationResponse>) {
        adapter.addData(applications)
        adapter.notifyDataSetChanged()
    }
}