package com.mlodi.sicredi.devandroidtest.ui.eventlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.mlodi.sicredi.devandroidtest.R
import com.mlodi.sicredi.devandroidtest.data.api.model.Event
import com.mlodi.sicredi.devandroidtest.databinding.ListItemEventListBinding
import com.mlodi.sicredi.devandroidtest.util.asDateString
import com.mlodi.sicredi.devandroidtest.util.asMoneyString

class EventListAdapter(private val onEventClicked: (String) -> Unit) : RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    var eventList: List<Event> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: ListItemEventListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = eventList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        LayoutInflater.from(parent.context).apply {
            return ViewHolder(ListItemEventListBinding.inflate(this, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {
            itemEventTitle.text = eventList[position].title
            itemEventDate.text = eventList[position].date.asDateString()
            itemEventPrice.text = eventList[position].price.asMoneyString()

            Glide
                .with(this.root.context)
                .load(eventList[position].image)
                .placeholder(R.drawable.ic_no_image)
                .error(R.drawable.ic_no_image)
                .centerInside()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemEventImage)
        }

        holder.binding.root.setOnClickListener { onEventClicked(eventList[position].id) }
    }
}