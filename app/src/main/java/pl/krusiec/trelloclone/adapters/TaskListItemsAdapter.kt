package pl.krusiec.trelloclone.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*
import pl.krusiec.trelloclone.R
import pl.krusiec.trelloclone.activities.TaskListActivity
import pl.krusiec.trelloclone.models.Task

open class TaskListItemsAdapter(private val context: Context, private var list: ArrayList<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        val layoutParams = LinearLayout.LayoutParams((parent.width * 0.7).toInt(), LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins((15.toDp()).toPx(), 0, (40.toDp()).toPx(), 0)
        view.layoutParams = layoutParams
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder){
            if (position == list.size - 1){
                holder.itemView.tvAddTaskList.visibility = View.VISIBLE
                holder.itemView.llTaskItem.visibility = View.GONE
            } else {
                holder.itemView.tvAddTaskList.visibility = View.GONE
                holder.itemView.llTaskItem.visibility = View.VISIBLE
            }

            holder.itemView.tvTaskListTitle.text = model.title
            holder.itemView.tvAddTaskList.setOnClickListener {
                holder.itemView.tvAddTaskList.visibility = View.GONE
                holder.itemView.cvAddTaskListName.visibility = View.VISIBLE
            }

            holder.itemView.ibCloseListName.setOnClickListener {
                holder.itemView.tvAddTaskList.visibility = View.VISIBLE
                holder.itemView.cvAddTaskListName.visibility = View.GONE
            }

            holder.itemView.ibDoneListName.setOnClickListener {
                val listName = holder.itemView.etTaskListName.text.toString()

                if (listName.isNotEmpty()){
                    if (context is TaskListActivity){
                        context.createTaskList(listName)
                    }
                }else{
                    Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}