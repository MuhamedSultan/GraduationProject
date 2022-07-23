package fragment

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.we_care.R
import kotlinx.android.synthetic.main.activity_adoption2.*


class Adoption2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoption2)

        button2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse("https://www.moss.gov.eg/Sites/MOSA/ar-eg/Pages/orphan-child.aspx"))
                startActivity(intent)

        }

    }
}