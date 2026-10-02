package com.example.iptvtvpro.ui

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.iptvtvpro.data.Channel
import com.example.iptvtvpro.data.PlaylistParser
import com.example.iptvtvpro.data.XtreamClient
import com.example.iptvtvpro.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity:Activity(){private lateinit var b:ActivityMainBinding;private var channels=listOf<Channel>()
 override fun onCreate(s:Bundle?){super.onCreate(s);b=ActivityMainBinding.inflate(layoutInflater);setContentView(b.root);b.loadButton.setOnClickListener{load(b.urlInput.text.toString())};b.xtreamButton.setOnClickListener{loadXtream()};b.channelList.setOnItemClickListener{_,_,p,_->startActivity(android.content.Intent(this,PlayerActivity::class.java).putExtra("url",channels[p].url).putExtra("name",channels[p].name))}}
 private fun load(input:String){if(input.isBlank())return;b.status.text="Loading…";lifecycleScope.launch(Dispatchers.IO){try{val text=if(PlaylistParser.isDirectUrl(input))"" else URL(input).readText();val list=if(text.isNotBlank())PlaylistParser.parse(text) else listOf(Channel(input,input));show(list,"${list.size} channels loaded")}catch(e:Exception){show(emptyList(),"Load error: ${e.message}")}}}
 private fun loadXtream(){b.status.text="Xtream: enter base URL, username and password in the secure profile implementation."}
 private suspend fun show(list:List<Channel>,status:String)=withContext(Dispatchers.Main){channels=list;b.channelList.adapter=ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,list.map{it.name});b.status.text=status}}
