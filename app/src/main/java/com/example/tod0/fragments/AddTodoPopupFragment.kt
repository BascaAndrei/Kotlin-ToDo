package com.example.tod0.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.tod0.R
import com.example.tod0.databinding.FragmentAddTodoPopupBinding
import com.example.tod0.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText

class AddTodoPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener : DialogueNextBtnClickListener
    private var toDoData : ToDoData? = null

    fun setListener(listener : DialogueNextBtnClickListener){
        this.listener = listener
    }

    companion object{
        const val TAG = "AddTodoPopupFragment"

        @JvmStatic
        fun newInstance(taskId:String ,task:String) = AddTodoPopupFragment().apply {
            arguments = Bundle().apply{
                putString("taskId" , taskId)
                putString("task" , task)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      if (arguments !=null){
          toDoData = ToDoData(arguments?.getString("taskId").toString(),
                              arguments?.getString("task").toString()
          )
      binding.todoEt.setText(toDoData?.task)
      }
        registerEvents()
    }

    private fun registerEvents(){
       binding.todoNextBtn.setOnClickListener {
           val todoTask = binding.todoEt.text.toString()
           if (todoTask.isNotEmpty()){
               if (toDoData == null){
                   listener.onSaveTask(todoTask, binding.todoEt)
               } else {
                   toDoData?.task = todoTask
                   listener.onUpdateTask(toDoData!!, binding.todoEt)
               }
           }else{
               Toast.makeText(context , "Please type some tasks!" , Toast.LENGTH_SHORT).show()
           }
       }

        binding.todoClose.setOnClickListener {
        dismiss()
        }

     }

    interface DialogueNextBtnClickListener {
        fun onSaveTask(todo : String, todoEt : TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData , todoEt : TextInputEditText)
    }

}