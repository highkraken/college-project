package com.example.collegeproject.viewmodels.admin

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.collegeproject.database.Product
import com.example.collegeproject.database.ProductDao
import com.example.collegeproject.database.PurchaseSale
import com.example.collegeproject.database.PurchaseSaleDao
import com.example.collegeproject.database.User
import com.example.collegeproject.database.UserDao
import com.example.collegeproject.utils.PriceType
import com.example.collegeproject.utils.UnitType
import com.example.collegeproject.utils.toPriceType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AddPurchaseSaleViewModel(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userDao: UserDao,
    private val productDao: ProductDao,
    private var sellerId: Long,
    private var productId: Long,
) : ViewModel() {
    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var sellerName by mutableStateOf("")
        private set

    fun setSelectedSeller(sellerName: String) {
        uiScope.launch {
            this@AddPurchaseSaleViewModel.sellerName = sellerName
            val sellerId = Regex("\\d+").find(sellerName)?.value?.toLong() ?: 3
            this@AddPurchaseSaleViewModel.sellerId = sellerId
        }
    }

    var productName by mutableStateOf("")
        private set

    fun onProductNameChange(productName: String) {
        uiScope.launch {
            this@AddPurchaseSaleViewModel.productName = productName
            val productId = Regex("\\d+").find(productName)?.value?.toLong() ?: 1
            this@AddPurchaseSaleViewModel.productId = productId
        }
    }

    fun setSelectedProduct(productName: String) {
        this.productName = productName
        if (productName.toLongOrNull() != null) {
            getProducts()
        }
    }

    private val _sellers = MutableLiveData<List<User>>()
    val sellers: LiveData<List<User>>
        get() = _sellers

    private val _buyers = MutableLiveData<List<User>>()
    val buyers: LiveData<List<User>>
        get() = _buyers

    private val _productsOfSeller = MutableLiveData<Pair<List<String>, List<String>>>()
    val productsOfSeller: LiveData<Pair<List<String>, List<String>>>
        get() = _productsOfSeller

    init {
        fetchData()
        Log.d("PRODUCTS", "products -> ${_productsOfSeller.value}")
    }

    var unitType by mutableStateOf(UnitType.BAG.toMenuItem())
        private set

    fun setSelectedUnitType(unitType: String) {
        this.unitType = unitType
    }

    var unit by mutableStateOf("1")
        private set

    fun onUnitChange(unit: String) {
        this.unit = unit
    }

    var quantity by mutableStateOf("")
        private set

    fun onQuantityChange(quantity: String) {
        this.quantity = quantity
        totalAmount = calculateTotal().toString()
    }

    var price by mutableStateOf("")
        private set

    fun onPriceChange(price: String) {
        this.price = price
        totalAmount = calculateTotal().toString()
    }

    var priceType by mutableStateOf(PriceType.TWENTY.type)
        private set

    fun setSelectedPriceType(priceType: String) {
        this.priceType = priceType
    }

    private var buyer by mutableStateOf<User?>(null)

    var buyerName by mutableStateOf("")
        private set

    fun setSelectedBuyerName(buyerName: String) {
        uiScope.launch {
            this@AddPurchaseSaleViewModel.buyerName = buyerName
            val buyerId = Regex("\\d+").find(buyerName)?.value?.toLong() ?: 2
            this@AddPurchaseSaleViewModel.buyer = getUserById(buyerId)
        }
    }

    var totalAmount by mutableStateOf("")
        private set

    fun onTotalAmountChange(totalAmount: String) {
        this.totalAmount = totalAmount
    }

    fun onSaveAndNextClick() {
        uiScope.launch {
            val buyerName = this@AddPurchaseSaleViewModel.buyerName.trim().split(" ").drop(1).joinToString(separator = " ")
            val sellerName = this@AddPurchaseSaleViewModel.sellerName.trim().split(" ").drop(1).joinToString(separator = " ")
            val purchaseSaleEntry = PurchaseSale(
                invoiceDate = LocalDate.now(),
                sellerId = sellerId,
                sellerName = sellerName,
                buyerId = buyer!!.userId,
                buyerName = buyerName,
                productUnit = unit.toFloatOrNull() ?: 0f,
                product = getProductById(productId),
                priceType = priceType.toPriceType(),
                productPrice = price.toFloatOrNull() ?: 0f,
                productQuantity = quantity.toFloatOrNull() ?: 0f,
                productTotal = calculateTotal()
            )
            addEntryToDatabase(purchaseSaleEntry)
            resetAllFields()
        }
    }

    private suspend fun addEntryToDatabase(entry: PurchaseSale) {
        return withContext(Dispatchers.IO) {
            purchaseSaleDao.upsertPurchaseSaleEntry(entry)
        }
    }

    private suspend fun getUserById(userId: Long): User {
        return withContext(Dispatchers.IO) {
            userDao.getUserById(userId)
        }
    }

    private suspend fun getProductById(productId: Long): Product {
        return withContext(Dispatchers.IO) {
            productDao.getProductById(productId)
        }
    }

    private fun calculateTotal(): Float {
        val priceType = this.priceType.toPriceType()
        val price = this.price.toFloatOrNull() ?: 0f
        val perUnitPrice = when (priceType) {
            PriceType.TWENTY -> price / 20
            PriceType.TEN -> price / 10
            PriceType.FIVE -> price / 5
            else -> price
        }
        val totalAmount = when (priceType) {
            PriceType.AMOUNT -> price
            PriceType.UNIT -> price * (unit.toFloatOrNull() ?: 0f)
            else -> perUnitPrice * (quantity.toFloatOrNull() ?: 0f)
        }
        return totalAmount
    }

    private fun resetAllFields() {
        unitType = UnitType.BAG.toMenuItem()
        unit = "1"
        quantity = ""
        price = ""
        priceType = PriceType.TWENTY.toMenuItem()
        buyer = null
        buyerName = ""
        totalAmount = ""
    }

    fun getSellers(): List<String> {
        return sellers.value?.map { "${it.userId}. ${it.ownerName}" } ?: listOf()
    }

    private fun getBuyers(): List<String> {
        return buyers.value?.map { "${it.userId}. ${it.ownerName}" } ?: listOf()
    }

    private fun getProducts() {
        val products = _productsOfSeller
        CoroutineScope(Dispatchers.IO).launch {
            if (productName.isNotEmpty()) {
                products.value = productName.toLongOrNull()?.let { productId ->
                    val product = getProductById(productId)
                    listOf("${product.productId}. ${product.productName}") to listOf<String>()
                } ?: ((products.value?.first?.plus(products.value?.second!!)
                    ?.filter { it.contains(productName) } ?: listOf()) to listOf())

                _productsOfSeller.value = products.value
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val allProductsLiveData = productDao.getAllProducts()
                val productsLiveData = productDao.getProductsOfSeller(sellerId)
                val allOtherProductsLiveData = productDao.getProductsNotOfSeller(sellerId)
                val allBuyersLiveData = userDao.getUsersByType("Buyer")
                val allSellersLiveData = userDao.getUsersByType("Seller")
                val mediatorLiveData =
                    MediatorLiveData<Pair<List<Product>?, List<Product>?>>().apply {
                        addSource(productsLiveData) {
                            value = it to allOtherProductsLiveData.value
                        }
                        addSource(allOtherProductsLiveData) {
                            value = productsLiveData.value to it
                        }
                    }
                if (sellerId == 0L) {
                    allProductsLiveData.observeForever { products ->
                        val formattedProducts =
                            products.map { "${it.productId}. ${it.productName}" }

                        _productsOfSeller.postValue(listOf<String>() to formattedProducts)
                    }
                } else {
                    mediatorLiveData.observeForever { (products, otherProducts) ->
                        val formattedProducts =
                            products?.map { "${it.productId}. ${it.productName}" }
                                ?: listOf()
                        val formattedOtherProducts =
                            otherProducts?.map { "${it.productId}. ${it.productName}" }
                                ?: listOf()
                        _productsOfSeller.postValue(formattedProducts to formattedOtherProducts)
                    }
                }
                allBuyersLiveData.observeForever { buyers ->
                    _buyers.postValue(buyers)
                }
                allSellersLiveData.observeForever { sellers ->
                    _sellers.postValue(sellers)
                }
            }

            if (sellerId != 0L) {
                val seller = getUserById(sellerId)
                sellerName = "${seller.userId}. ${seller.ownerName}"
            }
            if (productId != 0L) {
                val product = getProductById(productId)
                productName = "${product.productId}. ${product.productName}"
            }
        }
    }

    var sellerExpand by mutableStateOf(false)
        private set

    fun onSellerDismiss() {
        sellerExpand = false
    }

    fun onSellerExpandedChange() {
        sellerExpand = !sellerExpand
    }

    var buyerExpand by mutableStateOf(false)
        private set

    fun onBuyerDismiss() {
        buyerExpand = false
    }

    fun onBuyerExpandedChange() {
        buyerExpand = !buyerExpand
    }

    var productExpand by mutableStateOf(false)
        private set

    fun onProductDismiss() {
        productExpand = false
    }

    fun onProductExpandedChange() {
        productExpand = !productExpand
    }

    var unitTypeExpand by mutableStateOf(false)
        private set

    fun onUnitTypeDismiss() {
        unitTypeExpand = false
    }

    fun onUnitTypeExpandedChange() {
        unitTypeExpand = !unitTypeExpand
    }

    var priceTypeExpand by mutableStateOf(false)
        private set

    fun onPriceTypeDismiss() {
        priceTypeExpand = false
    }

    fun onPriceTypeExpandedChange() {
        priceTypeExpand = !priceTypeExpand
    }
}

@Suppress("UNCHECKED_CAST")
class AddPurchaseSaleViewModelFactory(
    private val purchaseSaleDao: PurchaseSaleDao,
    private val userDao: UserDao,
    private val productDao: ProductDao,
    private val sellerId: Long = 0,
    private val productId: Long = 0,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPurchaseSaleViewModel::class.java)) {
            return AddPurchaseSaleViewModel(
                purchaseSaleDao,
                userDao,
                productDao,
                sellerId,
                productId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}