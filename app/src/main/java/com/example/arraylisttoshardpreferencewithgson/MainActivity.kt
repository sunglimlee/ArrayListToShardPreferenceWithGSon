package com.example.arraylisttoshardpreferencewithgson

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    private lateinit var mExampleList: ArrayList<ExampleItem>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mExampleAdapter: ExampleAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mEditTextFirstName: EditText
    private lateinit var mEditTextLastName: EditText
    private lateinit var mButtonInsert: Button
    private lateinit var mButtonDelete: Button
    private lateinit var mButtonSave : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createExampleList()
        mEditTextFirstName = findViewById(R.id.editText_firstname)
        mEditTextLastName = findViewById(R.id.editText_lastname)
        mButtonInsert = findViewById(R.id.button_insert)
        mButtonInsert.setOnClickListener { insertItem(0) }
        mButtonDelete = findViewById(R.id.button_delete)
        mButtonDelete.setOnClickListener { deleteItem() }
        mButtonSave = findViewById(R.id.button_save)
        mButtonSave.setOnClickListener { saveItem() }
        buildRecyclerView()

    }

    private fun saveItem() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE)
        var editor : SharedPreferences.Editor = sharedPreferences.edit()
        val gSon : Gson = Gson()
        val jSon : String = gSon.toJson(mExampleList);
        //무조건 Json으로 바꿀수 있다고? 그리고 그걸 String형태로 저장할 수 있다고?
        //어떤 객체든지 상관없이????
        editor.putString("Example Item List", jSon)
        editor.apply()
        //진짜로 이렇게 쉽게 뭐든지 저장한다고? sharedPreference객체만들고 그 책체로 Editor객체 만들어서
        //putString해서 apply하면 무슨 객체든지 SharedPreference안에 저장된다고????
    }

    private fun loadItem() {
        var sharedPreferences : SharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE)
        val gSon : Gson = Gson()
        val jSon : String? = sharedPreferences.getString("Example Item List", null)
        val type : Type = TypeToken<ArrayList<ExampleItem>>() {}.type

    }

    private fun deleteItem() {
        val i : Int = ItemForDelete()
        when (i) {
            -1 -> {
                Toast.makeText(this@MainActivity, "Record doesn't to delete. Please select different name.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                mExampleList.removeAt(i)
                mExampleAdapter.notifyItemRemoved(i)
            }
        }
    }

    private fun ItemForDelete() : Int  {
        var i : Int = 0;
        val iterator : Iterator<ExampleItem> = mExampleList.iterator()
        while (iterator.hasNext()) {
            val exampleItem : ExampleItem = iterator.next()
            if (exampleItem.getItemFirstName().equals(mEditTextFirstName.text.toString()) && exampleItem.getItemLastName().equals(mEditTextLastName.text.toString())) {
                return i
            } else {
                i++
            }
        }
        return -1
    }

        private fun insertItem(position: Int) {
            //아직 null값에 대한 처리가 안되었다.
            if (position <= mExampleList.size && mEditTextFirstName.text != null && mEditTextLastName.text != null) {
                mExampleList.add(
                    position,
                    ExampleItem(
                        mEditTextFirstName.text.toString(),
                        mEditTextLastName.text.toString(),
                        R.drawable.ic_delete
                    )
                )
                mExampleAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "${mExampleList.size} 이하의 숫자를 입력하여 주십시오.", Toast.LENGTH_SHORT)
                    .show()
                mEditTextFirstName.requestFocus()
            }
        }


        private fun buildRecyclerView() {
            mRecyclerView = findViewById(R.id.recycleview)
            mRecyclerView.setHasFixedSize(true)
            mLayoutManager = LinearLayoutManager(this)
            mRecyclerView.layoutManager = mLayoutManager
            mExampleAdapter = ExampleAdapter(mExampleList)
            mRecyclerView.adapter = mExampleAdapter
            mExampleAdapter.setItemClickListener(object : ExampleAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    super.onItemClick(position)
                    mExampleList.get(position).also { it.changeItemFirstName("Clicked") }
                    mExampleAdapter.notifyItemChanged(position)
                }

                override fun onDeleteClick(position: Int) {
                    super.onDeleteClick(position)
                    mExampleList.removeAt(position)
                    mExampleAdapter.notifyItemRemoved(position)
                }
            })

        }


        private fun createExampleList() {
            mExampleList = ArrayList()
            mExampleList.add(0, ExampleItem("Steve", "Lee", R.drawable.ic_delete))
            mExampleList.add(1, ExampleItem("Miah", "Sul", R.drawable.ic_delete))
            mExampleList.add(2, ExampleItem("Joel", "Lee", R.drawable.ic_delete))
            mExampleList.add(3, ExampleItem("Joseph", "Lee", R.drawable.ic_delete))
        }
    }