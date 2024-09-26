package th.ac.rmutto.finlove.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import th.ac.rmutto.finlove.databinding.FragmentHomeBinding  // เปลี่ยนชื่อ Binding ให้ตรงกับ XML Layout

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null  // ใช้ชื่อ FragmentDashboardBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)  // ใช้ FragmentDashboardBinding
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
