package ca.adoan.photo_gallery

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(itemList: List<CardItem>): RecyclerView.Adapter<RecyclerViewAdapter.CardItemViewHolder>() {

    private val cardItemList = itemList

    inner class CardItemViewHolder(cardItemView: View) : RecyclerView.ViewHolder(cardItemView), View.OnClickListener {
        var cardPhotographerTextView: TextView = itemView.findViewById(R.id.card_photographer_textview)
        var cardImageView: ImageView = itemView.findViewById(R.id.card_imageview)
        var cardImageDescTextView: TextView = itemView.findViewById(R.id.card_image_description_textview)
        var cardImageDetailsImgButton: ImageButton = itemView.findViewById(R.id.card_details_button)

        init {
            cardImageDetailsImgButton.setOnClickListener(this)
            cardImageView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (v == cardImageDetailsImgButton) {
                val currentCardItem = cardItemList[adapterPosition]
                val webViewIntent = Intent(v.context, WebViewActivity::class.java)
                webViewIntent.putExtra("photographerDetailsUrl", currentCardItem.cardPhotographerDetails)
                webViewIntent.putExtra("photographerName", currentCardItem.cardPhotographerName)

                if (currentCardItem.cardPhotographerDetails.isBlank())
                {
                    Toast.makeText(v.context, "Sorry, this image does not have a website associated with it.", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    v.context.startActivity(webViewIntent)
                }
            } else {
                if (!cardImageDescTextView.isVisible) {
                    cardImageDescTextView.isVisible = true
                } else {
                    cardImageDescTextView.visibility = View.GONE
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)

        return CardItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CardItemViewHolder, position: Int) {
        val currentCardItem = cardItemList[position]

        val cardPhotographerName: String = currentCardItem.cardPhotographerName
        val cardImageUrl: String = currentCardItem.cardImage
        val cardImageDesc: String = currentCardItem.cardImageDetails

        holder.cardPhotographerTextView.text = cardPhotographerName
        holder.cardImageDescTextView.text = cardImageDesc

        Picasso.get().load(cardImageUrl).placeholder(R.drawable.image_progress)
            .error(R.drawable.image_error).fit().centerCrop().into(holder.cardImageView)
    }

    override fun getItemCount(): Int {
        return cardItemList.size
    }
}
