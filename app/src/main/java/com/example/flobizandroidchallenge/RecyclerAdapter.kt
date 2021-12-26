package com.example.flobizandroidchallenge

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerAdapter(val context: Context, val itemList:ArrayList<User>):
    RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int

    ): RecyclerAdapter.RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row, parent, false)

        return RecyclerViewHolder(view)
    }
    //lateinit var txtUrl:String
    override fun onBindViewHolder(holder: RecyclerAdapter.RecyclerViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtQuestionTitle.text = book.QuestionTitle
        holder.txtOwnerName.text = book.OwnerName
        holder.txtDate.text = book.Date
        holder.txtUrl=book.url
        //holder.imgBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.UserImage).into(holder.imgUserImage)

        holder.llContent.setOnClickListener {

            if (checkForInternet(context)) {
                Toast.makeText(context, "Opening in Browser.", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(book.url)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Device not connected to Internet!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }

    }


    override fun getItemCount(): Int {
        return itemList.size
    }
    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var txtUrl:String
        val txtQuestionTitle: TextView = view.findViewById(R.id.txtQuestionTitle)
        val txtOwnerName: TextView = view.findViewById(R.id.txtOwnerName)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val imgUserImage: ImageView = view.findViewById(R.id.imgUserImage)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }
}