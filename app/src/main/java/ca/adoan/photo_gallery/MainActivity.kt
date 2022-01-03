package ca.adoan.photo_gallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val fullCardItemList = mutableListOf<CardItem>()
    private val itemRecyclerView: RecyclerView by lazy {findViewById(R.id.main_recyclerview)}
    private val recyclerViewAdapter = RecyclerViewAdapter(fullCardItemList)

    private val APIKEY = "uAwNzyVEae3E0fexuywHflPJA8Il2xfAkpyIwiDKejQ"
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemRecyclerView.setHasFixedSize(true)
        itemRecyclerView.layoutManager = LinearLayoutManager(this)

        val volleyRequestQueue = Volley.newRequestQueue(this)

        parseImageObject(volleyRequestQueue)

    }

    private fun parseImageObject(requestQueue: RequestQueue) {

        val stringUrl = "https://api.unsplash.com/photos?client_id=$APIKEY&page=${(1..200).random()}&per_page=15"

        val stringRequest = JsonArrayRequest(Request.Method.GET, stringUrl, null,
            {
                for (i in 0 until it.length()) {

                    val currentCardItem = CardItem((it.getJSONObject(i)))

                    fullCardItemList.add(currentCardItem)
                    itemRecyclerView.adapter = recyclerViewAdapter
                }
            },
            {
                Log.e(TAG, it.networkResponse!!.toString())
                Log.e(TAG, it.networkResponse!!.toString())
            })
        requestQueue.add(stringRequest)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu!!)
        val menuItemBackButton: MenuItem = menu.findItem(R.id.menu_item_back)
        val menuItemSearchView: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = menuItemSearchView.actionView as SearchView

        menuItemBackButton.isVisible = false
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_refresh)
        {
            val volleyRequestQueue = Volley.newRequestQueue(this)

            fullCardItemList.clear()
            parseImageObject(volleyRequestQueue)
            Toast.makeText(this@MainActivity, "Gallery refreshed with 30 new images.", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun searchFilteredList(query: String) {
        val filteredList = mutableListOf<CardItem>()
        val newAdapter = RecyclerViewAdapter(filteredList)

        if (query.isNotBlank()) {

            for (cardItem in fullCardItemList) {
                if (cardItem.cardPhotographerName.contains(query, true)
                    || cardItem.cardImageDetails.contains(query, true)) {
                    filteredList.add(cardItem)
                }
            }

            itemRecyclerView.adapter = newAdapter

            Toast.makeText(this@MainActivity, "${filteredList.size} result(s) have been found.", Toast.LENGTH_SHORT).show()
        }
        else
        {
            return
        }

    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchFilteredList(newText!!)
        return true
    }

}