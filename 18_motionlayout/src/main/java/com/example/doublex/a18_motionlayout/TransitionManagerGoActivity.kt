package com.example.doublex.a18_motionlayout

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Scene
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_go.*

class TransitionManagerGoActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go)
        bindData()
    }

    private fun bindData() {

        findViewById<RatingBar>(R.id.rating_film_rating).rating = 4.5f
        findViewById<TextView>(R.id.text_film_title).text = getString(R.string.film_title)
        findViewById<TextView>(R.id.text_film_description).text = getString(R.string.film_description)
        findViewById<ImageView>(R.id.image_film_cover).setOnClickListener(this)
    }

    private var toggle = true


    /**
     * 过渡动画TransitionManger 的另外一种方式 TransitionManager.go（）
     * 这种方式可以将 开始场景 和 结束场景 的信息放在不通的xml中，然后通过Scene获取
     * 根据需求加载不通的Scene来完成呢过渡动画
     *
     * 特别注意：
     *      这种方式完成的动画后需要从新绑定数据到view上
     *
     * 通过代码看原理：
     *      TransitionManager.go(scene)->changeScene(scene, sDefaultTransition)
     *
     *      和beginDelayedTransition()这种方式一样会记录场景，加入监听唯一不同的是中间的一个方法
     *
     *      scene.enter()->此方法会调用removeAllView 然后在 addView。所以之前绑定的数据就无效了，动画执行完后需要从新绑定一次
     *
     *
     *
     *
     *
     * 对比TransitionManger的 go() 和 beginDelayedTransition()：
     *
     *      go()将场景变化放到了xml不局限于代码，随之需求变化方便改动布局就可。但是每次需要重新绑定数据
     *
     *      beginDelayedTransition()不用重新绑定布局，但是view的属性变动需要在代码中书写，需求变动的话改动麻烦且不直观
     *
     * 思考有没有一种方式结合两者的优点舍弃确定实现动画效果？
     *   @see ConstraintSetActivity
     */
    override fun onClick(v: View) {

        val startScene = Scene.getSceneForLayout(root, R.layout.go_start, this)
        val endScene = Scene.getSceneForLayout(root, R.layout.go_end, this)

        if (toggle)
            TransitionManager.go(endScene)
        else
            TransitionManager.go(startScene)

        bindData()
        toggle = !toggle
    }

}