package com.xht.jetpack.databinding.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xht.jetpack.R
import com.xht.jetpack.databinding.ActivityUserListBinding
import com.xht.jetpack.databinding.ItemUserListBinding
import com.xht.jetpack.databinding.bean.User
import kotlinx.android.synthetic.main.activity_user_list.*


class UserListActivity : AppCompatActivity() {
    private var mAdapter: UserListAdapter? = null

    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mContext = this
        val userListBinding = DataBindingUtil.setContentView<ActivityUserListBinding>(
            this,
            R.layout.activity_user_list
        )


        mAdapter = UserListAdapter(this)
        userListBinding.rvUserList.layoutManager = LinearLayoutManager(this)
        userListBinding.rvUserList.adapter = mAdapter
        mAdapter!!.setData(getUserList())

    }

    private fun getUserList(): MutableList<User> {
        val list: MutableList<User> = mutableListOf()
        list.add(User("小明", 18))
        list.add(User("小红", 18))
        list.add(User("小q", 18))
        list.add(User("小a", 18))
        return list
    }


    public class UserListAdapter(val context: Context) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

        private var list: MutableList<User> = mutableListOf()
        public fun setData(list: MutableList<User>) {
            this.list.clear()
            this.list = list
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.item_user_list, parent, false)
            return UserListViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
            holder.getItemUserBinding().user = list[position]
            holder.getItemUserBinding().executePendingBindings()
        }

        override fun getItemCount(): Int {
            return list.size
        }

        public class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var binding: ItemUserListBinding? = null

            init {
                binding = DataBindingUtil.bind(itemView)
            }

            public fun getItemUserBinding(): ItemUserListBinding {
                return binding!!;
            }
        }
    }

    public class ClickPresenter {
        fun addUser(view: View) {
//            Toast.makeText(this@UserListActivity, "addUser", Toast.LENGTH_SHORT).show()
        }

        public fun removeUser(view: View) {
//            Toast.makeText(this@UserListActivity, "removeUser", Toast.LENGTH_SHORT).show();
        }
    }
}