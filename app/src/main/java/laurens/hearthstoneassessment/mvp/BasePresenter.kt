package laurens.hearthstoneassessment.mvp

interface BasePresenter<View> {
    var view: View
    fun onAttach()
    fun onDetach()
    fun initialize(view: View) {
        this.view = view
        onAttach()
    }
}