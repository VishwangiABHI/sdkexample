package com.example.examplelibrary

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import org.w3c.dom.Text

class ABWCard(context: Context) : ConstraintLayout(context) {

    private var view: View =
        LayoutInflater.from(context).inflate(R.layout.abmultiply_dha_card, this, true)
//    private var imageView: ImageView
//    private var titleTextView: TextView
//    private var subtitleTextView: TextView
//    private var imageDrawable : Drawable?
//    private var title: String?
//    private var subtitle: String?

    //private var cardView: CardView = view.findViewById(R.id.parentDAS)
    private var tvDash: TextView = view.findViewById(R.id.tv_dash)
    private var tvDashDscr: TextView = view.findViewById(R.id.tv_dash_dscr)
    private var tvActiveMindStart: TextView = view.findViewById(R.id.tv_active_mind_start)
    private var ivZylaImg1: ImageView = view.findViewById(R.id.iv_zyla_img1)
    private var ivZylaImg2: ImageView = view.findViewById(R.id.iv_zyla_img2)

    init {
//        imageView = view.imageView as ImageView
//        titleTextView = view.titleTextView as TextView
//        subtitleTextView = view.subtitleTextView as TextView

        //        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ABWCard, 0, 0)
//
//        try {
//            imageDrawable = typedArray.getDrawable(R.styleable.CustomView_setImageDrawable)
//            title = typedArray.getString(R.styleable.CustomView_setTitle)
//            subtitle = typedArray.getString(R.styleable.CustomView_setSubTitle)
//
//            imageView.setImageDrawable(imageDrawable)
//            titleTextView.text = title
//            subtitleTextView.text = subtitle
//        }
//        finally {
//            typedArray.recycle()
//        }

        /** Uncomment below line if all of your attribute fields are required.
         * Throw an exception if required attributes are not set. It will caused Run Time Exception.
         * In this sample project we assume that, no attributes are mandatory
         * *
         */
        /*
        if (imageDrawable == null)
            throw RuntimeException("No image drawable provided")
        if (title == null) {
            throw RuntimeException("No title provided")
        }
        if (subtitle == null) {
            throw RuntimeException("No subtitle provided")
        }*/
    }

    /**
     * Below getter-setter will work, if we need to access the attributes programmatically
     */

//    fun setImageDrawable(drawable: Drawable?) {
//        imageView.setImageDrawable(drawable)
//    }
//    fun getImageDrawable() : Drawable? {
//        return imageDrawable
//    }
//
//    fun setTitle(text: String?) {
//        titleTextView.text = text
//    }
//    fun getTitle() : String? {
//        return title
//    }
//
//    fun setSubtitle(text: String?) {
//        subtitleTextView.text = text
//    }
//    fun getSubtitle() : String? {
//        return subtitle
//    }
}