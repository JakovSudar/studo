package com.example.studo.ui.main.adapter


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.studo.R
import com.example.studo.data.model.response.ApplicationResponse
import com.example.studo.helpers.StudoApplication
import kotlinx.android.synthetic.main.application_item.view.*


class ApplicationsAdapter() : RecyclerView.Adapter<ApplicationsAdapter.DataViewHolder>() {
    var applications = ArrayList<ApplicationResponse>()


    class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)  {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(application: ApplicationResponse){
            itemView.tv_aboutMe.text = application.about_me
            itemView.tv_email.text = application.email
            itemView.tv_phone.text = application.phone

            itemView.setOnClickListener{
                itemView.checkbox.isChecked = !itemView.checkbox.isChecked
                if(itemView.checkbox.isChecked)
                    it.setBackgroundResource(R.drawable.border_choosen)
                else
                    it.setBackgroundResource(R.drawable.border)
            }

            itemView.setOnLongClickListener{
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:" + application.phone)
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(StudoApplication.ApplicationContext,callIntent,null)
                true
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
                return DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.application_item,parent,false
                    )
                )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(applications[position])

    fun addData(applications: List<ApplicationResponse>){
        this.applications.addAll(applications)
    }


    override fun getItemCount(): Int {
        return applications.size
    }


}