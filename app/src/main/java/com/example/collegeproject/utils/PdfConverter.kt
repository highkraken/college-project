package com.example.collegeproject.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeproject.R
import java.io.File
import java.io.FileOutputStream

class PdfConverter {

    private fun createSaleBitmapFromView(
        context: Context,
        view: View,
        pdfDetails: PdfDetails,
        adapter: SaleBillRecyclerAdapter,
        activity: Activity,
    ): Bitmap {
        val companyNameTv = view.findViewById<TextView>(R.id.company_tv)
        val sloganTv = view.findViewById<TextView>(R.id.slogan_tv)
        val addressTv = view.findViewById<TextView>(R.id.address_tv)
        val buyerNameTv = view.findViewById<TextView>(R.id.name_tv)
        val dateTv = view.findViewById<TextView>(R.id.date_tv)
        val wageTv = view.findViewById<TextView>(R.id.wage_tv)
        val grandTotalTv = view.findViewById<TextView>(R.id.grand_total_tv)
        val debitTv = view.findViewById<TextView>(R.id.debit_tv)
        val totalDebitTv = view.findViewById<TextView>(R.id.total_debit_tv)
        val recyclerView = view.findViewById<RecyclerView>(R.id.items_rv)
        buyerNameTv.text = pdfDetails.customerName
        dateTv.text = pdfDetails.date
        debitTv.text = pdfDetails.total
        wageTv.text = pdfDetails.wage
        grandTotalTv.text = pdfDetails.grandTotal
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        return createBitmap(context, view, activity)
    }

    private fun createPurchaseBitmapFromView(
        context: Context,
        view: View,
        pdfDetails: PdfDetails,
        adapter: PurchaseBillRecyclerAdapter,
        activity: Activity,
    ): Bitmap {
        val companyNameTv = view.findViewById<TextView>(R.id.company_tv)
        val sloganTv = view.findViewById<TextView>(R.id.slogan_tv)
        val addressTv = view.findViewById<TextView>(R.id.address_tv)
        val buyerNameTv = view.findViewById<TextView>(R.id.name_tv)
        val dateTv = view.findViewById<TextView>(R.id.date_tv)
        val wageTv = view.findViewById<TextView>(R.id.labour_tv)
        val grandTotalTv = view.findViewById<TextView>(R.id.grand_total_tv)
        val commissionTv = view.findViewById<TextView>(R.id.commission_tv)
        val importTv = view.findViewById<TextView>(R.id.import_tv)
        val extExpTv = view.findViewById<TextView>(R.id.ext_exp_tv)
        val allExpTv = view.findViewById<TextView>(R.id.all_exp_tv)
        val rAllExpTv = view.findViewById<TextView>(R.id.all_exp_tv_r)
        val totalTv = view.findViewById<TextView>(R.id.total_tv)
        val recyclerView = view.findViewById<RecyclerView>(R.id.items_rv)
        buyerNameTv.text = pdfDetails.customerName
        dateTv.text = pdfDetails.date
        totalTv.text = pdfDetails.total
        wageTv.text = pdfDetails.wage
        grandTotalTv.text = pdfDetails.grandTotal
        commissionTv.text = pdfDetails.commission
        allExpTv.text = pdfDetails.allExtExp
        rAllExpTv.text = pdfDetails.allExtExp
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        return createBitmap(context, view, activity)
    }

    private fun createBitmap(
        context: Context,
        view: View,
        activity: Activity,
    ): Bitmap {
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.display?.getRealMetrics(displayMetrics)
            displayMetrics.densityDpi
        } else {
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        view.measure(
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
            ),
            View.MeasureSpec.makeMeasureSpec(
                displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
            )
        )
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return Bitmap.createScaledBitmap(bitmap, 595, 842, true)
    }

    private fun convertBitmapToPdf(bitmap: Bitmap, context: Context) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        page.canvas.drawBitmap(bitmap, 0F, 0F, null)
        pdfDocument.finishPage(page)
        val filePath = File(context.getExternalFilesDir(null), "bill.pdf")
        pdfDocument.writeTo(FileOutputStream(filePath))
        pdfDocument.close()
        renderPdf(context, filePath)
    }

    fun createPdf(
        context: Context,
        pdfDetails: PdfDetails,
        activity: Activity,
        billFormat: BillFormat
    ) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(billFormat.formatId, null)

        val bitmap =
            if (billFormat == BillFormat.SALE)
                createSaleBitmapFromView(context, view, pdfDetails, SaleBillRecyclerAdapter(pdfDetails.sItemsList), activity)
            else
                createPurchaseBitmapFromView(context, view, pdfDetails, PurchaseBillRecyclerAdapter(pdfDetails.pItemsList), activity)
        convertBitmapToPdf(bitmap, activity)
    }


    private fun renderPdf(context: Context, filePath: File) {
        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            filePath
        )
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, "application/pdf")

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {

        }
    }
}