package com.example.arraylisttoshardpreferencewithgson

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExampleAdapter(var mExampleList : ArrayList<ExampleItem>) :
    RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>()
{   private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int) {

        }
        fun onDeleteClick(position: Int) {

        }
    }
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        mListener = onItemClickListener
    }

    class ExampleViewHolder(itemView : View, listener : OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var mTextViewFirstName : TextView = itemView.findViewById(R.id.textview_firstname)
        var mTextViewLastName : TextView = itemView.findViewById(R.id.textview_lastname)
        var mImageViewDelete : ImageView = itemView.findViewById(R.id.imageview_delete)

        init {
            mImageViewDelete.setOnClickListener {
                if (listener != null) {
                    val position : Int = adapterPosition //Adapter의 Position을 받고
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position)
                    }
                }
            }
            itemView.setOnClickListener {
                if (listener != null) {
                    val position : Int = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        //새로운 view를 생성하게 하는 이벤트 함수였고 여기서 ExampleViewHolder가 만들어지고 여기서 inflate된 view와 맨꼭대기에서 받은 mListener가 넘겨온다.
        var v : View = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        val exampleViewHolder = ExampleViewHolder(v, mListener).also { return it }
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        //각각의 값들을 집어 넣어주는 역할을 하는 이벤트 함수였었다.
        val exampleItem : ExampleItem = mExampleList.get(position)
        holder.mTextViewFirstName?.text = exampleItem.getItemFirstName()
        holder.mTextViewLastName?.text = exampleItem.getItemLastName()
        //Log.e("LSL", "${exampleItem.getImageSource().toString()} 입니다.")
        holder.mImageViewDelete.setImageResource(exampleItem.getImageSource())
    }

    override fun getItemCount(): Int {
        return mExampleList.size
    }

}
