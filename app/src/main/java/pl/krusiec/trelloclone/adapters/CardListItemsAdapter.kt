package pl.krusiec.trelloclone.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*
import pl.krusiec.trelloclone.R
import pl.krusiec.trelloclone.models.Card

open class CardListItemsAdapter(private val context: Context, private var list: ArrayList<Card>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            if(model.labelColor.isNotEmpty()){
                holder.itemView.viewLabelColor.visibility = View.VISIBLE
                holder.itemView.viewLabelColor.setBackgroundColor(Color.parseColor(model.labelColor))
            }else{
                holder.itemView.viewLabelColor.visibility = View.GONE
            }

            holder.itemView.tvCardName.text = model.name
            holder.itemView.setOnClickListener {
                if (onClickListener != null){
                    onClickListener!!.onClick(position)
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(cardPosition: Int)
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}