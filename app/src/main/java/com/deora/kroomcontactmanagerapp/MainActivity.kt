package com.deora.kroomcontactmanagerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.deora.kroomcontactmanagerapp.databinding.ActivityMainBinding
import com.deora.kroomcontactmanagerapp.room.User
import com.deora.kroomcontactmanagerapp.room.UserDatabase
import com.deora.kroomcontactmanagerapp.room.UserRepository
import com.deora.kroomcontactmanagerapp.viewUI.MyRecyclerViewAdapter
import com.deora.kroomcontactmanagerapp.viewmodel.UserViewModel
import com.deora.kroomcontactmanagerapp.viewmodel.UserViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  userViewModel: UserViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        //Room
        val dao=UserDatabase.getInstance(application).userDao
        val repository=UserRepository(dao)
        val factory=UserViewModelFactory(repository)

        userViewModel=ViewModelProvider(this,factory).get(UserViewModel::class.java)

        binding.userViewModel=userViewModel

        binding.lifecycleOwner =this

        initRecyclerView()





    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        DisplayUsersList()
    }

    private fun DisplayUsersList() {
        userViewModel.users.observe(this, Observer {

            binding.recyclerView.adapter = MyRecyclerViewAdapter(
                it,{selectedItem:User-> listItemClicked(selectedItem)}
            )

        })
    }

    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(this, "Selected name is ${selectedItem.name}", Toast.LENGTH_SHORT).show()

        userViewModel.initUpdateAndDelete(selectedItem)

    }
}