package com.example.blackice

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_d_r_list.view.text2
import kotlinx.android.synthetic.main.activity_d_r_list.view.text3
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.drw_listview.view.*




data class ListViewItem(val title: String, val subTitle: String, val date: String, val locate: String, val user: String)

class ListViewAdapter(private val items: MutableList<ListViewItem>): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): ListViewItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.drw_listview, parent, false)

        val item: ListViewItem = items[position]
        if (convertView != null) {
            convertView.text2.text = item.title
        }
        if (convertView != null) {
            convertView.text3.text = item.subTitle
        }
        if (convertView != null) {
            convertView.text4.text = item.date
        }
        if (convertView != null) {
            convertView.text5.text = item.locate
        }
        if (convertView != null) {
            convertView.text6.text = item.user
        }

        return convertView
    }
}



class DRwarningActivity : AppCompatActivity() {

    var cnt=0
    var firebaseRef = Firebase.database.getReference("List")

 //   val LIST_MENU: MutableList<String> = mutableListOf<String>("")
    val LIST_MENU = mutableListOf<ListViewItem>()

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_rwarning)

        val listview = findViewById<ListView>(R.id.IdListview)
       // val items = mutableListOf<ListViewItem>()
      //  val adapter = ArrayAdapter(this, R.layout.drw_listview,  LIST_MENU)


       val adapter = ListViewAdapter(LIST_MENU)
        listview.adapter = adapter





        initcount(listview,
                adapter)

        val builder = AlertDialog.Builder(this)
        listview.onItemClickListener = AdapterView.OnItemClickListener { parent, v, position, id -> // get TextView's Text.

            val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

            dialogView.dialoglocate.setText("위치: " + LIST_MENU[position].title.toString())
            dialogView.dialogdate.setText("신고 시간: " + LIST_MENU[position].date.toString())
            dialogView.dialogLog.setText("신고 내용)\n" + LIST_MENU[position].subTitle.toString())
            dialogView.dialogID.setText(LIST_MENU[position].user.toString())
           // dialogView.webView("https://www.naver.com/")



            val url_test=LIST_MENU[position].locate.split("kko.to/")
            val url="http://kko.to/" +url_test[1]
            Log.w("KEY-url", url)


            //dialogView.webView.getSettings().setJavaScriptEnabled(true) //자바스크립트 허용

           // dialogView.webView.loadUrl("https://www.naver.com/") //웹뷰 실행
           // dialogView.webView.setWebChromeClient(WebChromeClient()) //웹뷰에 크롬 사용 허용//이 부분이 없으면 크롬에서 alert가 뜨지 않음
           // dialogView.webView.settings.setSupportMultipleWindows(false)
           dialogView.webView.settings.javaScriptEnabled = true
            dialogView.webView.loadUrl(url) //웹뷰 실행
            dialogView.webView.webViewClient= WebViewClient()
            dialogView.webView.webViewClient = object : WebViewClient() {
                var cnt=false
                override fun onPageFinished(view: WebView?, url: String?) {



                    if (url != null) {
                        //dialogView.webView.loadUrl("https://map.kakao.com/link/map/18577297")
                        if(!(url.contains("kko.to")) && !cnt)
                        {
                            val url_test= url.split("map")
                            var url01= "https://m.map" +url_test[1]//

                            val url_test2= url01.split("q=")
                            url01="https://m.map.kakao.com/actions/searchView?q="+url_test2[1]+"#!/all/map/place"
                            Log.w("KEY-url--", url.toString())
                            Log.w("KEY-url01", url01.toString())
                            dialogView.webView.loadUrl(url01)
                            cnt = true
                        }


                    }
                }
                override fun onReceivedSslError(
                        view: WebView?,
                        handler: SslErrorHandler,
                        error: SslError?
                ) {
                    handler.proceed() // Ignore SSL certificate errors
                }

                override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                ): Boolean {
                    view?.loadUrl(request?.getUrl().toString());
                    return true;
                }

            }

            //dialogView.webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

            //dialogView.webView.settings.domStorageEnabled = true
            //dialogView.webView.loadUrl("http://kko.to/" + url) //웹뷰 실행



            builder.setView(dialogView)
                    .setNegativeButton("닫기") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    }

                    .show()
        }


    }

    private fun initcount(listview: ListView?, adapter: ListViewAdapter) {

        firebaseRef.child("DangersReportLIst").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cnt = snapshot.childrenCount.toInt()
                data_date(listview, adapter, cnt)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }

    private fun data_date(listview: ListView?, adapter: ListViewAdapter, cnt: Int) {
        LIST_MENU.clear()
        Log.w("KEY-cnt", cnt.toString())
        for (i in 1..cnt) {


            firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRDate").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {






                    var key_date = snapshot.value.toString()
                    Log.w("KEY-date", key_date)
                    data_classify(listview, adapter, i, key_date)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value.")
                }
            })
        }

    }

    private fun  data_classify(listview: ListView?, adapter: ListViewAdapter, i: Int, key_date: String) {


        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRClassify").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                var key_classify = snapshot.value.toString()
                Log.w("KEY-classify", key_classify)

                data_content(listview, adapter, i, key_date, key_classify)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })
    }

    private fun   data_content(listview: ListView?, adapter: ListViewAdapter, i: Int, key_date: String, key_classify: String) {


        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRContent").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val key_content = snapshot.value.toString()
                Log.w("KEY-content", key_content)



                data_locate(listview, adapter, i, key_date, key_classify, key_content)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }
    private fun data_locate(listview: ListView?, adapter: ListViewAdapter, i: Int, key_date: String, key_classify: String, key_content: String) {
        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRLocate").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val key_locate = snapshot.value.toString()
                data_user(listview, adapter, i, key_date, key_classify, key_content, key_locate)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })
    }
    private fun data_user(listview: ListView?, adapter: ListViewAdapter, i: Int, key_date: String, key_classify: String, key_content: String, key_locate: String) {

        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRUser").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val key_user = snapshot.value.toString()


                //LIST_MENU.add(key)
                LIST_MENU.add(ListViewItem(key_classify + " 구역 안내", key_content, key_date, key_locate, key_user))
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }

}
