package com.example.week12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.internal.ContextUtils.getActivity
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGetAll: Button = findViewById<Button>(R.id.btnGetAll)
        btnGetAll.setOnClickListener{
            val rq:RequestQueue= Volley.newRequestQueue(this)
            val objRequest = JsonArrayRequest(
                Request.Method.GET,
                "http://demo.onmyfinger.com/home/getAll",
                null,
                Response.Listener {
                    response -> try {
                        var nameList:StringBuffer= StringBuffer()
                    for(i in 0 until response.length()){
                        //retrieve the first data
                        val objStudent:JSONObject=response.getJSONObject(i)
                        nameList.append(objStudent.getString("name")+ "\n")

                    }


                        findViewById<TextView>(R.id.txtDisplay).setText(nameList)

                    }catch(e:JSONException){
                        findViewById<TextView>(R.id.txtDisplay).setText(e.message)
                    }
                },
                Response.ErrorListener {
                    error -> findViewById<TextView>(R.id.txtDisplay).setText(error.message)
                }
            )

            rq?.add(objRequest)


        }

        val btnSearch: Button = findViewById<Button>(R.id.btnSearch)
        btnSearch.setOnClickListener{
            val studentID: String= findViewById<TextView>(R.id.inputStudentId).text.toString()

            val rq:RequestQueue= Volley.newRequestQueue(this)
            val objRequest = JsonObjectRequest(
                Request.Method.GET,
                "http://demo.onmyfinger.com/home/getById?id=" +studentID,
                null,
                Response.Listener {
                        response -> try {
                            val objStudent:JSONObject=response
                            val strname : String =objStudent.get("name").toString()
                            val strprog : String =objStudent.getString("programme")

                            findViewById<TextView>(R.id.inputStudentName).setText(strname)
                            findViewById<TextView>(R.id.inputStudentProg).setText(strprog)


                }catch(e:JSONException){
                    findViewById<TextView>(R.id.txtDisplay).setText(e.message)
                    Toast.makeText(MainActivity(),"id not found bro",Toast.LENGTH_SHORT).show();
                }
                },
                Response.ErrorListener {
                        error -> findViewById<TextView>(R.id.txtDisplay).setText(error.message)
                }
            )
            rq?.add(objRequest)
        }

        val btnAdd: Button = findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener{
            val strId: String= findViewById<TextView>(R.id.inputStudentId).text.toString()
            val strName: String= findViewById<TextView>(R.id.inputStudentName).text.toString()
            val strProg: String= findViewById<TextView>(R.id.inputStudentProg).text.toString()

            val rq:RequestQueue= Volley.newRequestQueue(this)
            val objRequest = JsonObjectRequest(
                Request.Method.GET,
                "http://demo.onmyfinger.com/home/add?id=" +strId + "&name=" + strName +"&programme="+ strProg,
                null,
                Response.Listener {
                        response -> try {
                    Toast.makeText(this,"Record Added",Toast.LENGTH_SHORT).show();

                }catch(e:JSONException){
                    findViewById<TextView>(R.id.txtDisplay).setText(e.message)

                }
                },
                Response.ErrorListener {
                        error -> findViewById<TextView>(R.id.txtDisplay).setText(error.message)
                }
            )
            rq?.add(objRequest)
        }
    }
}