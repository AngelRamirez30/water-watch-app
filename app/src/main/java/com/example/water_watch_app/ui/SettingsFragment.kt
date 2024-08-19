import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.water_watch_app.GenericActivity
import com.example.water_watch_app.R
import com.example.water_watch_app.utils.AuthManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class SettingsFragment : Fragment() {

    //@Inject
    //lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Configura el evento onClick para el LinearLayout de términos y condiciones
        val termsLayout: View = view.findViewById(R.id.termsLayout)
        termsLayout.setOnClickListener {
            showTermsDialog()
        }

        // Configura el evento onClick para el LinearLayout de privacidad
        val privacyLayout: View = view.findViewById(R.id.privacyLayout)
        privacyLayout.setOnClickListener {
            showPrivacyDialog()
        }
        val activity: View = view.findViewById(R.id.aboutLayout)
        activity.setOnClickListener {
            showAboutDialog()
        }

        // Configura el evento onClick para el botón de cerrar sesión
        val logoutButton: Button = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {

        }

        return view
    }

    private fun showTermsDialog() {
        GenericActivity.startActivity(requireContext(), R.layout.layout_terms)
    }

    private fun showPrivacyDialog() {
        GenericActivity.startActivity(requireContext(), R.layout.layout_privacy)
    }
    private fun showAboutDialog() {
        GenericActivity.startActivity(requireContext(), R.layout.layout_about)
    }
}
