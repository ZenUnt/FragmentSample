package com.websarva.wings.android.fragmentsample

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

class MenuListFragment : Fragment() {
    // 大画面かどうかの判定フラグ
    private var _isLayoutXLarge = true

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // 親クラスのメソッド呼び出し
        super.onActivityCreated(savedInstanceState)
        // 自分が所属するアクティビティからmenuThanksFrameを取得
        val menuThanksFrame = activity?.findViewById<View>(R.id.menuThanksFrame)
        // menuThanksFrameがnullの場合
        if (menuThanksFrame == null) {
            // 画面判定フラグを通常画面とする
            _isLayoutXLarge = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // フラグメントで表示する画面をXMLファイルからインフレーとする
        val view = inflater.inflate(R.layout.fragment_menu_list, container, false)
        // 画面部品ListViewを取得
        val lvMenu = view.findViewById<ListView>(R.id.lvMenu)
        // SimpleAdapterで使用するMutableListオブジェクトの作成
        val menuList: MutableList<MutableMap<String, String>> = mutableListOf()
        // メニューのデータ登録
        var menu = mutableMapOf("name" to "から揚げ定食", "price" to "800円")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ハンバーグ定食", "price" to "850円")
        menuList.add(menu)
        menu = mutableMapOf("name" to "ステーキ定食", "price" to "1000円")
        menuList.add(menu)
        menu = mutableMapOf("name" to "焼き肉定食", "price" to "900円")
        menuList.add(menu)
        menu = mutableMapOf("name" to "焼き魚定食", "price" to "800円")
        menuList.add(menu)
        menu = mutableMapOf("name" to "野菜炒め定食", "price" to "700円")
        menuList.add(menu)
        menu = mutableMapOf("name" to "天ぷら定食", "price" to "950円")
        menuList.add(menu)
        menu = mutableMapOf("name" to "とんかつ定食", "price" to "900円")
        menuList.add(menu)
        // SimpleAdapter第4引数from用データ作成
        val from = arrayOf("name", "price")
        // SimpleAdapter第5引数to用データ作成
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        // SimpleAdapter作成
        val adapter = SimpleAdapter(activity, menuList, android.R.layout.simple_list_item_2, from, to)
        // アダプタの登録
        lvMenu.adapter = adapter

        // リスナの登録
        lvMenu.onItemClickListener = ListItemClickListener()

        // インフレートされた画面を戻り値として返す
        return view
    }

    // リストがタップされた時の処理が記述されたメンバクラス
    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // タップされた行のデータを取得
            val item = parent.getItemAtPosition(position) as MutableMap<String, String>
            // 定食名を金額を取得
            val menuName = item["name"]
            val menuPrice = item["price"]

            // 引き継ぎデータを纏めて格納できるBundleオブジェクト
            val bundle = Bundle()
            // Bundleオブジェクトに引き継ぎデータを格納
            bundle.putString("menuName", menuName)
            bundle.putString("menuPrice", menuPrice)

            // 大画面の場合
            if (_isLayoutXLarge) {
                // フラグメントトランザクションの開始
                val transaction = fragmentManager?.beginTransaction()
                // 注文完了フラグメント作成
                val menuThanksFragment = MenuThanksFragment()
                // 引き継ぎデータを注文完了フラグメントに格納
                menuThanksFragment.arguments = bundle
                // 生成した注文完了フラグメントをmenuThanksFrameレイアウト部品に追加
                transaction?.replace(R.id.menuThanksFrame, menuThanksFragment)
                transaction?.commit()
            } else { // 通常画面の場合
                // インデントオブジェクトを生成
                val intent2MenuThanks = Intent(activity, MenuThanksActivity::class.java)
                // 第2画面に送るデータを格納
                intent2MenuThanks.putExtras(bundle)
                // 第2画面の起動
                startActivity(intent2MenuThanks)
            }

        }
    }
}