package com.hamiddev.nikeshop.ui.cart

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.BaseViewModel
import com.hamiddev.nikeshop.common.SingleLiveEvent
import com.hamiddev.nikeshop.common.safeApiCall
import com.hamiddev.nikeshop.data.model.*
import com.hamiddev.nikeshop.data.repo.cart.CartRepository
import com.hamiddev.nikeshop.data.repo.user.TokenContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) :
    BaseViewModel() {

    val cartItemsLiveData = SingleLiveEvent<Resource<CartResponse>>()
    val cartPurchaseLiveData = MutableLiveData<PurchaseDetail>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()
    val cartResponseLiveData = SingleLiveEvent<Resource<MessageResponse>>()
    val cartResponseLiveData2 = SingleLiveEvent<Resource<AddToCartResponse>>()

    fun getData() {
        Timber.i("timber cart refrence getData()")
        progressLiveData.value = true
        if (!TokenContainer.token.isNullOrEmpty()) {
            emptyStateLiveData.value = EmptyState(false)
            viewModelScope.launch(Dispatchers.IO) {

                val data = cartRepository.getCart()
                if (data.data?.cart_items?.isEmpty() == true)
                    emptyStateLiveData.postValue(EmptyState(true, R.string.no_product_cart))

                cartItemsLiveData.postValue(data)

                data.data?.let {
                    cartPurchaseLiveData.postValue(
                        PurchaseDetail(
                            it.total_price,
                            it.shipping_cost,
                            it.payable_price
                        )
                    )
                }
                progressLiveData.postValue(false)
            }
        } else {
            progressLiveData.value = false
            emptyStateLiveData.value = EmptyState(true, R.string.cartEmptyStateLoginRequired, true)
        }
    }


    fun removeItemFromCart(cartItem: CartItem) {
        Timber.i("timber cart refrence removeItem()")
        viewModelScope.launch {

            cartResponseLiveData.value =
                cartRepository.remove(cartItem.cart_item_id)

            EventBus.getDefault().getStickyEvent(CartItemCount::class.java)?.let {
                it.count -= cartItem.count
                EventBus.getDefault().postSticky(it)
            }
        }
    }

    fun increaseCartItemCount(cartItem: CartItem) {
        Timber.i("timber cart refrence increaseItem()")
        viewModelScope.launch {

            cartResponseLiveData2.value =
                cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)

            EventBus.getDefault().getStickyEvent(CartItemCount::class.java)?.let {
                it.count += 1
                EventBus.getDefault().postSticky(it)
            }
        }
    }

    fun decreaseCartItemCount(cartItem: CartItem) {
        Timber.i("timber cart refrence decreaseItem()")
        viewModelScope.launch {

            cartResponseLiveData2.value =
                cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)

            EventBus.getDefault().getStickyEvent(CartItemCount::class.java)?.let {
                it.count -= 1
                EventBus.getDefault().postSticky(it)
            }
        }
    }

    fun calculateAndPublishPurchaseDetail() {
        Timber.i("timber cart refrence calculate()")
        Timber.i("timber cart count ${cartItemsLiveData.value?.data?.cart_items!![0].count}")
        cartItemsLiveData.value?.let { cartItems ->
            cartPurchaseLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0

                cartItems.data?.cart_items!!.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }
                purchaseDetail.total_price = totalPrice
                purchaseDetail.payable_price = payablePrice
                cartPurchaseLiveData.postValue(purchaseDetail)
            }
        }
    }

    fun getCount() {
        viewModelScope.launch {
            if (TokenContainer.token != null) {

                cartRepository.getCartItemsCount().data?.let {
                    EventBus.getDefault().postSticky(it)
                }
            }
        }
    }
}