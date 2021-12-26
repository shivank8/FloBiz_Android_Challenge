package com.example.flobizandroidchallenge.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.flobizandroidchallenge.R
import com.example.flobizandroidchallenge.RecyclerAdapter
import com.example.flobizandroidchallenge.User
import org.json.JSONException
import java.math.RoundingMode

class MainFragment : Fragment() {

    lateinit var recyclerDashboard: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: RecyclerAdapter

    lateinit var progressLayout: RelativeLayout

    lateinit var progressBar: ProgressBar
    lateinit var txtViewCount: TextView
    lateinit var txtAnsCount: TextView
    var viewCount:Int=0
    var ansCount:Int=0
    var count:Int=1
    val bookInfoList = arrayListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.main_fragment, container, false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)

        progressLayout = view.findViewById(R.id.progressLayout)

        progressBar = view.findViewById(R.id.progressBar)

        progressLayout.visibility = View.VISIBLE

        layoutManager = LinearLayoutManager(activity)

        txtViewCount=view.findViewById(R.id.txtViewCount)
        txtAnsCount=view.findViewById(R.id.txtAnsCount)




        val queue = Volley.newRequestQueue(activity as Context)

        val url =
            "https://api.stackexchange.com/2.2/questions?key=ZiXCZbWaOwnDgpVT9Hx8IA((&order=desc&sort=activity&site=stackoverflow"


        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                // Here we will handle the response
                try {
                    progressLayout.visibility = View.GONE
                        val data = it.getJSONArray("items")
                        for (i in 0 until data.length()) {
                            val bookJsonObject = data.getJSONObject(i)
                            viewCount+=bookJsonObject.getInt("view_count")
                            ansCount+=bookJsonObject.getInt("answer_count")
                            println("viewCount=$viewCount anscount=$ansCount and count=$count")
                            count++
                            var avgV = viewCount.toDouble() / count
                            var avgA = ansCount.toDouble() / count

                            avgV = avgV.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
                            avgA = avgA.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
                            txtViewCount.setText(avgV.toString())
                            txtAnsCount.setText(avgA.toString())
                            val user = bookJsonObject.getJSONObject("owner")
                            val display_name: String = user.getString("display_name")
                            val profile_image: String = user.getString("profile_image")
                            val bookObject = User(
                                bookJsonObject.getString("title"),
                                display_name,
                                bookJsonObject.getString("creation_date"),
                                profile_image,
                                bookJsonObject.getString("link")
                            )
                            bookInfoList.add(bookObject)
                            recyclerAdapter = RecyclerAdapter(activity as Context, bookInfoList)

                            recyclerDashboard.adapter = recyclerAdapter

                            recyclerDashboard.layoutManager = layoutManager

                        }


                } catch (e: JSONException) {
                    Toast.makeText(
                        activity as Context,
                        "Some unexpected eexception occurred!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }, Response.ErrorListener {

                //Here we will handle the errors
                if (activity != null) {
                    Toast.makeText(
                        activity as Context,
                        "Volley error occurred!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }){}

        return view

    }

}