package com.edgardo.labk_fragment_1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    object ImageAssets{
        val heads: List<Int> = object : ArrayList<Int>(){
            init {
                add(R.drawable.head1)
                add(R.drawable.head2)
                add(R.drawable.head3)
                add(R.drawable.head4)
                add(R.drawable.head5)
                add(R.drawable.head6)
                add(R.drawable.head7)
                add(R.drawable.head8)
                add(R.drawable.head9)
                add(R.drawable.head10)
                add(R.drawable.head11)
                add(R.drawable.head12)
            }
        }
        val bodies : List<Int> = object : ArrayList<Int>(){
            init {
                add(R.drawable.body1)
                add(R.drawable.body2)
                add(R.drawable.body3)
                add(R.drawable.body4)
                add(R.drawable.body5)
                add(R.drawable.body6)
                add(R.drawable.body7)
                add(R.drawable.body8)
                add(R.drawable.body9)
                add(R.drawable.body10)
                add(R.drawable.body11)
                add(R.drawable.body12)
            }
        }
        val legs : List<Int> = object : ArrayList<Int>(){
            init {
                add(R.drawable.legs1)
                add(R.drawable.legs2)
                add(R.drawable.legs3)
                add(R.drawable.legs3)
                add(R.drawable.legs4)
                add(R.drawable.legs5)
                add(R.drawable.legs6)
                add(R.drawable.legs7)
                add(R.drawable.legs8)
                add(R.drawable.legs9)
                add(R.drawable.legs10)
                add(R.drawable.legs11)
                add(R.drawable.legs12)
            }
        }

        val all: List<Int> = object : ArrayList<Int>(){
            init {
                addAll(heads)
                addAll(bodies)
                addAll(legs)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.title = "Fragments"


        val  bodyFragment = BodyPartFragment.newInstance(ImageAssets.bodies, 1)
        val  headFragment = BodyPartFragment.newInstance(ImageAssets.heads, 1)
        val  legFragment = BodyPartFragment.newInstance(ImageAssets.legs, 1)
        val fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().add(R.id.fragment_layout_head,headFragment).commit()
        fragmentManager.beginTransaction().add(R.id.fragment_layout_body,bodyFragment).commit()
        fragmentManager.beginTransaction().add(R.id.fragment_layout_leg,legFragment).commit()
    }
}
