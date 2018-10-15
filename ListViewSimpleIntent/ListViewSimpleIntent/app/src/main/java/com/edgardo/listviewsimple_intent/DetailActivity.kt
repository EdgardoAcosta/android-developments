package com.edgardo.listviewsimple_intent

import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var frutasIds: IntArray
    var frutaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        frutasIds = intArrayOf(R.drawable.banana, R.drawable.pinapple, R.drawable.orange,
                R.drawable.watermelon)

        val extras = intent.extras ?: return

        val frutaPos = extras.getInt("FRUTA_POSITION_LABEL")
        val frutaName = extras.getString("FRUTA_NAME_LABEL")

        frutaId = frutasIds[frutaPos]

        val imagenFruta = getDrawable(frutaId)
        image_fruta.setImageDrawable(imagenFruta)

        text_name.text = frutaName
    }
}
