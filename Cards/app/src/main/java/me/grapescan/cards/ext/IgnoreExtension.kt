package me.grapescan.cards.ext

/**
 * This extension can is useful in case you want to ignore the return type/value of some function.
 *
 * For example, when loading image with Glide you could write one line method, if it would not return ViewTarget:
 * ```
 * interface MyView {
 *     fun showImage(uri: Uri)
 * }
 *
 * class MyViewImpl(private val imageView: ImageView) : MyView {
 *
 *     private val glide = GlideApp.with(imageView)
 *
 *     override fun showImage(uri: Uri) {
 *         glide.load(uri).into(imageView) // this could be one line
 *     }
 * }
 * ```
 *
 * You can use toUnit() here to erase returned value and make method one line:
 * ```
 * interface MyView {
 *     fun showImage(uri: Uri)
 * }
 *
 * class MyViewImpl(private val imageView: ImageView) : MyView {
 *
 *     private val glide = GlideApp.with(imageView)
 *
 *     override fun showImage(uri: Uri) = glide.load(uri).into(imageView).toUnit()
 * }
 * ```
 */
fun <T : Any?> T.toUnit(): Unit = Unit
