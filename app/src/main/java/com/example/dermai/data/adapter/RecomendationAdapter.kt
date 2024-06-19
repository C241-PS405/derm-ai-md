package com.example.dermai.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dermai.R
import com.example.dermai.data.pref.RecomendationModel

class RecomendationAdapter(private val recommendations: List<RecomendationModel>) :
    RecyclerView.Adapter<RecomendationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_list)
        val titleView: TextView = view.findViewById(R.id.tv_title_skin)
        val descriptionView: TextView = view.findViewById(R.id.tv_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recomendation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recommendation = recommendations[position]
        holder.imageView.setImageResource(recommendation.imageResourceId)
        holder.titleView.text = recommendation.title
        holder.descriptionView.text = recommendation.description
    }

    override fun getItemCount() = recommendations.size
}