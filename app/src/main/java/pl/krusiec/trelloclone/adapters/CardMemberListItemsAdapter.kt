package pl.krusiec.trelloclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_card_selected_member.view.*
import pl.krusiec.trelloclone.R
import pl.krusiec.trelloclone.models.SelectedMembers

open class CardMemberListItemsAdapter(
    private val context: Context,
    private val list: ArrayList<SelectedMembers>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_card_selected_member, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            if (position == list.size - 1) {
                holder.itemView.ivAddMember.visibility = View.VISIBLE
                holder.itemView.ivSelectedMemberImage.visibility = View.GONE
            } else {
                holder.itemView.ivAddMember.visibility = View.GONE
                holder.itemView.ivSelectedMemberImage.visibility = View.VISIBLE

                Glide.with(context)
                    .load(model.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(holder.itemView.ivSelectedMemberImage)
            }

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick()
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}