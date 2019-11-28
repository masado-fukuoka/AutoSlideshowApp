package fukuoka.masado.techacademy.autoslideshowapp

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.provider.MediaStore
import android.content.ContentUris
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class MainActivity : AppCompatActivity() {







    private val PERMISSIONS_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Android 6.0以降の場合
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // パーミッションの許可状態を確認する
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 許可されている
                getContentsInfo()
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST_CODE
                )
            }
            // Android 5系以下の場合
        } else {
            getContentsInfo()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContentsInfo()
                }
        }
    }





    private fun getContentsInfo() {
        // 画像の情報を取得する
        val resolver = contentResolver
        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, // データの種類
            null, // 項目(null = 全項目)
            null, // フィルタ条件(null = フィルタなし)
            null, // フィルタ用パラメータ
            null // ソート (null ソートなし)
        )


        susumu_button.setOnClickListener() {

            if (cursor!!.moveToNext()) {
                // indexからIDを取得し、そのIDから画像のURIを取得する
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                Log.d("ANDROID", "URI : " + imageUri.toString())
                imageView.setImageURI(imageUri)
            } else if (cursor!!.moveToNext() === false) {
                cursor!!.moveToFirst()
            }
        }

        modoru_button.setOnClickListener() {

            if (cursor!!.moveToPrevious()) {
                // indexからIDを取得し、そのIDから画像のURIを取得する
                val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                val id = cursor.getLong(fieldIndex)
                val imageUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                Log.d("ANDROID", "URI : " + imageUri.toString())
                imageView.setImageURI(imageUri)
            } else if (cursor!!.moveToPrevious() === false) {
                cursor!!.moveToLast()
            }
        }

        class MainActivity : AppCompatActivity() {

            private var mTimer: Timer? = null

            // タイマー用の時間のための変数
            private var mTimerSec = 0.0

            private var mHandler = Handler()

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                saisei_button.setOnClickListener {
                    if (mTimer == null) {
                        mTimer = Timer()
                        mTimer!!.schedule(object : TimerTask() {
                            override fun run() {
                                mTimerSec += 0.1
                                mHandler.post {
                                    if (cursor!!.moveToNext()) {
                                        // indexからIDを取得し、そのIDから画像のURIを取得する
                                        val fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)
                                        val id = cursor.getLong(fieldIndex)
                                        val imageUri =
                                            ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                                        Log.d("ANDROID", "URI : " + imageUri.toString())
                                        imageView.setImageURI(imageUri)
                                    }
                                    }
                                }

                        }, 200, 200) // 最初に始動させるまで 200ミリ秒、ループの間隔を 200ミリ秒 に設定
                    }

                    saisei_button.text = "停止"


                }


            }


        }
    }
}


/*
///class MainActivity : AppCompatActivity() {
private var mTimer: Timer? = null

// タイマー用の時間のための変数
private var mTimerSec = 0.0

private var mHandler = Handler()

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // タイマーの作成
    mTimer = Timer()

    // タイマーの始動
    mTimer!!.schedule(object : TimerTask() {
        override fun run() {
            mTimerSec += 0.1
            mHandler.post {

                imageView.setImageURI(imageUri)
                timer.text= String.format("%.1f", mTimerSec)

            }
        }
    }, 100, 100) // 最初に始動させるまで 100ミリ秒、ループの間隔を 100ミリ秒 に設定
}

}*/



