package xyz.teamgravity.shoppinglisttest.fragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import xyz.teamgravity.shoppinglisttest.databinding.FragmentImagePickBinding
import xyz.teamgravity.shoppinglisttest.viewmodel.ShoppingViewModel

@AndroidEntryPoint
class ImagePickFragment : Fragment() {

    private var _binding: FragmentImagePickBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ShoppingViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentImagePickBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ShoppingViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}