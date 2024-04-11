package com.example.collegeproject.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeproject.R
import com.example.collegeproject.model.SaleProductResult

class SaleBillRecyclerAdapter(
    private val itemsList: List<SaleProductResult>
) : RecyclerView.Adapter<SaleBillRecyclerAdapter.SaleBillViewHolder>() {

    class SaleBillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val unitTv: TextView = itemView.findViewById(R.id.unit_tv)
        val productTv: TextView = itemView.findViewById(R.id.product_tv)
        val quantityTv: TextView = itemView.findViewById(R.id.quantity_tv)
        val rateTv: TextView = itemView.findViewById(R.id.rate_tv)
        val totalTv: TextView = itemView.findViewById(R.id.item_total_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleBillViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.invoice_item_rv, parent, false)
        return SaleBillViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: SaleBillViewHolder, position: Int) {
        holder.unitTv.text = itemsList[position].productUnit.toString()
        holder.productTv.text = itemsList[position].productName
        holder.quantityTv.text = itemsList[position].productQuantity.toString()
        holder.rateTv.text = itemsList[position].productPrice.toString()
        holder.totalTv.text = itemsList[position].productTotal.toString()
    }

}