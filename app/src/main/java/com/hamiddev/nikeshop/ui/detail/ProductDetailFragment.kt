package com.hamiddev.nikeshop.ui.detail

import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hamiddev.nikeshop.R
import com.hamiddev.nikeshop.common.*
import com.hamiddev.nikeshop.custom.scroll.CustomObserverScrollViewCallBack
import com.hamiddev.nikeshop.data.model.Comment
import com.hamiddev.nikeshop.data.model.Status
import com.hamiddev.nikeshop.databinding.FragmentProductDetailBinding
import com.hamiddev.nikeshop.service.ImageLoadingService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class   ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {


    @Inject
    lateinit var imageLoadingService: ImageLoadingService
    @Inject
    lateinit var notificationManager: NotificationManager

    private val viewModel: ProductDetailViewModel by viewModels()

    private lateinit var colorAdapter: ColorListAdapter
    private lateinit var sizeAdapter: SizeShoeAdapter
    private lateinit var commentAdapter: CommentAdapter

    private lateinit var args: ProductDetailFragmentArgs

    private val colors = mutableListOf(R.color.blue, R.color.black, R.color.yellow, R.color.red)
    private val sizes = mutableListOf(36, 38, 40, 42, 44, 46, 48)

    override fun viewCreated() {
        super.viewCreated()

        backBtn()

        provideArgs()

        callNecessaryMethodFromViewModel()

        initAdapter()

        observeToComments()

        handleParallaxProductImage()

        observeToProducts()

        observeToResponse()

        addToCart()

    }

    private fun provideArgs() {
        args = ProductDetailFragmentArgs.fromBundle(requireArguments())
    }

    private fun callNecessaryMethodFromViewModel() {
        viewModel.initProduct(args.product)
        viewModel.getComments()
    }


    private fun addToCart() {
        binding.addToCartBtn.setOnClickListener {
            if (viewModel.isLogin())
                viewModel.addToCart()
            else
                showErrorSnack(getString(R.string.should_login))
        }
    }

    private fun initAdapter() {
        colorAdapter = ColorListAdapter(requireContext())
        sizeAdapter = SizeShoeAdapter(requireContext())
        commentAdapter = CommentAdapter()
    }

    private fun backBtn() {
        binding.backBtn.setOnClickListener {
            navigateUp()
        }
    }

    private fun handleParallaxProductImage() {
        with(binding) {
            ivProductImage.post {
                val productImgHeight = ivProductImage.measuredHeight
                val toolbar = toolbarView
                val image = ivProductImage
                scroll.addScrollViewCallbacks(object : CustomObserverScrollViewCallBack() {
                    override fun onScrollChanged(
                        scrollY: Int,
                        firstScroll: Boolean,
                        dragging: Boolean
                    ) {
                        toolbar.alpha = scrollY.toFloat() / productImgHeight.toFloat()
                        image.translationY = scrollY.toFloat() / 2
                    }
                })
            }
        }
    }

    private fun observeToComments() {
        viewModel.commentLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.ERROR -> {
                    setProgressIndicator(false)
                    showErrorSnack(it.errorResponse?.errorMessage!!)
                }
                Status.SUCCESS -> {
                    setProgressIndicator(false)
                    initCommentList(it.data!!)
                    showAllComments(it.data)
                }

                Status.LOADING -> {
                    setProgressIndicator(true)
                }
            }
        }
    }

    private fun showAllComments(comments: List<Comment>) {
        binding.viewAllCommentsBtn.setOnClickListener {
            navigate(
                ProductDetailFragmentDirections.actionProductDetailFragmentToCommentsSheet(
                    comments.toTypedArray()
                )
            )
        }
    }

    private fun observeToProducts() {
        viewModel.productLiveData.observe(viewLifecycleOwner) {
            with(binding) {
                tvProductName.text = it.title
                /*tvProductPrice.text = formatPrice(it.price)
                tvProductPriceDiscount.text = formatPrice(it.previous_price)
                tvProductPriceDiscount.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG*/
                imageLoadingService.load(ivProductImage, it.image)
                toolbarTitleTv.text = it.title

                initColorList()
                initSizeList()

                if (it.isFavorite)
                    favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
                else
                    favoriteBtn.setImageResource(R.drawable.ic_favorites)

                favoriteBtn.setOnClickListener { _ ->
                    if (it.isFavorite) {
                        viewModel.removeFavorite(it)
                    } else {
                        viewModel.addFavorite(it)
                    }
                    viewModel.responseLiveData.observe(viewLifecycleOwner) {
                        showSnack(getString(it))
                    }
                }

            }
        }
    }

    private fun observeToResponse() {

        viewModel.cartResponseLiveData.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.ERROR -> showErrorSnack(it.errorResponse!!.errorMessage)
                Status.SUCCESS -> {
                    showSnack(getString(R.string.successAddToCart))

                    val pendingIntent = NavDeepLinkBuilder(requireContext())
                        .setGraph(R.navigation.cart)
                        .setDestination(R.id.cartFragment)
                        .createPendingIntent()

                    val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                        .setContentTitle("محصول شماره ${it.data?.product_id} به سبد خرید شما اضافه شد")
                        .setContentText(getString(R.string.successAddToCartContentNotification))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .build()

                    notificationManager.notify(Random.nextInt(), notification)
                }
                Status.LOADING -> {
                    //nothing to do
                }
            }
        }

    }

    private fun initColorList() {
        with(binding) {
            rvColor.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            colorAdapter.colorLists = colors
            rvColor.adapter = colorAdapter
        }
    }

    private fun initSizeList() {
        with(binding) {
            rvSize.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            sizeAdapter.sizeList = sizes
            rvSize.adapter = sizeAdapter
        }
    }

    private fun initCommentList(comments: List<Comment>) {
        with(binding) {
            rvComment.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            commentAdapter.comments = comments.toMutableList()
            rvComment.adapter = commentAdapter
        }
    }
}