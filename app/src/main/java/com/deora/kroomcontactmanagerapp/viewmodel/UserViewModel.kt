package com.deora.kroomcontactmanagerapp.viewmodel


import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Update
import com.deora.kroomcontactmanagerapp.room.User
import com.deora.kroomcontactmanagerapp.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel (private val repository: UserRepository):ViewModel(),Observable {

//     val users=repository.users
//    private var isUpdateOrDelete=false
//    private lateinit var userToUpdateOrDelete :User

   val users=repository.users
    private var isUpdateOrDelete=false
    private lateinit var userToUpdateOrDelete:User
    @Bindable
    val inputName=MutableLiveData<String?>()

    @Bindable
    val inputEmail=MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText=MutableLiveData<String>()


    @Bindable
    val clearAllOrDeleteButtonText =MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value="Save"
        clearAllOrDeleteButtonText.value   ="Clear All"
    }

    fun saveOrUpdate(){
        if(isUpdateOrDelete){
            //make update:
            userToUpdateOrDelete.name=inputName.value!!
            userToUpdateOrDelete.email=inputEmail.value!!
            update(userToUpdateOrDelete)


        }else{
            /////insert Functionallity
            val name =inputName.value!!
            val email=inputEmail.value!!

            insert(User(0,name,email))

            inputName.value=null
            inputName.value=null

        }

    }

    fun  clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(userToUpdateOrDelete)
        }else{
            clearAll()
         }
    }

    fun insert(user:User)=viewModelScope.launch {
        repository.insert(user)
    }

    fun clearAll()=viewModelScope.launch {
        repository.deleteAll()
    }
    fun update(user: User)=viewModelScope.launch {
        repository.update(user)

//Reseting the Buttons  and Fields
          inputName.value=null
         inputEmail.value=null
        isUpdateOrDelete=false
        saveOrUpdateButtonText.value="Save"
        clearAllOrDeleteButtonText.value="Clear All"




    }

    fun delete(user: User)=viewModelScope.launch {
        repository.delete(user)
        //Reseting the Buttons  and Fields
        inputName.value=null
        inputEmail.value=null
        isUpdateOrDelete=false
        saveOrUpdateButtonText.value="Save"
        clearAllOrDeleteButtonText.value="Clear All"



    }
    fun initUpdateAndDelete(user: User){

        inputName.value=user.name
        inputEmail.value=user.email
        isUpdateOrDelete=true
        userToUpdateOrDelete=user
        saveOrUpdateButtonText.value="Update"
        clearAllOrDeleteButtonText.value="Delete"




    }
//        private val users=repository.users
//          private var isUpdatedordelete=false
//       private lateinit var userToClearAllorDelete:User
//
//       @Bindable
//       val inputName=MutableLiveData<String?>()
//    @Bindable
//    val inputEmail=MutableLiveData<String?>()
//    @Bindable
//    val buttonSaveOrUpdate=MutableLiveData<String>()
//    @Bindable
//    val buttonCLearAllOrDletete =MutableLiveData<String>()
//
//    fun saveOrUpdate(){
//        if(isUpdatedordelete){
//            userToClearAllorDelete.name=inputName.value!!
//            userToClearAllorDelete.email=inputEmail.value!!
//            update(userToClearAllorDelete)
//        }else {
//            //insert
//            val name = inputName.value!!
//            val email = inputEmail.value!!
//            insert(User(0, name, email))
//
//            inputName.value=null
//            inputEmail.value=null
//        }
//    }
//    fun clearAllOrDelete(){
//        if(isUpdatedordelete){
//            delete(userToClearAllorDelete)
//        }else{
//            clearAll()
//        }
//    }
//
//    fun insert(user: User)=viewModelScope.launch{
//        repository.insert(user)
//    }
//    fun clearAll()=viewModelScope.launch {
//        repository.deleteAll()
//    }
//    fun update(user: User)=viewModelScope.launch{
//        repository.update(user)
//
//        //reset
//        inputName.value=null
//        inputEmail.value=null
//        buttonSaveOrUpdate.value="Save"
//        buttonCLearAllOrDletete.value="Clear"
//
//
//
//    }
//    fun  delete(user: User)=viewModelScope.launch{
//        repository.delete(user)
//
//        //reset
//        inputName.value=null
//        inputEmail.value=null
//        buttonSaveOrUpdate.value="Save"
//        buttonCLearAllOrDletete.value="Clear"
//
//
//    }
//


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }


}