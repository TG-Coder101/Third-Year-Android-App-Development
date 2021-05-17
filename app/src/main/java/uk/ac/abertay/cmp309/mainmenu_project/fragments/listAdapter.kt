package uk.ac.abertay.cmp309.mainmenu_project.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_row.view.*
import uk.ac.abertay.cmp309.mainmenu_project.R
import uk.ac.abertay.cmp309.mainmenu_project.fragments.model.Student

class ListAdapter(private val onClick: (Student) -> Unit) : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<Student>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater.inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position] //get position of student list to get current item
        holder.itemView.id_text.text = currentItem.id.toString()    //get id's from custom row layout
        holder.itemView.firstName_txt.text = currentItem.firstName
        holder.itemView.lastName_txt.text = currentItem.lastName
        holder.itemView.yearGroup_txt.text = currentItem.yearGroup.toString()
        holder.itemView.age_txt.text = currentItem.age.toString()

        holder.itemView.rowLayout.setOnClickListener {
            onClick(currentItem)
        }
    }

    //get the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(student: List<Student>) { //set text view text
        this.userList = student //set student list with value passed in by parameters
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}




