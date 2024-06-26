package com.zuzudev.advweek4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.zuzudev.advweek4.R
import com.zuzudev.advweek4.databinding.FragmentStudentDetailBinding
import com.zuzudev.advweek4.model.Student
import com.zuzudev.advweek4.util.loadImage
import com.zuzudev.advweek4.viewmodel.DetailViewModel
import com.zuzudev.advweek4.viewmodel.ListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class StudentDetailFragment : Fragment(), ButtonDetailCLickListener {
    private lateinit var binding:FragmentStudentDetailBinding
    private lateinit var viewModel:DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.student = Student("","","","","https://randomuser.me/api/portraits/men/73.jpg")
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        var id = ""
        if(arguments != null){
            id = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId
        }

        viewModel.fetch(id)
        observeViewModel()

    }

    fun observeViewModel(){
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
//            binding.txtId.setText(it.id)
            binding.student = it //
            binding.listener = this //untuk button listener

            //NOTIF
            var student = it
            binding.btnUpdate?.setOnClickListener {
                Observable.timer(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.d("Messages", "five seconds")
                        MainActivity.showNotification(student.name.toString(),
                            "A new notification created",
                            R.drawable.baseline_person_add_24)
                    }
            }
//            binding.txtId.setText(binding.student.id)
//            binding.txtName.setText( binding.student.name)
//            binding.txtDob.setText( binding.student.dob)
//            binding.txtPhone.setText( binding.student.phone)


//            val picasso = Picasso.Builder(requireContext())
//            picasso.listener { picasso, uri, exception ->
//                exception.printStackTrace()
//            }
//
//            picasso.build().load(it.photoUrl)
//                .into(binding.imageView, object: Callback {
//                    override fun onSuccess() {
//                        binding.progressBar4.visibility = View.INVISIBLE
//                        binding.imageView.visibility = View.VISIBLE
//                    }
//                    override fun onError(e: Exception?) {
//                        Log.e("picasso_error", e.toString())
//                    }
//                })
//            var imageView = binding.imageView
//            var progressBar = binding.progressBar4
//            imageView.loadImage(it.photoUrl, progressBar)
        })
    }

    override fun onButtonDetailClick(v: View) {
        Toast.makeText(v.context, "Updated", Toast.LENGTH_SHORT).show()
    }
}