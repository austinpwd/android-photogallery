package ca.adoan.photo_gallery

import org.json.JSONObject

class CardItem(response: JSONObject) {

    private val jsonUserObject: JSONObject = response.getJSONObject("user")
    private val jsonImageObject: JSONObject = response.getJSONObject("urls")
    private val name: String = jsonUserObject.getString("name")
    private val photographerDetails: String = jsonUserObject.getString("portfolio_url")
    private val regular: String = jsonImageObject.getString("regular").replace("fm=jpg", "fm=webp")


    val cardPhotographerName: String = name
    val cardPhotographerDetails: String = photographerDetails
    val cardImage: String = regular
    val cardImageDetails: String = response.getString("alt_description")
}

